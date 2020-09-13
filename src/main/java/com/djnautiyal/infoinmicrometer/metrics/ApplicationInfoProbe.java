package com.djnautiyal.infoinmicrometer.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationInfoProbe implements MeterBinder {

    private final byte[] ip;
    private String hostName;
    private final String name;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.id}")
    private String appId;

    private final String description;
    private static final double UP = 1.0;
    //private final Iterable<Tag> tags;

    public ApplicationInfoProbe(){
        InetAddress ip1 = null;
        try {
            ip1 = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            ip1 = null;
        }
        finally{
            this.ip = ip1.getAddress();
            this.hostName = ip1.getHostName();
        }
        this.name = "Application_Info";
        this.description = "Displaying Application Name, Id, Hostname, IP Address";

    }
    @Override
    public void bindTo(final MeterRegistry meterRegistry) {
        Gauge.builder(this.name,this, value ->  UP )
                .description(this.description)
                .register(meterRegistry);
    }

//    protected static Iterable<Tag> tags(){
//
//    }
}
