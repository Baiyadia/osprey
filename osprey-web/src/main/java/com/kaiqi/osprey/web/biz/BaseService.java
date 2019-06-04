package com.kaiqi.osprey.web.biz;

import com.kaiqi.osprey.web.AppConfig;

import javax.annotation.Resource;

public abstract class BaseService {

    @Resource
    protected AppConfig appConf;

}
