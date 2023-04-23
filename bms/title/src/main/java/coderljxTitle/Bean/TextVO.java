package coderljxTitle.Bean;

import Pojo.DB.BeanVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextVO extends BeanVo {

    private Integer id;

    private String detail;

    private Integer uid;

    /**
     * // 封面图片
     */
    private String coverPhoto;

    /**
     * // 文章标签id，最多5个标签
     */
    private String titleIdStr;

    /**
     * // 1,原创，2,转载 3, 翻译
     */
    private Character textType;

    /**
     * 发布形式 , 1, 全部可见，2，近我可见，3，粉丝可见，4，VIP可见
     */
    private Character releaseForm;

    /**
     * // 内容等级，1， 初级，2，中级，3， 高级
     */
    private Character contextLevel;

    private String imageStr;

    /**
     * // 封面标题
     */
    private String coverTitle;


    private String userName;


    private Integer like; //点赞数量

    private Integer stepOn; // 点踩数量

    /**
     * 如果当前请求的时候(text/get 接口) 前端用户有登录
     * 则会查询当前请求的用户有没有点过赞或者点过踩
     */
    private Character reqUserAction;

}
