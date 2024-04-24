package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.config.DDTankFileConfigProperties;
import com.pwc.sdc.ddtank.config.DMPicConfigProperties;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.core.DDTankPic;
import com.pwc.sdc.ddtank.core.pic.PicFind;
import com.pwc.sdc.ddtank.core.pic.PicFindBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDDTankPic implements DDTankPic, Serializable {

    private static final long serialVersionUID = 1L;


    protected Library dm;

    protected DDTankCoreTaskProperties properties;

    private Map<String, PicFind> picFindMap;


    public AbstractDDTankPic(DDTankCoreTaskProperties properties, Library dm, String picKeyPrefix) {
        this.properties = properties;
        this.dm = dm;
        Map<String, PicFindBuilder> keyPicFindBuilderMap = DMPicConfigProperties.getKeyPicFindBuilderMap(this.getClass());
        picFindMap = new HashMap<>();
        for (String key : keyPicFindBuilderMap.keySet()) {
            PicFindBuilder picFindBuilder = keyPicFindBuilderMap.get(key);
            picFindMap.put(key, picFindBuilder.build(new File(DDTankFileConfigProperties.getBaseDir(), properties.getPicDir()).getAbsolutePath(), dm));
        }
    }

    protected PicFind getPicFind(String key) {
        return picFindMap.get(key);
    }
}