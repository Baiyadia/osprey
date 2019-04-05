package com.kaiqi.osprey.service;

import com.kaiqi.osprey.AppConfig;
import com.kaiqi.osprey.ServerConfig;

import javax.annotation.Resource;

public abstract class BaseService {

    @Resource
    protected AppConfig appConfig;

    @Resource
    protected ServerConfig serverConfig;

}
