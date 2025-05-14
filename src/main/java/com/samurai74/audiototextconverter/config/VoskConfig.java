package com.samurai74.audiototextconverter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vosk.Model;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class VoskConfig {
    @Bean
    public Model voskModel() throws IOException {
        String modelPath = Paths.get("vosk-model-small-en-us-0.15").toAbsolutePath().toString();
        log.info("modelPath  ->"+modelPath);
        return new Model(modelPath);
    }
}
