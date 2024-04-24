package com.pwc.sdc.ddtank.core;

import com.pwc.sdc.ddtank.base.Point;

public interface DDTankOperate {
    void chooseMap();

    boolean angleAdjust(int targetAngle);

    void attack(double strength);

    /**
     * 获取最佳角度
     * @return
     */
    int getBestAngle(Point myPosition, Point enemyPosition);

    double getStrength(int angle,double wind, double horizontal, double vertical);
}
