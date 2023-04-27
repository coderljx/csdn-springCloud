package file;

import file.MinIo.Minio;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


@SpringBootTest
public class a {
    private final Logger mylog = LoggerFactory.getLogger(a.class);

    @Resource
    private Minio minio;


    @Test
    void a () throws Exception{

        File file = new File("/Users/brother/Desktop/apache-maven-3.8.3/a.jpeg");
        FileInputStream inputStream = new FileInputStream(file);
        minio.uploadFile(inputStream,"jpeg", "test","re");

    }

    @Test
    void a1 () throws Exception{

        String preUrl = minio.getPreUrl("test", "re");
        System.out.println(preUrl);

    }


}
