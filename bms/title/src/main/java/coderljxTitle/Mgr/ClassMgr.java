package coderljxTitle.Mgr;

import Pojo.DB.Module;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import coderljxTitle.Dao.AdvertiDao;
import coderljxTitle.Dao.ModuleDao;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClassMgr {

    @Resource
    private ModuleDao moduleDao;
    @Resource
    private AdvertiDao advertiDao;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;


    public List<Module> queryModule(){
        return moduleDao.queryModule();
    }


    /**
     * 新增一个系统模块
     * @param modules
     */
    public void addModules(Module modules, User user,String appid) {
        if (StringUtils.isEmp(modules.getModuleName())){
            throw new TypeException("E000002");
        }
        Module module = moduleDao.queryModuleByName(modules.getModuleName());
        if (module != null && StringUtils.isEmp(module.getModuleName())) {
            throw new TypeException("E000004");
        }
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://userService/api/user/getUserStatus/" + appid + "?userid=" + user.getId(), String.class);
        if (!StringUtils.isEmp(forEntity.getBody())) {
            throw new TypeException("E000001_01");
        }
        moduleDao.addModule(modules,user.getName());
    }


    /**
     * 删除一个系统模块
     * @param id
     * @param modifyBy
     */
    public void delModules(Integer id,String modifyBy) {
        moduleDao.delModule(id,modifyBy);
    }


    /**
     * 获取广告图片链接地址
     * @return
     */
    public List<String> getLinks() {
        List<String> advLinks = advertiDao.getAdvLinks();
        List<String> voList = new LinkedList<>();
        List<ServiceInstance> titleService = discoveryClient.getInstances("esService");
        if (!StringUtils.isListEmp(titleService)) {
            String url = titleService.get(0).getUri().toString();
            for (String advLink : advLinks) {
                voList.add(url + "/es/img/viewImg/" + advLink);
            }
        }
        return voList;
    }



}
