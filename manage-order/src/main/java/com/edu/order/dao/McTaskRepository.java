package com.edu.order.dao;

import com.edu.framework.domain.task.McTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface McTaskRepository extends JpaRepository<McTask, String> {
    /**
     * 取出指定时间之前的记录
     *
     * @param pageable
     * @param updateTime
     * @return
     */
    Page<McTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * @param id
     * @param version
     * @return
     */
    @Modifying
    @Query("update McTask t set t.version = :version+1 where t.id = :id and t.version = :version")
    int updateTaskVersion(@Param(value = "id") String id, @Param(value = "version") int version);
}
