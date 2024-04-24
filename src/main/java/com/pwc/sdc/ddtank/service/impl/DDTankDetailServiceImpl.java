package com.pwc.sdc.ddtank.service.impl;

import com.pwc.sdc.ddtank.core.DDTankCoreScript;
import com.pwc.sdc.ddtank.core.DDTankCoreTask;
import com.pwc.sdc.ddtank.entity.LevelRule;
import com.pwc.sdc.ddtank.service.DDTankDetailService;
import org.springframework.stereotype.Service;

@Service
public class DDTankDetailServiceImpl implements DDTankDetailService {
    public boolean setTaskAutoComplete(DDTankCoreScript coreThread, int taskAutoComplete) {
        if(coreThread == null) {
            return false;
        }
        DDTankCoreTask task = coreThread.getTask();
        task.setTaskAutoComplete(taskAutoComplete);
        return true;
    }

    @Override
    public boolean setAutoUseProp(DDTankCoreScript coreThread, int autoUseProp) {
        if(coreThread == null) {
            return false;
        }
        DDTankCoreTask task = coreThread.getTask();
        task.setAutoUseProp(autoUseProp);
        return true;
    }

    @Override
    public boolean setAutoReconnect(DDTankCoreScript coreThread, String username, String password) {
        if(coreThread == null) {
            return false;
        }
        coreThread.setAutoReconnect(username, password);
        return true;
    }

    @Override
    public boolean addRule(DDTankCoreScript coreThread, LevelRule levelRule) {
        if(coreThread == null) {
            return false;
        }
        coreThread.addRule(levelRule);
        return true;
    }

    @Override
    public boolean removeRule(DDTankCoreScript coreThread, int index) {
        if(coreThread == null) {
            return false;
        }
        coreThread.removeRule(index);
        return true;
    }
}
