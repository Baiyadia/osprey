package com.kaiqi.osprey.web.biz;

import com.kaiqi.osprey.common.annotation.ReliableTransaction;
import org.springframework.stereotype.Component;

@Component
public class KQService extends BaseService {

    @ReliableTransaction
    public int demo(String a) {
        return 0;
    }

}
