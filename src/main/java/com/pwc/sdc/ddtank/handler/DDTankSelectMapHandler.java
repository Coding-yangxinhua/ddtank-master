package com.pwc.sdc.ddtank.handler;

import com.pwc.sdc.ddtank.entity.LevelRule;

import java.util.List;

public interface DDTankSelectMapHandler {
    /**
     * 选择副本
     * @param passed 已通关次数
     */
    void select(int passed);

    boolean addRule(LevelRule rule);

    boolean removeRule(int index);

    List<LevelRule> getRules();
}
