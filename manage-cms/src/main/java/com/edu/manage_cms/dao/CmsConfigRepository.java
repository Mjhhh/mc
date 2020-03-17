package com.edu.manage_cms.dao;

import com.edu.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author mjh
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {
    CmsConfig findByName(String name);
}
