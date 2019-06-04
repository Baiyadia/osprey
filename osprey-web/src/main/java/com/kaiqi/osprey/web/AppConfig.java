package com.kaiqi.osprey.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "osprey.bourses")
    public HashMap<String, HashMap<String, String>> bourseConfig() {
        return new HashMap();
    }

    @Bean
    @ConfigurationProperties(prefix = "dao")
    public ServerConfig serverConfig() {
        return new ServerConfig();
    }

    @Data
    public class ServerConfig {

        public String address;
        public String port;
    }

    @Data
    public class Bourse {

        public String name;
        public String url;
    }
}
