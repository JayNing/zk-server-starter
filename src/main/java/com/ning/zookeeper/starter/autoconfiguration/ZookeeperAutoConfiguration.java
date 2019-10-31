package com.ning.zookeeper.starter.autoconfiguration;

import com.ning.zookeeper.starter.ZookeeperTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JAY
 * @Date 2019/8/19 19:42
 * @Description TODO
 **/
@EnableConfigurationProperties(ZkProperties.class)
@Configuration
public class ZookeeperAutoConfiguration {

    @Bean
    public ZookeeperTemplate zookeeperTemplate(ZkProperties zkProperties){
        return new ZookeeperTemplate(zkProperties);
    }

}
