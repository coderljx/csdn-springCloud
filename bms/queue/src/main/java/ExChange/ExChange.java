package ExChange;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义交换机
 */
@Configuration
public class ExChange {
    private final Logger mylog = LoggerFactory.getLogger(ExChange.class);

//    @Bean
//    public FanoutExchange fin() {
//        return new FanoutExchange("");
//    }

}
