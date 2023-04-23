package coderljxTitle.Mgr;

import Pojo.DB.TextDetail;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxUtils.MapUtils;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import Pojo.SearchArgs;
import Pojo.SearchArgsMap;
import coderljxTitle.Dao.TextDetailDao;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TextDetailMgr {

    @Resource
    private TextDetailDao textDetailDao;
    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;


    /**
     * 根据接口的传参 远程调用获得用户名
     * @param user
     * @return
     */
    public String getUserName(User user) {
        String userByid = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        return ResponseParse.getUserName(userByid, "name");
    }

    /**
     * 新增一个文章点赞信息
     * @param user
     * @throws IllegalAccessException
     */
    public void addTextDetail(User user) throws Exception {
        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        List<SearchArgs.Condition> children = searchArgsMap.getArgsItem().getChildren();
        if (StringUtils.isListEmp(children)) {
            return;
        }
        SearchArgs.Condition conditions = children.get(0);
        // Json 对象
        String value = conditions.getValue();
        TextDetail textDetail = JSONObject.parseObject(value, TextDetail.class);
        MapUtils.NotNullImp(textDetail);
        String userName = getUserName(user);
        textDetail.setCreateBy(userName);
        textDetailDao.addTextDetail(textDetail);
    }



    /**
     * update 一个 TextDetail
     *
     * @param user
     */
    public void updateTextDetail(User user) throws Exception {
        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        List<SearchArgs.Condition> children = searchArgsMap.getArgsItem().getChildren();
        if (StringUtils.isListEmp(children)) {
            return;
        }

        SearchArgs.Condition conditions = children.get(0);
        // Json 对象
        String value = conditions.getValue();
        TextDetail textDetail = JSONObject.parseObject(value, TextDetail.class);
        MapUtils.NotNullImp(textDetail);
        String userName = getUserName(user);
        textDetail.setModifyBy(userName);
        textDetailDao.updateTextDetail(textDetail);
    }


    /**
     * 根据用户的id 和文章id 删除该用户对该文章的操作
     * 只有该用户操作过后 才能调用这个接口
     * @param user
     * @param textId
     */
    public void delTextDetail(User user,Integer textId) {
        if (textId == null || textId <= 0) {
            throw new DataException("textId");
        }
        String userName = getUserName(user);
        textDetailDao.delTextDetail(textId,userName,user.getId());
    }






}
