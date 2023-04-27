package file.MinIo;

import file.config.MinIoConfig;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class Minio {
    private final Logger mylog = LoggerFactory.getLogger(Minio.class);

    private final MinioClient minioClient;

    @Autowired
    public Minio(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public List<Bucket> getBucket() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets;
    }


    /**
     * 上传一个文件到 minio
     *
     * @param inputStream
     * @param contentType
     * @param bucket
     * @param name
     * @throws Exception
     */
    public void uploadFile(InputStream inputStream, String contentType, String bucket, String name) throws Exception {
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket)
                .contentType(contentType)
                .object(name)
                .stream(inputStream, inputStream.available(), -1)
                .build());
    }


    /**
     * 从 minio 删除一个文件
     *
     * @param bucket
     * @param name
     * @throws Exception
     */
    public void deleteFile(String bucket, String name) throws Exception {
        minioClient.removeObject(RemoveObjectArgs
                .builder()
                .bucket(bucket)
                .object(name)
                .build());
    }


    /**
     * 获得一个对象的预览地址，用于 img 图片的预览
     * @param bucket
     * @param name
     * @return
     * @throws Exception
     */
    public String getPreUrl(String bucket, String name) throws Exception {
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs
                        .builder()
                        .bucket(bucket)
                        .object(name)
                        .expiry(30, TimeUnit.SECONDS)
                        .method(Method.GET)
                        .build());

        return presignedObjectUrl;
    }

}
