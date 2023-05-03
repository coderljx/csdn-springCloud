package file.DB;

import Pojo.DB.Base;
import lombok.Data;

/**
 * 所有的文件都记录再一张表中
 */
@Data
public class FileInfo extends Base {

    private String bucket; // 桶名称

    private String fileName; // 文件名称

    public FileInfo(){}


    public FileInfo(String bucket,String fileName){
        this.bucket = bucket;
        this.fileName = fileName;
    }



}
