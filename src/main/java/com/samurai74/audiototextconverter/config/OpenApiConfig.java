package com.samurai74.audiototextconverter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info=@Info(
                contact = @Contact(
                        name="Mehrab Hasan",
                        email="2016mehrab@gmail.com"
                ),
                description = "OpenAPI specification for Audio to Text Converter",
                title = "Audio to Text Converter API",
                version= "1.0.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url="http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url="http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
