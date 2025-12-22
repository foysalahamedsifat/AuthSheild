package com.authsheild.infra;

import com.authsheild.infra.config.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class PropsConfig {}
