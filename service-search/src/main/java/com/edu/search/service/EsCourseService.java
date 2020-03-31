package com.edu.search.service;

import com.edu.framework.domain.course.CoursePub;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
@Service
public class EsCourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsCourseService.class);

    @Value("${elasticsearch.course.index}")
    private String esIndex;
    @Value("${elasticsearch.course.type}")
    private String esType;
    @Value("${elasticsearch.course.source_field}")
    private String sourceField;
    @Value("${elasticsearch.media.index}")
    private String mediaIndex;
    @Value("${elasticsearch.media.type}")
    private String mediaType;
    @Value("${elasticsearch.media.source_field}")
    private String mediaSourceField;

    @Resource
    RestHighLevelClient restHighLevelClient;

    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam) {
        //设置索引
        SearchRequest searchRequest = new SearchRequest(esIndex);
        //设置类型
        searchRequest.types(esType);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //source源字段过滤
        String[] sourceFields = sourceField.split(",");
        searchSourceBuilder.fetchSource(sourceFields, new String[]{});
        //关键字
        if (StringUtils.isNotBlank(courseSearchParam.getKeyword())) {
            //匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "teachplan", "description");
            //设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            //提升另一个字段的Boost值
            multiMatchQueryBuilder.field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //过虑
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
            //根据一级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt", courseSearchParam.getMt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {
            //根据二级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {
            //根据难度等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
        }
        //分页
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 20;
        }
        int start = (page - 1) * size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);
        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);
//        //高亮设置
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font class=\"eslight\">");
//        highlightBuilder.postTags("</font>");
//        //设置高亮字段
//        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
//        searchSourceBuilder.highlighter(highlightBuilder);
        //请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("mock search error..{}", e.getMessage());
            return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<CoursePub>());
        }
        //结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //记录总数
        long totalHits = hits.getTotalHits();
        //数据列表
        List<CoursePub> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            CoursePub coursePub = new CoursePub();
            //取出source
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //取出id
            String id = (String) sourceAsMap.get("id");
            coursePub.setId(id);
            //取出名称
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField nameField = highlightFields.get("name");
                if (nameField != null) {
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            coursePub.setName(name);
            //图片
            String pic = (String) sourceAsMap.get("pic");
            coursePub.setPic(pic);
            //收费
            String charge = null;
            if (sourceAsMap.get("charge") != null) {
                charge = String.valueOf(sourceAsMap.get("charge"));
            }
            coursePub.setCharge(charge);
            //价格
            Float price = null;
            if (sourceAsMap.get("price") != null) {
                price = Float.parseFloat(sourceAsMap.get("price").toString());
            }
            coursePub.setPrice(price);
            //原价格
            Float priceOld = null;
            if (sourceAsMap.get("price_old") != null) {
                priceOld = Float.parseFloat(sourceAsMap.get("price_old").toString());
            }
            coursePub.setPriceOld(priceOld);
            //设置等级
            if (sourceAsMap.get("grade") != null) {
                coursePub.setGrade(String.valueOf(sourceAsMap.get("grade")));
            }
            //设置描述
            if (sourceAsMap.get("description") != null) {
                coursePub.setDescription(String.valueOf(sourceAsMap.get("description")));
            }
            list.add(coursePub);
        }
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(totalHits);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 获取课程所有信息
     *
     * @param id
     * @return
     */
    public CommonResponseResult getall(String id) {
        //设置索引库
        SearchRequest searchRequest = new SearchRequest(esIndex);
        //设置类型
        searchRequest.types(esType);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件，根据课程id查询
        searchSourceBuilder.query(QueryBuilders.termQuery("id", id));
        //取消source源字段过滤，查询所有字段
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            //执行搜索
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Map<String, CoursePub> map = new HashMap<>();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String courseId = (String) sourceAsMap.get("id");
            String name = (String) sourceAsMap.get("name");
            String grade = (String) sourceAsMap.get("grade");
            String charge = (String) sourceAsMap.get("charge");
            String pic = (String) sourceAsMap.get("pic");
            String description = (String) sourceAsMap.get("description");
            String teachplan = (String) sourceAsMap.get("teachplan");
            CoursePub coursePub = new CoursePub();
            coursePub.setId(courseId);
            coursePub.setName(name);
            coursePub.setPic(pic);
            coursePub.setGrade(grade);
            coursePub.setCharge(charge);
            coursePub.setTeachplan(teachplan);
            coursePub.setDescription(description);
            map.put(courseId, coursePub);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, map);
    }

    /**
     * 根据课程计划查询媒资信息
     * @param teachplanIds
     * @return
     */
    public QueryResponseResult getmedia(String[] teachplanIds) {
        //设置索引
        SearchRequest searchRequest = new SearchRequest(mediaIndex);
        //设置类型
        searchRequest.types(mediaType);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        String[] source_fields = mediaSourceField.split(",");
        searchSourceBuilder.fetchSource(source_fields, new String[]{});
        //查询条件，根据课程计划id查询(可传入多个id)
        searchSourceBuilder.query(QueryBuilders.termsQuery("teachplan_id", teachplanIds));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            //执行搜索
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        assert searchResponse != null;
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //数据列表
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //取出课程计划媒资信息
            String courseid = (String) sourceAsMap.get("courseid");
            String mediaId = (String) sourceAsMap.get("media_id");
            String mediaUrl = (String) sourceAsMap.get("media_url");
            String teachplanId = (String) sourceAsMap.get("teachplan_id");
            String mediaFileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaUrl(mediaUrl);
            teachplanMediaPub.setMediaFileOriginalName(mediaFileoriginalname);
            teachplanMediaPub.setMediaId(mediaId);
            teachplanMediaPub.setTeachplanId(teachplanId);
            //将数据加入列表
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        //构建返回课程媒资信息对象
        QueryResult<TeachplanMediaPub> queryResult = new QueryResult<>();
        queryResult.setList(teachplanMediaPubList);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
