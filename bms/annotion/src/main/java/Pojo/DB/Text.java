package Pojo.DB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Text extends Base{
    private String detail;
    private Integer uid;
    private String coverPhoto; // 封面图片
    private String titleIdStr; // 文章标签id，最多5个标签
    private Character textType; // 1,原创，2,转载 3, 翻译
    private Character releaseForm; // 发布形式 , 1, 全部可见，2，近我可见，3，粉丝可见，4，VIP可见
    private Character contextLevel; // 内容等级，1， 初级，2，中级，3， 高级
    private String imageStr;
    private String coverTitle; // 封面标题
}
