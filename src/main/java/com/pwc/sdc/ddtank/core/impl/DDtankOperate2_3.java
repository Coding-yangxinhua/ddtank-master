package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.core.DDTankPic;
import com.pwc.sdc.ddtank.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class DDtankOperate2_3 extends DDtankOperate10_4 implements Serializable {
    private static final long serialVersionUID = 1L;
    public DDtankOperate2_3(Library dm, Mouse mouse, Keyboard keyboard, DDTankPic ddTankPic, DDTankCoreTaskProperties properties) {
        super(dm, mouse, keyboard, ddTankPic, properties);
    }

    @Override
    public void chooseMap() {
        int x = -1, y = -1;
        int line = properties.getLevelLine();
        int row = properties.getLevelRow();
        // 没有副本模式了

        switch (line % 3) {
            case 1:
                y = 315;
                break;
            case 2:
                y = 370;
                break;
            case 0:
                y = 425;
                break;
        }
        switch (row % 4) {
            case 1:
                x = 320;
                break;
            case 2:
                x = 455;
                break;
            case 3:
                x = 590;
                break;
            case 0:
                x = 725;
                break;
        }

        // 对于需要翻页的副本将那些的操作，该端的翻页逻辑是点一下滑块，点两下按钮
        if (line > 3) {
            for (int i = 0; i < (line / 3); i++) {
                mouse.moveTo(805, 430);
                mouse.leftDown();
                mouse.leftUp();
                ThreadUtils.delay(100, true);
                mouse.moveTo(805, 435);
                mouse.leftDown();
                mouse.leftUp();
                ThreadUtils.delay(100, true);
                mouse.leftDown();
                mouse.leftUp();
                ThreadUtils.delay(100, true);
            }
        }


        log.debug("选择地图最终坐标: {}, {}", x, y);
        mouse.moveAndClick(x, y);
        ThreadUtils.delay(100, true);

        // 选择难度
        mouse.moveAndClick(269 + 524 * (properties.getLevelDifficulty() / 100), 500);

        // 点击确定
        mouse.moveAndClick(721,569);
        ThreadUtils.delay(300, true);
    }

    @Override
    protected void angleAdjust(int nowAngle, int targetAngle, int angleMis) {
        if (nowAngle < (targetAngle + angleMis)) {
            for (int i = 0; i < targetAngle - nowAngle; i++) {
                keyboard.keyDown('w');
                ThreadUtils.delay(10, true);
                keyboard.keyUp('w');
            }
            ThreadUtils.delay(100, true);
        }
        if (nowAngle > targetAngle + angleMis) {
            for (int i = 0; i < nowAngle - targetAngle; i++) {
                keyboard.keyDown('s');
                ThreadUtils.delay(10, true);
                keyboard.keyUp('s');
            }
            ThreadUtils.delay(100, true);
        }
    }
}
