package coderljxTitle.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextVO {
    private final Logger mylog = LoggerFactory.getLogger(TextVO.class);

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

}
