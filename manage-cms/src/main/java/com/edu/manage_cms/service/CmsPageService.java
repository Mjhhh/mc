package com.edu.manage_cms.service;

import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.request.QueryPageRequest;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.model.response.QueryResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mjh
 */
@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(StringUtils.isNotEmpty(queryPageRequest.getSiteId()) ? queryPageRequest.getSiteId() : null);
        cmsPage.setPageAliase(StringUtils.isNotEmpty(queryPageRequest.getPageAliase()) ? queryPageRequest.getPageAliase() : null);
        cmsPage.setPageName(StringUtils.isNotEmpty(queryPageRequest.getPageName()) ? queryPageRequest.getPageName() : null);
        cmsPage.setPageType(StringUtils.isNotEmpty(queryPageRequest.getPageType()) ? queryPageRequest.getPageType() : null);

        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        page = page - 1;
        //分页对象
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    /**
     * 添加页面
     *
     * @param cmsPage 页面的信息
     * @return 新增的页面
     */
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage selectCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (selectCmsPage != null) {
            //检验页面是否存在，已经存在则抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);//主键由spring data自动生成
        cmsPageRepository.save(cmsPage);
        //返回结果
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据id获取单个页面信息
     *
     * @param id
     * @return 页面信息
     */
    public CmsPage getById(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        return null;
    }

    /**
     * 更新页面信息
     *
     * @param id      页面id
     * @param cmsPage 更新之后的页面
     * @return 返回操作信息
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        if (StringUtils.isBlank(id) || cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        CmsPage one = this.getById(id);
        if (one == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        one.setTemplateId(cmsPage.getTemplateId());
        //更新所属站点
        one.setSiteId(cmsPage.getSiteId());
        //更新页面别名
        one.setPageAliase(cmsPage.getPageAliase());
        //更新页面名称
        one.setPageName(cmsPage.getPageName());
        //更新访问路径
        one.setPageWebPath(cmsPage.getPageWebPath());
        //更新物理路径
        one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        //执行更新
        CmsPage save = cmsPageRepository.save(one);
        if (save == null) {
            ExceptionCast.cast(CmsCode.CMS_SAVE_PAGE_FAIL);
        }
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     * 删除页面
     *
     * @param id 页面id
     * @return 返回操作信息
     */
    public ResponseResult delete(String id) {
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);
        if (cmsPage.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            ExceptionCast.cast(CmsCode.CMS_PAGE_IS_NOT_EXISTS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }


}
