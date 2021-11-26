package com.zbf.manage.mapper;

import com.zbf.manage.entity.Access;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccessDao  extends BaseMapper<Access> {

    int deleteByPrimaryKey(Integer id);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Integer id);

    @Select("select * from access where appkey = #{key} and is_enable = 1 order by create_time desc LIMIT 1")
    Access findAccessByKey(@Param("key")String key);

    @Select("select * from access where is_enable = 1 ")
    List<Access> selectAccessIsEnable();

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);
}
