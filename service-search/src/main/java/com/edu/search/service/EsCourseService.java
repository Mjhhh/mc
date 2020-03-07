package com.edu.search.service;

import com.edu.framework.domain.course.CoursePub;
import com.edu.framework.domain.search.CourseSearchParam;
import com.edu.framework.model.response.CommonCode;
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
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            //根据一级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            //根据二级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            //根据难度等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        //分页
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 20;
        }
        int start = (page - 1) * size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);
        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);
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
            if(highlightFields!=null){
                HighlightField nameField = highlightFields.get("name");
                if(nameField!=null){
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
            //价格
            Double price = null;
            if (sourceAsMap.get("price") != null) {
                price = (Double) sourceAsMap.get("price");
            }
            //收费
            String charge = null;
            if (sourceAsMap.get("charge") != null) {
                charge = (String) sourceAsMap.get("charge");
            }
            coursePub.setCharge(charge);
            //原价格
            Double priceOld = null;
            if (sourceAsMap.get("price_old") != null) {
                priceOld = (Double) sourceAsMap.get("price_old");
            }
            coursePub.setPrice_old(priceOld);

            list.add(coursePub);
        }
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(totalHits);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}