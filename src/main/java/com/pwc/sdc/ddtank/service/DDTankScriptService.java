package com.pwc.sdc.ddtank.service;

import com.pwc.sdc.ddtank.core.DDTankCoreScript;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;

import java.util.List;

public interface DDTankScriptService {
    DDTankCoreScript add(String name, boolean needCorrect, DDTankCoreTaskProperties properties);

    List<DDTankCoreScript> list();

    DDTankCoreScript getByIndex(int index);

    boolean removeByIndex(List<Integer> index);

    boolean addOrUpdate(DDTankCoreScript script);
}
