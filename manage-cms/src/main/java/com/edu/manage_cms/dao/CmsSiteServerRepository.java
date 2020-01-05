package com.edu.manage_cms.dao;

import com.edu.framework.domain.cms.CmsSiteServer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author mjh
 */
public interface CmsSiteServerRepository extends MongoRepository<CmsSiteServer,String> {
}
