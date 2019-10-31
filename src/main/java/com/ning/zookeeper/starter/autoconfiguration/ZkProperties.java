package com.ning.zookeeper.starter.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author JAY
 * @Date 2019/8/19 19:48
 * @Description TODO
 **/
@ConfigurationProperties(prefix = "zk.server")
public class ZkProperties {
    private String address;
    private int sessionTimeOut;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }
}
