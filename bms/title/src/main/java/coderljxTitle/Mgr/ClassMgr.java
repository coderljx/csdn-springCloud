package coderljxTitle.Mgr;

import Pojo.DB.Module;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import coderljxTitle.Dao.ModuleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassMgr {

    @Resource
    private ModuleDao moduleDao;


    public List<Module> queryModule(){
        return moduleDao.queryModule();
    }


    public void addModules(Module modules,String createBy) {
        if (StringUtils.isEmp(modules.getModuleName())){
            throw new TypeException("E000002");
        }
        Module module = moduleDao.queryModuleByName(modules.getModuleName());
        if (module != null && StringUtils.isEmp(module.getModuleName())) {
            throw new TypeException("E000004");
        }
        moduleDao.addModule(modules,createBy);
    }


    public void delModules(Integer id,String modifyBy) {
        moduleDao.delModule(id,modifyBy);
    }


}