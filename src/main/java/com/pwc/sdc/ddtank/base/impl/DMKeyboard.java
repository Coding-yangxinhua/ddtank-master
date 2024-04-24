package com.pwc.sdc.ddtank.base.impl;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import com.pwc.sdc.ddtank.util.ThreadUtils;

import java.io.Serializable;

public class DMKeyboard implements Keyboard, Serializable {

    private static final long serialVersionUID = 42L;

    transient private ActiveXComponent dm;

    public DMKeyboard(ActiveXComponent dm) {
        this.dm = dm;
    }

    @Override
    public void keyDown(char k) {
        k = Character.toUpperCase(k);
        dm.invoke("keyDown", new Variant(k));
    }

    @Override
    public void keyUp(char k) {
        k = Character.toUpperCase(k);
        dm.invoke("keyUp", new Variant(k));
    }

    @Override
    public void keyPress(char k) {
        k = Character.toUpperCase(k);
        dm.invoke("keyPress", new Variant(k));
    }

    @Override
    public void keysPress(String str, long millis) {
        for (char k : str.toCharArray()) {
            k = Character.toUpperCase(k);
            keyDown(k);
            ThreadUtils.delayPersisted(millis, true);
            keyUp(k);
        }
    }

    @Override
    public void keyPressChar(String k) {
        dm.invoke("keyPressChar", new Variant(k));
    }

    @Override
    public void keyDownChar(String k) {
        dm.invoke("keyDownChar", new Variant(k));
    }

    @Override
    public void keyUpChar(String k) {
        dm.invoke("keyUpChar", new Variant(k));
    }

    @Override
    public void keysPressStr(String str) {
        dm.invoke("keyPressStr", new Variant(str), new Variant(0));
    }

    @Override
    public void keysPress(String str) {
        keysPress(str, 0);
    }
}
