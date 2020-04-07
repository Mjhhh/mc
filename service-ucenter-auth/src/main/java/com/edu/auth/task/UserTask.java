package com.edu.auth.task;

import com.edu.auth.dao.McUserRepository;
import com.edu.framework.domain.ucenter.McUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class UserTask {

    @Autowired
    McUserRepository mcUserRepository;

    /**
     * 每一小时清空一次验证码
     */
    @Async
    @Scheduled(cron = "0 0 0/1 * * *")
    public void clearVerifycode() {
        String filePath = "D:/JavaProject/projectForSchool/mcEduUI/mc-ui/img/tempfile";
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File f : files) {
                   f.delete();
                }
            }
        }
    }
}