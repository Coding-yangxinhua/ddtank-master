package com.pwc.sdc.ddtank.handler.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.handler.DDTankAutoCompleteHandler;

import java.io.Serializable;

import static com.pwc.sdc.ddtank.util.ThreadUtils.delay;

public class DDTankAutoCompleteHandlerImpl implements DDTankAutoCompleteHandler, Serializable {
    private static final long serialVersionUID = 1L;

    private Keyboard keyboard;

    private Mouse mouse;

    public DDTankAutoCompleteHandlerImpl(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    @Override
    public void completeTask() {
        // 先点击一下其他地方，防止q按在了聊天输入框里。
        mouse.moveAndClick(20,520);
        for (int i = 0; i < 3; i++) {
            keyboard.keyPress('q');
            delay(1000, true);
            // 领取任务按钮的位置
            mouse.moveAndClick(667,517);
            delay(1000, true);
            keyboard.keyPress('q');
            mouse.moveAndClick(667,517);
        }
    }
}
