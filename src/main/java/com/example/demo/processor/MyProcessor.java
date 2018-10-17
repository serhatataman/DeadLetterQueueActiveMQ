package com.example.demo.processor;

import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

public class MyProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("Message ID is: " + exchange.getIn().getMessageId());
//        throw new Exception("Oh noes!!!");
//        System.out.println("File`s class path is: " + exchange.getIn().getHeader(Exchange.FILE_PATH));
//        System.out.println("File`s content is: " + exchange.getIn().getBody(String.class));

//
//        System.out.println("Exception thrown!");
//        throw new CamelException();
    }
}