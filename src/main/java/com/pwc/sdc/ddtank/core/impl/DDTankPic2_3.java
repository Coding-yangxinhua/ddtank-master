package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.base.Point;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.util.ColorUtils;
import com.pwc.sdc.ddtank.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class DDTankPic2_3 extends AbstractDDTankPic implements Serializable {
    private static final long serialVersionUID = 1L;

    public DDTankPic2_3(Library dm, DDTankCoreTaskProperties properties, Mouse mouse) {
        super(properties, dm, mouse);
    }

    @Override
    public Point mailPoint() {
        return new Point(836, 52);
    }

    @Override
    public void closeTip() {
        mouse.moveAndClick(428, 346);
    }

    @Override
    public void createRoom(Point point) {
        point.setOffset(30, 10);
        mouse.moveAndClick(point);
        mouse.moveAndClick(408, 442);
        ThreadUtils.delay(1000, true);
    }

    @Override
    public void drawThird() {
        mouse.moveAndClick(433, 343);
        mouse.moveAndClick(400, 340);
    }

    @Override
    public boolean needGoingToWharf() {
        if (getPicFind("needGoingToWharf").findPic()) {
            mouse.moveAndClick(608, 171);
            mouse.moveAndClick(608, 171);
        }
        // 蛋2.3大厅检测需要移动下鼠标
        mouse.moveTo(558, 531);
        Point point = new Point();
        if (getPicFind("needGoingToWharf2").findPic(point)) {
            mouse.moveAndClick(point);
            return true;
        }
        return false;
    }

    @Override
    public Integer getAngle() {
        String result = dm.ocr(42, 548, 86, 588, "1a1a1a-000000|1a260d-000000|101724-000000|1a2016-000000|28222b-000000|260d0d-000000|1c1d20-000000|211d1d-000000|171d32-000000|1c0d03-000000", 0.95);
        result = result.replaceAll("\\D", "");
        if (result.isEmpty()) {
            result = dm.ocr(42, 548, 86, 588, "000000-000000|170b02-000000|1b1818-000000", 0.95);
            result = result.replaceAll("\\D", "");
        }
        if (result.isEmpty()) {
            return null;
        }
        return Integer.parseInt(result);
    }

    @Override
    public double calcUnitDistance() {
        String color = dm.getAveRGB(properties.getStaticX1(), properties.getStaticY1(), properties.getStaticX2(), properties.getStaticY2());
        // 希望将颜色偏白606060
        color = ColorUtils.add(color, "303030") + "-303030";
        log.debug("计算屏距-颜色：{}", color);
        double rectangleWidth = binaryPicProcess.findRectangleWidth(properties.getStaticX1(), properties.getStaticY1(), properties.getStaticX2(), properties.getStaticY2(),
                color, 1);
        return rectangleWidth / 10;
    }
}