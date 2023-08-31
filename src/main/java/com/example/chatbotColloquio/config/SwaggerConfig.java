package com.example.chatbotColloquio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "InterviewMe: utilizzo delle API dell'applicazione",
                version = "1.0.0",
                description = "Descrizione di prova",
                termsOfService = "Boh",
                contact = @Contact(
                        name = "Gruppo1: Vincenzo Merola, Giuseppe Bronzellino, Pietro Benedicenti, Rocco Tripodi",
                        email = "gruppo1@email.com"
                ),
                license = @License(
                        name = "licenza di prova",
                        url = "url di prova"
                )
        )
)
public class SwaggerConfig {

}
