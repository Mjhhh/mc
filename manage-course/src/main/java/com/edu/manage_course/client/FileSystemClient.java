package com.edu.manage_course.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.framework.domain.cms.response.CmsPageResult;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceList.BASE_FILESYSTEM)
public interface FileSystemClient {

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @DeleteMapping("/filesystem/delete")
    ResponseResult delete(@RequestParam String fileId);
}

