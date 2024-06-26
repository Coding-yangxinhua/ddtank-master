package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.core.DDTankLevel;
import com.pwc.sdc.ddtank.core.DDTankLevelSummary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DDTankLevelSummaryImpl implements DDTankLevelSummary {

    private static final long serialVersionUID = 1L;

    private final DDTankCoreTaskProperties properties;

    private final Map<DDTankLevel, Integer> levelSummaryMap = new ConcurrentHashMap<>();

    public DDTankLevelSummaryImpl(DDTankCoreTaskProperties properties) {
        this.properties = properties;
    }

    @Override
    public void summary() {
        DDTankLevel level = DDTankLevel.getInstance(properties);
        levelSummaryMap.merge(level, 1, Integer::sum);
    }

    @Override
    public Map<DDTankLevel, Integer> getSummary() {
        return levelSummaryMap;
    }
}
