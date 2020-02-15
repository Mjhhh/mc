package com.edu.manage_cms.dao;

import com.edu.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author mjh
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary, String> {

    /**
     * 根据字典分类查询字典信息
     * @param dType 类型
     * @return 字典信息
     */
    SysDictionary findByDType(String dType);
}
