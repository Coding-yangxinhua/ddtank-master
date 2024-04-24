package com.pwc.sdc.ddtank.account.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.account.DDTankAccountSignHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.pwc.sdc.ddtank.util.ThreadUtils.delay;

public class SimpleDDTankAccountSignHandlerImpl implements DDTankAccountSignHandler, Serializable {
    private Mouse mouse;

    private Keyboard keyboard;

    private Library dm;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String password;

    public SimpleDDTankAccountSignHandlerImpl(Library dm, Mouse mouse, Keyboard keyboard) {
        this.dm = dm;
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

    @Override
    public void login() {
        // 先等待网页响应
        delay(3000, true);

        // 有的时候点一下可能没用
//        mouse.moveAndClick(300, 300);
//        mouse.moveAndClick(300, 300);
//        mouse.moveAndClick(300, 300);
        delay(500, true);
//        keyboard.keyPressChar("tab");
        // 发送用户名
        dm.sendStringIme(username);
        delay(500, true);
        keyboard.keyPressChar("tab");
        delay(500, true);
        // 发送密码
        dm.sendStringIme(password);
        delay(500, true);
        keyboard.keyPressChar("tab");
        keyboard.keyPressChar("enter");
    }
}
