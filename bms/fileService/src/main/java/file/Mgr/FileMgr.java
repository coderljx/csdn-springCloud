package file.Mgr;

import file.MinIo.Minio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class FileMgr {
    private final Logger mylog = LoggerFactory.getLogger(FileMgr.class);

    @Resource
    private Minio minio;


    public void upload() {

    }


    public String getPreUrl(String bucket, String name) throws Exception{
        return minio.getPreUrl(bucket, name);
    }


}
