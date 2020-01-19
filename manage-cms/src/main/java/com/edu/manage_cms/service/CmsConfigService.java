package com.edu.manage_cms.service;

import com.edu.framework.domain.cms.CmsConfig;
import com.edu.framework.domain.cms.response.CmsCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.manage_cms.dao.CmsConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mjh
 */
@Service
public class CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig getConfigById(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_PARAMS_ISNULL);
        }
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        return optional.orElse(null);
    }
}
