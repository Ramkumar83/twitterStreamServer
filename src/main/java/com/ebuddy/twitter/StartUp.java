package com.ebuddy.twitter;

import com.ebuddy.twitter.nio.server.NioHttpServer;
import com.ebuddy.twitter.stream.client.TwitterStreamClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 2:17 AM
 */
public class StartUp {
    public static void main(String[] args) {
        ApplicationContext context =  new ClassPathXmlApplicationContext("Beans.xml");
    }
}
