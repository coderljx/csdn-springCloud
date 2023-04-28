package file.DB;

import Pojo.DB.Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录用户id 与文件的对应关系
 * 用于记录用户的头像文件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFile extends Base {

    private Integer userId;

    private Integer fileId;
}
