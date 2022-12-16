package example.Api;


import DB.Log;
import DB.Response;
import DB.User;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import example.EsTemplate;
import example.Pojo.log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LogApi {

    @Resource
    private EsTemplate esTemplate;

    @PostMapping("create")
    public void create(@RequestBody String params){
        try {
            log log = JSONObject.parseObject(params, example.Pojo.log.class);
            esTemplate.save(log);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
