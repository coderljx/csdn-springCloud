package WeChat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = {"WeChat"})
public class WeChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class,args);
    }

    @Resource
    private WeChat.Ws.webServer webServer;

    // spirng启动后 会执行这个方法， 跟    PostConstruct 是一个效果
    @Override
    public void run(String... args) throws Exception {
        webServer.stack();
    }
}
