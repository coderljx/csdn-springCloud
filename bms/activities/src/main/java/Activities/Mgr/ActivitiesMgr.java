package Activities.Mgr;

import Activities.Dao.ActivitiesDao;
import Pojo.DB.Activities;
import Pojo.DB.User;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import Pojo.SearchArgs;
import Pojo.SearchArgsMap;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ActivitiesMgr {

    @Resource
    private ActivitiesDao activitiesDao;
    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;


    public List<Activities> getActive(Integer limit,Integer offset){
        List<Activities> all = activitiesDao.getAll(limit, offset);
        return all;
    }


    /**
     * 新增一个活动
     * @param user
     */
    public void addActivities(User user) {
        String res = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String userName = ResponseParse.getUserName(res, "name");

        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        List<Activities> activities = new CopyOnWriteArrayList<>();
        List<SearchArgs.Condition> children = searchArgsMap.getArgsItem().getChildren();
        children.forEach(item -> {
            String field = item.getField();
            String value = item.getValue();
            if (!StringUtils.isEmp(value)) {
                Activities activitie = new Activities(value);
                activities.add(activitie);
            }
        });
        if (activities.size() == 0) {
            return;
        }
        activitiesDao.addActivice(activities,userName);
    }



    public void updateActivities(User user) {
        String res = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String userName = ResponseParse.getUserName(res, "name");

        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        Object object = searchArgsMap.getObject();
        Activities activities1 = JSONObject.parseObject((String) object, Activities.class);
        activitiesDao.updateActivice(activities1,userName);
    }






}
