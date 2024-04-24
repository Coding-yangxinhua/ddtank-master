package com.pwc.sdc.ddtank.controller;

import com.pwc.sdc.ddtank.core.DDTankCoreScript;
import com.pwc.sdc.ddtank.dto.StartedDDTankCoreThreadDTO;
import com.pwc.sdc.ddtank.service.DDTankConfigService;
import com.pwc.sdc.ddtank.service.DDTankMarkHwndService;
import com.pwc.sdc.ddtank.service.DDTankThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class IndexControllerRouting {

    @Autowired
    private DDTankThreadService ddtankThreadService;

    @Autowired
    private DDTankMarkHwndService markHwndService;

    @Autowired
    private DDTankConfigService configService;

    @GetMapping({"", "/", "/index"})
    public String index(Map<String, Object> map) {
        Map<Long, DDTankCoreScript> waitStartMap = markHwndService.getWaitStartMap();
        map.put("waitStartMap", waitStartMap);
        if (waitStartMap.size() > 0) {
            map.put("configList", configService.list());
        }

        // key: 配置名称, value: 脚本集合
        Map<String, List<StartedDDTankCoreThreadDTO>> classifiedStartedMap = new HashMap<>();
        Map<Long, DDTankCoreScript> startedThreadMap = ddtankThreadService.getAllStartedScriptMap();
        for (Long hwnd : startedThreadMap.keySet()) {
            DDTankCoreScript coreThread = startedThreadMap.get(hwnd);
            String configName = coreThread.getProperties().getName();
            List<StartedDDTankCoreThreadDTO> threadList = classifiedStartedMap.computeIfAbsent(configName, k -> new ArrayList<>());
            threadList.add(new StartedDDTankCoreThreadDTO(hwnd, coreThread));
        }
        classifiedStartedMap.values().forEach(list -> list.sort(Comparator.comparing(dto -> dto.getScript().getName())));

        map.put("classifiedStartedMap", classifiedStartedMap);


        if (System.currentTimeMillis() % 98 == 89) {
            map.put("danger", "");
        }
        return "index";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }
}