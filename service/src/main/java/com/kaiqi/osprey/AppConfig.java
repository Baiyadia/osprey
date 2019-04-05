package com.kaiqi.osprey;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "bourses")
public class AppConfig {

    public Map<String, Bourse> bourses;

    @Data
    class Bourse {
        public String name;
        public String url;
    }
}
