package io.github.tibetteixeira;

import io.github.tibetteixeira.annotations.Development;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@Development
public class DevelopmentSettings {

    private static final Logger LOGGER = Logger.getLogger(DevelopmentSettings.class.getName());

    @Bean
    public CommandLineRunner initialize() {
        return args -> LOGGER.info("Aplicação em ambiente de desenvolvimento disponível.");
    }
}
