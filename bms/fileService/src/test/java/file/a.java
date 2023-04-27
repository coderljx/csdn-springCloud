package file;

import Pojo.LjxUtils.UUID;
import file.MinIo.Minio;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;


@SpringBootTest
public class a {
    private final Logger mylog = LoggerFactory.getLogger(a.class);

    @Resource
    private Minio minio;


    @Test
    void a () throws Exception{

        File file = new File("C:\\Users\\Brother\\Desktop\\1.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        minio.uploadFile(inputStream,"1.jpg", "img", UUID.getUUID());

    }

    @Test
    void a1 () throws Exception{

        String preUrl = minio.getPreUrl("img", "41a2adcbe77d4e7598e40db66422015f");
        System.out.println(preUrl);

    }


    public static void main(String[] args) {

    }



}
