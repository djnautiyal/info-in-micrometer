package com.djnautiyal.infoinmicrometer.config;

import com.djnautiyal.infoinmicrometer.metrics.ApplicationInfoProbe;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class InfoConfig {

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    ApplicationInfoProbe applicationInfoProbe(){
        return new ApplicationInfoProbe();
    }
}
