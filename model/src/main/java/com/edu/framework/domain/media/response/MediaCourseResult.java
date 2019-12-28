package com.edu.framework.domain.media.response;

import com.edu.framework.domain.media.MediaVideoCourse;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import com.edu.framework.domain.media.MediaFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@NoArgsConstructor
public class MediaCourseResult extends ResponseResult {
    public MediaCourseResult(ResultCode resultCode, MediaVideoCourse mediaVideoCourse) {
        super(resultCode);
        this.mediaVideoCourse = mediaVideoCourse;
    }

    MediaFile mediaVideo;
    MediaVideoCourse mediaVideoCourse;
}
