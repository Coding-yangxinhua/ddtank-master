package com.pwc.sdc.ddtank.handler.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.handler.DDTankFindPositionMoveHandler;

import java.io.Serializable;

import static com.pwc.sdc.ddtank.util.ThreadUtils.delay;

public class SimpleDDTankFindPositionMoveHandlerImpl implements DDTankFindPositionMoveHandler, Serializable {

    private static final long serialVersionUID = 1L;

    private Keyboard keyboard;

    public SimpleDDTankFindPositionMoveHandlerImpl(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public boolean move(int triedTimes) {
        if (System.currentTimeMillis() % 3 == 2) {
            toRight();
        } else {
            toLeft();
        }
        return true;
    }

    private void toLeft() {
        keyboard.keyPress('a');
        keyboard.keyDown('a');
        delay(200, true);
        keyboard.keyUp('a');
        keyboard.keyPress('d');
    }

    private void toRight() {
        keyboard.keyPress('d');
        keyboard.keyDown('d');
        delay(200, true);
        keyboard.keyUp('d');
        keyboard.keyPress('a');
    }
}
