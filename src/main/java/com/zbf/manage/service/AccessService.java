package com.zbf.manage.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zbf.manage.common.Result;
import com.zbf.manage.common.contant.RetCode;
import com.zbf.manage.common.exception.BusinessException;
import com.zbf.manage.common.util.RetResponse;
import com.zbf.manage.entity.Access;
import com.zbf.manage.entity.CallbackLog;
import com.zbf.manage.mapper.AccessDao;
import com.zbf.manage.mapper.CallbackLogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
//@RefreshScope
public class AccessService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final AccessDao accessDao;
    private final CallbackLogDao callbackLogDao;

    @Autowired
    public AccessService(AccessDao accessDao, CallbackLogDao callbackLogDao) {
        this.accessDao = accessDao;
        this.callbackLogDao = callbackLogDao;
    }


    public ResponseEntity<Result<String>> keepAlive(String key){

        Access info = null;
        try {
            info = accessDao.findAccessByKey(key);
        }catch (Exception e){
            AccessService.log.error("保活异常:" + e.getMessage());
            throw new BusinessException(RetCode.FAIL, "保活异常,请联系管理员");
        }
        if(info == null){
            return new ResponseEntity<>(RetResponse.error("保活失败,未获取到有效的key"), HttpStatus.OK);
        }

        CallbackLog log = new CallbackLog(key,"","");
        try {
            callbackLogDao.insert(log);
        }catch (Exception e){
            AccessService.log.error("插入日志异常:" + e.getMessage());
        }

        return new ResponseEntity<>(RetResponse.success("保活成功"), HttpStatus.OK);
    }

    public void scriptKeepAlive(){

       List<Access> infos = new ArrayList<>();
        try {
            infos = accessDao.selectAccessIsEnable();
        }catch (Exception e){
            AccessService.log.error("获取所有已启动key异常:" + e.getMessage());
        }
        if(infos.size() == 0){
            AccessService.log.info("无已启动key");
        }
        List<CallbackLog> callbackLogList = new ArrayList<>();
        try {
            String time = sdf.format(new Date(new Date().getTime() -5*60*1000)) ;
            callbackLogList = callbackLogDao.selectByAppkeyList(infos.stream().map(Access::getAppkey).collect(Collectors.toList()), time);
        }catch (Exception e){
            AccessService.log.error("获取所有已启动key异常:" + e.getMessage());
        }

//        List<Access> noKeepList = new ArrayList<>();
//        for (Access a:infos             ) {
//            for (CallbackLog c:callbackLogList             ) {
//                if(a.getAppkey().equals(c.getAppkey())){
//                    continue;
//                }
//                noKeepList.add();
//            }
//            noKeepList
//        }


    }

}
