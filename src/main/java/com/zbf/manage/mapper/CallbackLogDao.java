package com.zbf.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbf.manage.entity.Access;
import com.zbf.manage.entity.CallbackLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CallbackLogDao extends BaseMapper<CallbackLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(CallbackLog record);

    int insertSelective(CallbackLog record);

    CallbackLog selectByPrimaryKey(Integer id);

    List<CallbackLog> selectByAppkeyList(@Param("key")List<String> keys,@Param("key")String time);

    int updateByPrimaryKeySelective(CallbackLog record);

    int updateByPrimaryKey(CallbackLog record);
}
