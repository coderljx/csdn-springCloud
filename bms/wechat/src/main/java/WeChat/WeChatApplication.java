package WeChat;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@SpringBootApplication(scanBasePackages = {"WeChat"})
@EnableScheduling
public class WeChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class,args);

    }

    @Resource
    private WeChat.Ws.webServer webServer;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    // spirng启动后 会执行这个方法， 跟    PostConstruct 是一个效果
    @Override
    public void run(String... args) throws Exception {
        webServer.stack();

        /**
         * 开启事件监听
         * 1. 写入某一个类型的变量，
         * 2. 使用@EventListener 注解监听这个类型的注解
         * 3. 完成事件的发布于订阅
         */
        applicationEventPublisher.publishEvent("koko");
    }


}

@Service
class ra {

    @EventListener(classes = String.class)
    public void a (String name){
        System.out.println("事件监听成功,获取的值是：" + name);
    }

//    @Scheduled(cron = "3 0 0 * * ?")
//    @Scheduled(fixedRate = 1000) // 每秒钟执行一次
    public void aa(){
        System.out.println(123);
    }


    public static void main(String[] args) {



    }


}