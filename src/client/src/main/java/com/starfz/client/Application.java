package com.starfz.client;


import com.starfz.client.instance.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        final String host = "127.0.0.1";
        final int port = 5200;
        new Client(host, port).start();
    }

}
