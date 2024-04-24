package com.pwc.sdc.ddtank.service;

import com.pwc.sdc.ddtank.core.DDTankCoreScript;
import com.pwc.sdc.ddtank.entity.LevelRule;

public interface DDTankDetailService {
    boolean setTaskAutoComplete(DDTankCoreScript coreThread, int taskAutoComplete);

    boolean setAutoUseProp(DDTankCoreScript coreThread, int autoUseProp);

    boolean setAutoReconnect(DDTankCoreScript coreThread, String username, String password);

    boolean addRule(DDTankCoreScript coreThread, LevelRule levelRule);

    boolean removeRule(DDTankCoreScript coreThread, int index);
}
