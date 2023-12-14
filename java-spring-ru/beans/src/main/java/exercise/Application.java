package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    @RequestScope
    public Daytime getDaytime() { // имя метода не важно
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() >= 6 && now.getHour() < 22) {
            Day day = new Day();
            day.init();
            return day;
        } else {
            Night night = new Night();
            night.init();
            return night;
        }
    }
    // END
}
