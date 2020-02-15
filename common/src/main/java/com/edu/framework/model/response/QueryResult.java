package com.edu.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 返回的数据
 * @author Admin
 */
@Data
@ToString
public class QueryResult<T> {
    /**
     * 数据列表
     */
    private List<T> list;
    /**
     * 数据总数
     */
    private long total;
}
