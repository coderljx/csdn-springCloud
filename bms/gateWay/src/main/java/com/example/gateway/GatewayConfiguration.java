package com.example.gateway;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
//    private final List<ViewResolver> viewResolvers;
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//
//    public GatewayConfiguration(ObjectProvider<List<ViewResolver>>
//                                        viewResolversProvider,
//                                ServerCodecConfigurer serverCodecConfigurer) {
//        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//    // 初始化一个限流的过滤器
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public GlobalFilter sentinelGatewayFilter() {
//        return new SentinelGatewayFilter();
//    }
//    // 配置初始化的限流参数
//    @PostConstruct
//    public void initGatewayRules() {
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(
//                new GatewayFlowRule("userService") //资源名称,对应路由id
//                        .setCount(10) // 限流阈值
//                        .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
//        );
//        rules.add(
//                new GatewayFlowRule("titleService") //资源名称,对应路由id
//                        .setCount(10) // 限流阈值
//                        .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
//        );
//        GatewayRuleManager.loadRules(rules);
//    }
//    // 配置限流的异常处理器
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers,
//                serverCodecConfigurer);
//    }
//
//    // 自定义限流异常页面
//    @PostConstruct
//    public void initBlockHandlers() {
//        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
//            Map map = new HashMap<>();
//            map.put("code", -101);
//            map.put("message", "接口被限流了");
//            return ServerResponse.status(HttpStatus.OK).
//                    contentType(MediaType.APPLICATION_JSON_UTF8).
//                    body(BodyInserters.fromObject(map));
//        };
//        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
//    }
}
