package io.getarrays.server;

import io.getarrays.server.model.Server;
import io.getarrays.server.repo.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static io.getarrays.server.enumeration.Status.*;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null, "10.7.14.99", "Ubuntu Linux", "16 GB", "Personal PC", "http://localhost/server/image/1.png", SERVER_UP));
            serverRepository.save(new Server(null, "10.61.3.91", "Fedora Linux", "32 GB", "Dell Tower", "http://localhost/server/image/2.png", SERVER_DOWN));
            serverRepository.save(new Server(null, "10.7.12.138", "MS 2008", "64 GB", "Web Server", "http://localhost/server/image/3.png", SERVER_UP));
            serverRepository.save(new Server(null, "18.203.57.69", "Red Hat Enterprise Linux", "16 GB", "Mail Server", "http://localhost/server/image/4.png", SERVER_UP));
        };
    }

}
