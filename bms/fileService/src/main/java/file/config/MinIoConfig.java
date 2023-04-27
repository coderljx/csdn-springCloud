package file.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinIoConfig {
    private final Logger mylog = LoggerFactory.getLogger(MinIoConfig.class);

    @Value ("${minio.url}")
    private String url;
    @Value ("${minio.accessKey}")
    private String accessKey;
    @Value ("${minio.secretKey}")
    private String secretKey;

    /**
     * 根据配置 创建一个client
     */
    @Bean
    public MinioClient createMinIoClient() {
     MinioClient build = MinioClient.builder().endpoint(url).credentials(accessKey,secretKey).build();
     return build;
    }

}
