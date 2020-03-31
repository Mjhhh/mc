package com.edu.order.dao;

import com.edu.framework.domain.order.McOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface McOrdersMapper{
    @Select("SELECT * FROM mc_orders WHERE user_id = #{userId}")
    List<McOrders> selectListByUserId(String userId);
}
