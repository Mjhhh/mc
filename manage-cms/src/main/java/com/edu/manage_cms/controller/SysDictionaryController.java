package com.edu.manage_cms.controller;

import com.edu.api.cms.SysDicthinaryControllerApi;
import com.edu.framework.domain.system.SysDictionary;
import com.edu.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/sys//dictionary")
public class SysDictionaryController implements SysDicthinaryControllerApi {

    @Autowired
    SysDictionaryService sysDictionaryService;

    @Override
    @GetMapping("/get/{type}")
    public SysDictionary getByType(@PathVariable String type) {
        return sysDictionaryService.findDictionaryByType(type);
    }
}
