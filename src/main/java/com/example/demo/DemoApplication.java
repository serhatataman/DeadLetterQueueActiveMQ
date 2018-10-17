package com.example.demo;

import com.example.demo.router.MyRouter;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.camel.component.ActiveMQEndpoint;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.apache.activemq.store.kahadb.plist.PListStoreImpl;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.URI;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public BrokerService setupBroker() {

        CamelContext camelContext = new DefaultCamelContext();
        BrokerService brokerService = new BrokerService();
        MyRouter router = new MyRouter();
//        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();

        try {
            brokerService.addConnector(brokerUrl);
            brokerService.setUseShutdownHook(false);
            brokerService.setAdvisorySupport(true);
            brokerService.setEnableStatistics(true);
            brokerService.setPersistenceAdapter(createKahaDBPersistenceAdapter());
            brokerService.setTempDataStore(createKahaDBTempDataStore());

//            final TransportConnector connector = new TransportConnector();
//            connector.setUri(new URI(brokerUrl));
//            brokerService.addConnector(connector);

            //setting persistence messages
            //brokerService.setPersistent(true);

//            System.out.println("Broker URL is: " + brokerUrl);
//            System.out.println("Broker name: " + brokerService.getBrokerName());
//            System.out.println("Broker`s data directory: " + brokerService.getBrokerDataDirectory().toString());
//            System.out.println("Data persistence: " + brokerService.isPersistent());
//            System.out.println("Using JMX: " + brokerService.isUseJmx());

            brokerService.start();
            camelContext.start();

            camelContext.addRoutes(router);

            Thread.sleep(6000);

//            System.out.println("Queue size is: " + brokerService.checkQueueSize("MyQueue"));
//            System.out.println("Consumer system usage is: " + brokerService.getConsumerSystemUsage().toString());
//            System.out.println("Data directory file: " + brokerService.getDataDirectoryFile().toString());

            brokerService.stop();
            camelContext.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return brokerService;
    }

    private KahaDBPersistenceAdapter createKahaDBPersistenceAdapter() {
        final KahaDBPersistenceAdapter kahaDBPersistenceAdapter = new KahaDBPersistenceAdapter();
        kahaDBPersistenceAdapter.setDirectory(new File("C:\\JavaProjects\\tmp", "activemq/data"));
        kahaDBPersistenceAdapter.setCompactAcksIgnoresStoreGrowth(true);
        kahaDBPersistenceAdapter.setCompactAcksAfterNoGC(5);
        kahaDBPersistenceAdapter.setUseLock(false);
        return kahaDBPersistenceAdapter;
    }

    private PListStoreImpl createKahaDBTempDataStore() {
        final PListStoreImpl tempKahaDBStore = new PListStoreImpl();
        tempKahaDBStore.setDirectory(new File("C:\\JavaProjects\\tmp", "activemq/tmp"));
        return tempKahaDBStore;
    }
}