package fun.crimiwar.intellstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IntellStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntellStoreApplication.class, args);
    }

}
