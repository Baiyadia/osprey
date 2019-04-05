package com.kaiqi.osprey;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "data")
public class ServerConfig {

    public String address;
    public String port;
}
