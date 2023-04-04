package coderljxTitle.Mgr;

import Pojo.DB.Title;
import Pojo.DB.User;
import Pojo.LjxUtils.ResponseParse;
import Pojo.SearchArgs;
import Pojo.SearchArgsMap;
import coderljxTitle.Dao.TitleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TitleMgr {

    @Resource
    private TitleDao titleDao;
    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;

    public List<Title> getTitleByModule(Integer moduleId) {
        List<Title> titleByModule = titleDao.getTitleByModule(moduleId);
        return titleByModule;
    }


    public void addTitle(User user,Integer moduleId) {
        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        List<SearchArgs.Condition> children = searchArgsMap.getArgsItem().getChildren();
        if (children.size() == 0) {
            return;
        }
        String userByid = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String userName = ResponseParse.getUserName(userByid, "name");
        List<Title> titleList = new CopyOnWriteArrayList<>();
        children.forEach(item -> {
            Title title = new Title();
            title.setTitleName(item.getValue());
            title.setModuleId(moduleId);
            titleList.add(title);
        });
        titleDao.addTitle(titleList,userName);
    }



}
