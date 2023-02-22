package com.fmetin.readingisgood.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class CommonProperties {
    @Value("${redis.lock.timeout.seconds:3}")
    private int redisLockTimeoutSeconds;

    @Value("${redis.lock.acquired.seconds:1}")
    private int redisLockAcquiredSeconds;

    public static int REDIS_LOCK_TIMEOUT_SECONDS;
    public static int REDIS_LOCK_ACQUIRED_SECONDS;

    @Value("${redis.lock.timeout.seconds:3}")
    public void setRedisLockTimeoutSeconds(int value) {
        CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS = value;
    }

    @Value("${redis.lock.acquired.seconds:1}")
    public void setRedisLockAcquiredSeconds(int value) {
        CommonProperties.REDIS_LOCK_ACQUIRED_SECONDS = value;
    }
}
