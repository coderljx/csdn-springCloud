package file.DB;

import Pojo.DB.Base;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有的文件都记录再一张表中
 */
@Data
public class FileInfo extends Base {

    private String bucket; // 桶名称

    private String fileName; // 文件名称
}
