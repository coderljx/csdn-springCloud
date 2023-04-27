package file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication (scanBasePackages = "file")
public class FileApplication {
    private final Logger mylog = LoggerFactory.getLogger(FileApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
