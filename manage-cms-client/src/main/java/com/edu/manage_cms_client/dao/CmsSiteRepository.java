package com.edu.manage_cms_client.dao;

import com.edu.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
