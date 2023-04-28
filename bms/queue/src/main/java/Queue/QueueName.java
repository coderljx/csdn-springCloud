package Queue;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.xml.ws.WebEndpoint;


@Configuration
public class QueueName {
    private final Logger mylog = LoggerFactory.getLogger(QueueName.class);

//    @Bean
//    public Queue queue() {
//        return new Queue("as");
//    }


}
