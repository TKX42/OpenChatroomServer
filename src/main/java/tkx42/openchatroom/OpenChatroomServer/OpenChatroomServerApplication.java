package tkx42.openchatroom.OpenChatroomServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpenChatroomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenChatroomServerApplication.class, args);
    }

}
