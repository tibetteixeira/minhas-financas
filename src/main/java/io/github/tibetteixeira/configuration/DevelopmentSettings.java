package io.github.tibetteixeira.configuration;

import io.github.tibetteixeira.annotations.Development;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
@Slf4j
public class DevelopmentSettings {

    @Bean
    public CommandLineRunner initialize() {
        return args -> log.info("Aplicação em ambiente de desenvolvimento disponível.");
    }
}
