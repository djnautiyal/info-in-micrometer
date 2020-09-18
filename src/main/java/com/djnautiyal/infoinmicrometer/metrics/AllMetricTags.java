package com.djnautiyal.infoinmicrometer.metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class AllMetricTags implements MeterFilter {

    private final String ip;
    private final String name;
    private final String description;
    private String hostName;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.id}")
    private String appId;

    public AllMetricTags(){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Unable to get Inetaddress");
        }

        this.ip = inetAddress == null ? null : inetAddress.getHostAddress();
        this.hostName = inetAddress == null ? null : inetAddress.getHostName();

        this.name = "Application_Info";
        this.description = "Displaying Application Name, Id, Hostname, IP Address";
    }

    @Override
    public Meter.Id map(Meter.Id id) {
        return id.withTags(Tags.concat(
                Tags.of(
                        Tag.of("IpAddress", this.ip.toString()), Tag.of("HostName", this.hostName),
                        Tag.of("Application-Name", this.appName), Tag.of("Application-Id", this.appId)
                )
        ));
    }
}
