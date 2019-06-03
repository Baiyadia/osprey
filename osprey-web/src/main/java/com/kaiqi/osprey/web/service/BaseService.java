package com.kaiqi.osprey.web.service;

import com.kaiqi.osprey.web.AppConfig;

import javax.annotation.Resource;

public abstract class BaseService {

    @Resource
    protected AppConfig appConf;

}
