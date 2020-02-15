package com.edu.manage_cms.service;

import com.edu.framework.domain.system.SysDictionary;
import com.edu.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryService {

    @Autowired
    SysDictionaryRepository sysDictionaryRepository;

    /**
     * 根据字典分类查询字典信息
     * @param dType 类型
     * @return 字典信息
     */
    public SysDictionary findDictionaryByType(String dType) {
        return sysDictionaryRepository.findByDType(dType);
    }
}
