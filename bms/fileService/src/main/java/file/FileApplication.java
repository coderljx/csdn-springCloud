package file;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication (scanBasePackages = "file")
@EnableDiscoveryClient
@MapperScan({"file.Dao"})
@EnableFeignClients("Pojo.openFeign") // 设置feign 扫描的包路径
public class FileApplication {
    private final Logger mylog = LoggerFactory.getLogger(FileApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
