package tw.appworks.school.yichien.enabling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class EnablingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnablingApplication.class, args);
    }

}
