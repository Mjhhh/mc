package com.edu.api.cms;

import com.edu.framework.model.response.QueryResponseResult;
import com.edu.framework.domain.cms.request.QueryPageRequest;

public interface CmsPageControllerApi {
    //页面查询
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}
