package com.edu.manage_cms_client.dao;

import com.edu.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage, String> {



}
