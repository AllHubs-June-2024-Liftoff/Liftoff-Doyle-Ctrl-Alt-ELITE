package org.launchcode.BingeBuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "org.launchcode.BingeBuddy.models")
public class BingeBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingeBuddyApplication.class, args);

    }
}



