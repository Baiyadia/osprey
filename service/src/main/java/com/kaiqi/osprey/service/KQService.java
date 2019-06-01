package com.kaiqi.osprey.service;

import com.kaiqi.osprey.annotation.ReliableTransaction;
import org.springframework.stereotype.Component;

@Component
public class KQService extends BaseService {

    @ReliableTransaction
    public int demo(String a) {
        return 0;
    }

}
