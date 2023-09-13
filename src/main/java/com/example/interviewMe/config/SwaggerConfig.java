package com.example.interviewMe.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "InterviewMe: Come utilizzare le API dell'applicazione",
                version = "1.0.0",
                description = "Questa pagina permette di capire come vengono impiegate le API e di interagire con esse al fine di creare un utente e/o una lista di utenti, modificarli, eliminarli e ritrovarli. Inoltre è possibile gestire la comunicazione a colloquio con l'alternarsi di domande e risposte.",
                termsOfService = "Progetto creato a scopo accademico",
                contact = @Contact(
                        name = "Vincenzo Merola, Giuseppe Bronzellino, Pietro Benedicenti, Rocco Tripodi",
                        url = "https://github.com/TripodiRocco/simulaColloquiGruppo1Java13.git"
                )
        )
)
public class SwaggerConfig {

}
