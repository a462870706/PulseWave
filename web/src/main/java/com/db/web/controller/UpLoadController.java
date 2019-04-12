package com.db.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.web.entity.User;
import com.db.web.entity.Wave;
import com.db.web.service.UserService;
import com.db.web.service.WaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UpLoadController {

    @Autowired
    UserService userService;
    @Autowired
    WaveService waveService;

    // 上传请求
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/uploadRequest")
    public String uploadRequest(@RequestBody String object) {
        JSONObject back = new JSONObject();

        // 将JSON格式字符串转化为JSONObject
        JSONObject request = JSONObject.parseObject(object);

        // 获得请求 id
        String curr_id = request.getInteger("id").toString();
        // 获得请求id对于 User
        User curr_user = userService.selectUser_id(curr_id);
        if (curr_user == null) { // 用户不存在
            back.put("success", "false");
        } else {
            // 获得user对应 identity
            Boolean curr_identity = curr_user.getIdentity();

            if (curr_identity) { // 医师
                back.put("success", "false");
            } else { // 患者
                back.put("success", "true");

                // 解析JSON中的wave
                JSONArray waveJSONArray = request.getJSONArray("wave");
                List<Wave> waveList = waveJSONArray.toJavaList(Wave.class);

                // 插入数据
                for (Wave curr_wave : waveList) {
                    waveService.insertWave(curr_id, curr_wave);
                }
            }
        }

        return back.toString();
    }
}
