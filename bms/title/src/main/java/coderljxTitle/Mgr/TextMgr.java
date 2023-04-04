package coderljxTitle.Mgr;

import Pojo.DB.Advertisement;
import Pojo.DB.Text;
import Pojo.DB.User;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.ThreadUtils;
import Pojo.LjxUtils.UUID;
import coderljxTitle.Dao.TextDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class TextMgr {

    @Resource
    private TextDao textDao;
    @Value("${file.exoprt}")
    private String fileExport;

    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;

    public List<Text> getUserText(Integer userid,Integer id) {
        return textDao.getUserText(userid,id);
    }

    /**
     * 给用户新增一个文章
     *
     * @param detail       文章内容
     * @param uid          用户id
     * @param coverPhoto   封面图片文件
     * @param titleIdStr   文章标签id
     * @param textType     文章类型
     * @param releaseForm  发布形式
     * @param contextLevel 内容等级
     * @param file         文章中上传的文件
     * @param coverTitle   封面标题
     */
    public void addUserText(String appid, String detail, Integer uid, MultipartFile[] coverPhoto,
                            String titleIdStr, Character textType,
                            Character releaseForm, Character contextLevel,
                            MultipartFile[] file, String coverTitle)
            throws ExecutionException, InterruptedException {
        /**
         * 从线程池中拿两个线程分别执行文件保存操作，比之前一个线程运行的要快
         */
        String coverPhotoStr = ThreadUtils.invoke(() -> saveFIle(coverPhoto)).get();
        String imageStr = ThreadUtils.invoke(() -> saveFIle(file)).get();
        Text text = new Text(detail, uid, coverPhotoStr, titleIdStr, textType, releaseForm, contextLevel, imageStr, coverTitle);
        String userByid = userOpenFeign.getUserByid(Integer.parseInt(appid), uid);
        String userName = ResponseParse.getUserName(userByid, "name");
        text.setCreateBy(userName);
        textDao.addUserText(text);
    }

    /**
     * 删除用户的文章
     *
     * @param id
     * @param user
     */
    public void deleteUserText(Integer id, User user) {
        String userByid = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        String name = ResponseParse.getUserName(userByid, "name");
        textDao.deleteUserText(id, name);
    }


    /**
     * 保存文件
     *
     * @param files
     * @return
     * @throws IOException
     */
    private String saveFIle(MultipartFile[] files) throws IOException {
        StringBuilder str = new StringBuilder();
        if ((files == null || files.length == 0)) {
            return "";
        }
        for (MultipartFile multipartFile : files) {
            String contentType = Objects.requireNonNull(
                    multipartFile.getContentType()).substring(0,
                    multipartFile.getContentType().indexOf("application/"));
            String name = UUID.getUUID() + "." + contentType;
            String filePath = fileExport + File.separator + name;
            str.append(name).append(",");
            multipartFile.transferTo(new File(filePath));
        }
        return str.substring(0, str.length() - 1);
    }


    /**
     * 获取头条，热点，广告等信息
     */
    public void getAvdText() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Advertisement> adv = textDao.getAdv(list);
        adv.forEach(item -> {
            System.out.println(item.getFileUrl());
        });
    }


    /**
     * 获得关注的人发布的文章
     *
     * @param userid
     */
    public List<Text> getFollowedText(Integer userid) {
        List<Text> followedText = textDao.getFollowedText(userid);
        return followedText;
    }


}
