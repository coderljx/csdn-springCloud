package coderljxTitle;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"coderljxTitle","LjxRedis","an"})
@MapperScan({"coderljxTitle.Dao","com.codeljxUser.Dao"})
public class TitleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitleApplication.class,args);
    }
}
