package com.edu.manage_cms.dao;


import com.edu.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author mjh
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {

}
