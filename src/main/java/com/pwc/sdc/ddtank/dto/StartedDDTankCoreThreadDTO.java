package com.pwc.sdc.ddtank.dto;

import com.pwc.sdc.ddtank.core.DDTankCoreScript;
import lombok.Getter;

public class StartedDDTankCoreThreadDTO {
    @Getter
    private final long hwnd;

    @Getter
    private final DDTankCoreScript script;

    public StartedDDTankCoreThreadDTO(long hwnd, DDTankCoreScript script) {
        this.hwnd = hwnd;
        this.script = script;
    }
}
