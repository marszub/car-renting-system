package pl.agh.edu.cardatabase.config;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Communicator communicator() {
        return Util.initialize(new String[]{"--Ice.Config=config.client"});
    }
}
