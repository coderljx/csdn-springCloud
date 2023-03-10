package example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "example")
@EnableDiscoveryClient
public class EsDB {

    public static void main(String[] args) {
        SpringApplication.run(EsDB.class, args);
    }



}
