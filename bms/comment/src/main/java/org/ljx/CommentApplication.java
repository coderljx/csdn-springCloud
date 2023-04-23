package org.ljx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "org.ljx")
@EnableDiscoveryClient
@EnableFeignClients("Pojo.openFeign")
public class CommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class,args);
    }

    /**
     * 同一个交换机 绑定多个队列 这样就可以根据你交换机的类型来确定是不是广播的了
     */

    @Bean
    public FanoutExchange FanoutExchange() {
        return new FanoutExchange("ex");
    }


    @Bean
    public Queue Queue1() {
        return new Queue("q1");
    }

    @Bean
    public Queue Queue2() {
        return new Queue("q2");
    }




    @Bean
    public Binding binding(FanoutExchange FanoutExchange,Queue Queue1) {
        return BindingBuilder.bind(Queue1).to(FanoutExchange);
    }
    @Bean
    public Binding binding1(FanoutExchange FanoutExchange,Queue Queue2) {
        return BindingBuilder.bind(Queue2).to(FanoutExchange);
    }



}
