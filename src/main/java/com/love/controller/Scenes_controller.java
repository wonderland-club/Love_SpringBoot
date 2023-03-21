package com.love.controller;

import com.love.Mapper.ScenesMapper;
import com.love.Mapper.UserMapper;
import com.love.module.Scenes;
import com.love.module.ScenesExample;
import com.love.module.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Scenes_controller {

    @Autowired
    ScenesMapper scenesMapper;//查询
    ScenesExample scenesExample;
    ScenesExample.Criteria criteria;

    @GetMapping("/scenes-all")
    public List<Scenes> getScenesAll() {
        scenesExample = new ScenesExample();
        List<Scenes> ScenesALl = scenesMapper.selectByExample(scenesExample);
        return ScenesALl;
    }

    @PostMapping("/setScenes")
    public List<Scenes> SetScenesAll(@RequestBody List<Scenes> scenesList) {
        scenesExample = new ScenesExample();
        scenesMapper.selectByExample(scenesExample);
        scenesExample = new ScenesExample();
        List<Scenes> ScenesALl = scenesMapper.selectByExample(scenesExample);
        return ScenesALl;
    }
}
