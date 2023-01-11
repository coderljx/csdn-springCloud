package coderljxTitle;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"coderljxTitle"})
@MapperScan({"coderljxTitle.Dao","com.codeljxUser.Dao"})
@EnableDiscoveryClient
public class TitleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitleApplication.class,args);
    }
}
