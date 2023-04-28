package file.MinIo;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;


@Service
public class Minio {
    private final Logger mylog = LoggerFactory.getLogger(Minio.class);

    private final MinioClient minioClient;

    @Autowired
    public Minio(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public List<Bucket> getBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets;
    }

    public Boolean getBucket(String name) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
    }

    public void createBucket(String name) throws Exception{
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
    }


    /**
     * 上传一个文件到 minio
     *
     * @param inputStream
     * @param fileName
     * @param bucket
     * @param name
     * @throws Exception
     */
    public void uploadFile(InputStream inputStream, String fileName, String bucket, String name) throws Exception {
        if (!getBucket(bucket)) createBucket(bucket);
        String c =  MediaTypeFactory.getMediaType(fileName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket)
                .contentType(c)
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
     *
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
//                        .expiry(30, TimeUnit.SECONDS)
                        .method(Method.GET)
                        .build());

        return presignedObjectUrl;
    }



}
