package com.pwc.sdc.ddtank.handler.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.handler.DDTankAngleAdjustMoveHandler;
import com.pwc.sdc.ddtank.type.TowardEnum;

import java.io.Serializable;

import static com.pwc.sdc.ddtank.util.ThreadUtils.delay;

public class SimpleDDTankAngleAdjustMoveHandlerHandlerImpl implements DDTankAngleAdjustMoveHandler, Serializable {

    private static final long serialVersionUID = 1L;

    private Keyboard keyboard;

    public SimpleDDTankAngleAdjustMoveHandlerHandlerImpl(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public boolean move(TowardEnum targetToward, int targetAngle, int triedTimes) {
        if (targetToward == TowardEnum.LEFT) {
            for (int i = 0; i < 3; i++) {
                keyboard.keyDown('a');
                delay(50, true);
                keyboard.keyUp('a');
                delay(100, true);
            }
        } else if (targetToward == TowardEnum.RIGHT) {
            for (int i = 0; i < 3; i++) {
                keyboard.keyDown('d');
                delay(50, true);
                keyboard.keyUp('d');
                delay(100, true);
            }
        }
        return triedTimes <= 3;
    }
}
