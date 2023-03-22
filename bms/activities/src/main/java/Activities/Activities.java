package Activities;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "Activities")
@EnableDiscoveryClient
@MapperScan({"Activities.Dao","com.codeljxUser.Dao"})
@EnableFeignClients("Pojo.openFeign")
public class Activities {
    public static void main(String[] args) {
        SpringApplication.run(Activities.class,args);
    }
}
