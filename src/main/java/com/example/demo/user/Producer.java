package com.example.demo.user;

import com.example.demo.model.Student;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer{

    public void produceMessage() {

//        Student student = new Student("1234", "Serhat Ataman", "Videregåendeskole");
//
//        try {
//            final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
//            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
//
//            Connection connection = connectionFactory.createConnection();
//
//            connection.start();
//
//            // JMS messages are sent and received using a Session. We will
//            // create here a non-transactional session object. If you want
//            // to use transactions you should set the first parameter to 'true'
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            Queue queue = session.createQueue("ObjectQueue");
//
//            MessageProducer messageProducer = session.createProducer(queue);
//
//            ObjectMessage objectMessage = session.createObjectMessage();
//
//            objectMessage.setObject(student.toString());
//
//            messageProducer.send(objectMessage);
//
//            session.close();
//            connection.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
