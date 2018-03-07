package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    @Autowired
    private DemoComponent demo;


    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        demo.demoRun();

    }




}