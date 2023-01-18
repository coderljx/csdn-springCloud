package example.Api;


import com.alibaba.fastjson2.JSONObject;
import example.EsTemplate;
import example.Pojo.log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LogApi {

    @Resource
    private EsTemplate esTemplate;

    @PostMapping("create")
    public void create(@RequestBody String params){
        try {
            log log = JSONObject.parseObject(params, example.Pojo.log.class);
            log.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            esTemplate.save(log);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
