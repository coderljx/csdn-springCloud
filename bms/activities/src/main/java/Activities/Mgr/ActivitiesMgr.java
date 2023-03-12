package Activities.Mgr;

import Activities.Dao.ActivitiesDao;
import Pojo.DB.Activities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivitiesMgr {

    @Resource
    private ActivitiesDao activitiesDao;


    public List<Activities> getActive(Integer limit,Integer offset){
        List<Activities> all = activitiesDao.getAll(limit, offset);
        return all;
    }



}
