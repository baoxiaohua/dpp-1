package com.hh.sd.core.config.custom;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KylinConfig {

    @Value("${kylin.jdbc-url}")
    private String jdbcUrl;

    @Value("${kylin.user}")
    private String user;

    @Value("${kylin.password}")
    private String password;
}
