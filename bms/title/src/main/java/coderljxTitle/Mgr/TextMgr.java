package coderljxTitle.Mgr;

import Pojo.DB.Advertisement;
import Pojo.DB.Text;
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

@Service
public class TextMgr {

    @Resource
    private TextDao textDao;
    @Value("${file.exoprt}")
    private String fileExport;

    public List<Text> getUserText(Integer userid){
        return textDao.getUserText(userid);
    }

    /**
     * 给用户新增一个文章
     * @param detail 文章内容
     * @param uid 用户id
     * @param coverPhoto 封面图片文件
     * @param titleIdStr 文章标签id
     * @param textType 文章类型
     * @param releaseForm 发布形式
     * @param contextLevel 内容等级
     * @param file 文章中上传的文件
     * @param coverTitle 封面标题
     */
    public void addUserText(String detail, Integer uid, MultipartFile[] coverPhoto, String titleIdStr, Character textType,
                            Character releaseForm, Character contextLevel,MultipartFile[] file,String coverTitle) throws IOException {
        String coverPhotoStr = this.saveFIle(coverPhoto);
        String imageStr = this.saveFIle(file);
        Text text = new Text(detail,uid, coverPhotoStr,titleIdStr,textType,releaseForm,contextLevel, imageStr,coverTitle);
        textDao.addUserText(text);
    }

    /**
     * 删除用户的文章
     * @param id
     * @param modifyBY
     */
    public void deleteUserText(Integer id, String modifyBY){
        textDao.deleteUserText(id, modifyBY);
    }

    private String saveFIle(MultipartFile[] files) throws IOException {
        StringBuilder str = new StringBuilder();
        if (!(files == null || files.length == 0)) {
            for (MultipartFile multipartFile : files) {
                String contentType = Objects.requireNonNull(multipartFile.getContentType()).substring(0,
                        multipartFile.getContentType().indexOf("application/"));
                String filePath = fileExport + File.separator + UUID.getUUID() + "." + contentType;
                str.append(filePath).append(",");
                multipartFile.transferTo(new File(filePath));
            }
        }
        return str.substring(0,str.length() -1);
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



}
