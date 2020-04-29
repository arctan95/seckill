package com.tan.seckill.mapper;

import com.tan.seckill.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author tan
 * @Date 2020/4/15 11:18
 * @Version 1.0
 **/
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from sk_user where id = #{id}")
    public User getById(@Param("id")long id);

    @Update("update sk_user set password = #{password} where id = #{id}")
    public void update(User toBeUpdate);
}
