package com.pwc.sdc.ddtank.config;

import com.pwc.sdc.ddtank.base.Keyboard;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.base.impl.*;
import com.pwc.sdc.ddtank.base.impl.DMKeyboard;
import com.pwc.sdc.ddtank.base.impl.DMLibrary;
import com.pwc.sdc.ddtank.base.impl.DMMouse;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.mapper.DDTankConfigMapper;
import com.pwc.sdc.ddtank.util.JacobUtils;
import com.jacob.activeX.ActiveXComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DDtankComponents {

    @Autowired
    private ConfigurableApplicationContext context;

    @Bean
    public DDTankSetting getDDTankSetting() {
        return new DDTankSetting();
    }

    @Bean
    public DDTankCoreTaskProperties getDDTankConfigProperties() {
        return new DDTankCoreTaskProperties(DDTankConfigMapper.getDefaultConfigProperties());
    }

    /**
     * 新建大漠对象
     *
     * @return
     */
    @Bean
    public ActiveXComponent getDmActiveXComponent() {
        return JacobUtils.getActiveXCompnent();
    }

    /**
     * 新建大漠库
     *
     * @return
     */
    @Bean
    public DMLibrary getDMLibrary() {
        return new DMLibrary(getDmActiveXComponent());
    }

    /**
     * 由于鼠标是调用dll，所以可以设为单例
     *
     * @return
     */
    @Bean
    public Mouse getMouse() {
        return new DMMouse(getDmActiveXComponent());
    }

    /**
     * 由于键盘是调用dll，所以可以设为单例
     *
     * @return
     */
    @Bean
    public Keyboard getKeyboard() {
        return new DMKeyboard(getDmActiveXComponent());
    }
}
