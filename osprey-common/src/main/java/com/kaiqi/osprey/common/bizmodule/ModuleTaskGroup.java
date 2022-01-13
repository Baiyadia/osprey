package com.kaiqi.osprey.common.bizmodule;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangs
 * @title: ModuleTaskGroup
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 任务组，加载实现该任务框架的所有任务。
 * @date 2020-02-14 19:20
 */
//@Component
public class ModuleTaskGroup {

    private final Map<String, IndividualModule> bizModuleMap = new HashMap<>();

//    @Resource()
    private void loadModules(IndividualModule[] array) {
        for (IndividualModule obj : array) {
            if (!StringUtils.isEmpty(obj.taskId())) {
                bizModuleMap.put(obj.taskId(), obj);
            }
        }
    }

    public IndividualModule get(String name) {
        return bizModuleMap.get(name);
    }
}
