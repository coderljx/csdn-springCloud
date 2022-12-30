package coderljxTitle.Mgr;

import Pojo.DB.Module;
import coderljxTitle.Dao.ModuleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleMgr {

    @Resource
    private ModuleDao moduleDao;


    public List<Module> queryModule(){
        return moduleDao.queryModule();
    }


    public void addModules(Module modules,String createBy) {
        moduleDao.addModule(modules,createBy);
    }


}
