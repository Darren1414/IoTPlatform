package com.starfz.connector;

import com.starfz.connector.server.Server;
import com.starfz.connector.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        Server server = (Server)SpringUtil.getBean("connector-server");
        server.start();
    }

}
