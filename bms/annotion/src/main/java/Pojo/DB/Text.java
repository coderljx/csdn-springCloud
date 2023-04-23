package Pojo.DB;

import lombok.Data;
@Data
public class Text extends Base{

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

    private Integer view; // 文章的查看次数

    public Text() {}

    public Text(String detail, Integer uid, String coverPhoto, String titleIdStr, Character textType, Character releaseForm, Character contextLevel, String imageStr, String coverTitle) {
        this.detail = detail;
        this.uid = uid;
        this.coverPhoto = coverPhoto;
        this.titleIdStr = titleIdStr;
        this.textType = textType;
        this.releaseForm = releaseForm;
        this.contextLevel = contextLevel;
        this.imageStr = imageStr;
        this.coverTitle = coverTitle;
    }
}
