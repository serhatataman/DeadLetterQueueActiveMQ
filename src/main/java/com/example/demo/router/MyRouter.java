package com.example.demo.router;

import com.example.demo.processor.MyProcessor;
import com.example.demo.transformer.MyTransformer;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRouter extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(MyRouter.class);

    @Override
    public void configure() throws Exception {

        /*
        * I have a text file in my C:/JavaProjects/tmp/input directory.
        * I would like to take the file into an internal queue,
        * Produce an unsuccessful delivery with calling .rollback()
        * then the message should forward to deadLetterQueue
        * after that I will take the data from deadLetterQueue and put it to C:/JavaProjects/tmp/output directory.
        * */

        onException(Exception.class)
                .log(LoggingLevel.ERROR, logger, "THE ERROR IS: " + exceptionMessage().toString());

        errorHandler(deadLetterChannel("activemq:queue:deadLetterQueue")
                        .useOriginalMessage()
                        //.backOffMultiplier(2)
                        .redeliveryDelay(100)
                        .maximumRedeliveries(5)
                        .log("The message`s been sent to the dead letter queue!"));

        /*
        * Receiving data to an internal queue "incoming",
        * then calling rollback() to send the info from internal queue to dead Letter queue
        * */

        from("file:/JavaProjects/tmp/input?noop=true")
                .to("activemq:queue:incoming");
        from("activemq:queue:incoming")
                .process(new MyProcessor())
                .bean(new MyTransformer())//transform the file
                //.setHeader(Exchange.HTTP_METHOD, constant("POST"))
                //.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.FILE_NAME, constant("outputJson.json"))
                .rollback("Lets go to the DEAD QUEUE!")
                .to("activemq:queue:MyQueue");


        //Getting data from Dead Letter Queue and put it ro our output folder

        from(deadLetterChannel("activemq:queue:deadLetterQueue").getDeadLetterUri())
                .process(new MyProcessor())
                .to("file:/JavaProjects/tmp/output");
    }
}