package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.base.*;
import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.base.Point;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.core.DDTankPic;
import com.pwc.sdc.ddtank.type.TowardEnum;
import com.pwc.sdc.ddtank.util.BinaryPicProcess;
import com.pwc.sdc.ddtank.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class DDTankPic10_4 extends AbstractDDTankPic implements DDTankPic, Serializable {
    private static final long serialVersionUID = 1L;

    public DDTankPic10_4(Library dm, DDTankCoreTaskProperties properties, Mouse mouse) {
        super(properties, dm, mouse);
    }


    @Override
    public Point mailPoint() {
        return new Point(850, 60);
    }

    @Override
    public Integer getAngle() {
        ThreadUtils.delay(100, true);
        String reuslt = dm.ocr(23, 552, 77, 590, "000000-000000", 0.9);
        reuslt = reuslt.replaceAll("\\D", "");
        if (reuslt.isEmpty()) {
            return null;
        }
        return Integer.parseInt(reuslt);
    }

    @Override
    public void drawThird() {
        mouse.moveAndClick(400, 347);
        mouse.moveAndClick(400, 347);
    }

    /**
     * 10.4无全选到背包的操作
     * @param find
     */
    @Override
    public void needPutInBag(boolean find) {
        return;
    }

    @Override
    public double calcUnitDistance() {
        double rectangleWidth = binaryPicProcess.findRectangleWidth(properties.getStaticX1(), properties.getStaticY1(), properties.getStaticX2(), properties.getStaticY2(),
                "999999", 1);
        return rectangleWidth / 10;
    }
}