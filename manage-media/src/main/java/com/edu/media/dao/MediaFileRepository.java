package com.edu.media.dao;

import com.edu.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Admin
 */
public interface MediaFileRepository extends MongoRepository<MediaFile, String> {
}
