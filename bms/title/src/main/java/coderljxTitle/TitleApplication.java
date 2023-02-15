package coderljxTitle;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"coderljxTitle"})
@MapperScan({"coderljxTitle.Dao","com.codeljxUser.Dao"})
@EnableFeignClients // openfeign 服务调用
//@EnableDiscoveryClient // nacos
public class TitleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitleApplication.class,args);
    }
}
