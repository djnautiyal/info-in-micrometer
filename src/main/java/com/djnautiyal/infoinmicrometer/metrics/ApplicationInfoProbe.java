package com.djnautiyal.infoinmicrometer.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationInfoProbe implements MeterBinder {

    private static final double UP = 1.0;
    private static final double DOWN = 0.0;
    private final String ip;
    private final String name;
    private final String description;
    private String hostName;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.id}")
    private String appId;
    //private final Iterable<Tag> tags;

    public ApplicationInfoProbe() {
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
    public void bindTo(final MeterRegistry meterRegistry) {
        Gauge.builder(this.name, this, value -> this.ip.isEmpty() ? UP : DOWN)
                .description(this.description)
                .tags(this.tags())
                .register(meterRegistry);
    }

    private Iterable<Tag> tags() {
        return Tags.of(Tag.of("IpAddress", this.ip.toString()), Tag.of("HostName", this.hostName),
                Tag.of("Application-Name", this.appName), Tag.of("Application-Id", this.appId));
    }
}
