package com.kaiqi.osprey.service;

import com.kaiqi.osprey.AppConfig;

import javax.annotation.Resource;

public abstract class BaseService {

    @Resource
    protected AppConfig appConf;

}
