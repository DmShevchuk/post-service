//package ru.dmmitrii.post.config;
//
//import io.github.resilience4j.ratelimiter.RateLimiter;
//import io.github.resilience4j.ratelimiter.RateLimiterConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//@Configuration
//public class RateLimiterCustomConfig {
//
//    @Bean
//    public RateLimiter customRateLimiter() {
//        var config = RateLimiterConfig.custom()
//                .limitForPeriod(2)
//                .limitRefreshPeriod(Duration.ofMinutes(1))
//                .timeoutDuration(Duration.ofMillis(500))
//                .build();
//
//        return RateLimiter.of("myRateLimiter", config);
//    }
//}
//
