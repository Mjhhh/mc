package com.edu.framework.domain.cms.response;

import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import com.edu.framework.domain.cms.CmsPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cms请求响应类
 */
@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
