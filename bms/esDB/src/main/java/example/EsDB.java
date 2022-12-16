package example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "example")
public class EsDB {

    public static void main(String[] args) {
        SpringApplication.run(EsDB.class, args);
    }



}
