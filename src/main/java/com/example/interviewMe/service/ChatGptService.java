package com.example.interviewMe.service;

import com.example.interviewMe.model.Colloquio;
import com.example.interviewMe.model.Domanda;
import com.example.interviewMe.model.Risposta;
import com.example.interviewMe.repository.ColloquioRepository;
import com.example.interviewMe.repository.DomandaRepository;
import com.example.interviewMe.repository.RispostaRepository;
import com.example.interviewMe.repository.UtenteRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGptService implements GptService{
    // private static final String GPT_API_URL = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";

    private String apiKey = ""; // chiave API


    private String model = "gpt-3.5-turbo";

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ColloquioRepository colloquioRepository;
    @Autowired
    private DomandaRepository domandaRepository;

    @Autowired
    private RispostaRepository rispostaRepository;
    @Override
    public Domanda generaDomanda(Colloquio colloquio) {
        colloquioRepository.save(colloquio);
        Domanda domandaGenerata = chiamataApiGenerazioneGpt(colloquio);
        /*
    Domanda domandaGenerata = chiamataApiGenerazioneGpt(colloquio);
        // Aggiungi la domanda generata alla lista di domande del colloquio


        colloquio.getDomandaList().add(domandaGenerata);
        // Salva il colloquio nel database
        colloquioRepository.save(colloquio);


         */
        return domandaGenerata;
    }

    private Domanda chiamataApiGenerazioneGpt(Colloquio colloquio) {
        // Crea un prompt basato sull'argomento del colloquio.....AGGIUNGERE IL NUMERO DI DOMANDE
        String prompt = "Fammi 1 domanda di un colloquio di lavoro su: " + colloquio.getArgomentoColloquio() + " di difficoltà " + colloquio.getDifficolta() + " su 9";
        //risposta generata da GPT utilizzando il prompt
        //DECOMMENTARE DOPO TEST
        String gptResponse = generateGptResponse(prompt);
        // Crea una nuova domanda con il testo generato da GPT


        //TEST: ////////////////////////////
        /*
        String gptResponse  = "Domanda di colloquio generata da GPT";
        ////////////////////////////////////


         */
        Domanda nuovaDomanda = new Domanda(gptResponse);
        nuovaDomanda.setColloquio(colloquio);

        domandaRepository.save(nuovaDomanda);

         return nuovaDomanda;
    }

    @Override
    public String valutaERispondi(Colloquio colloquio, Domanda domanda, Risposta rispostaUtente) {
        // prompt per GPT
        String prompt = "Dammi un parere di una riga della risposta: " + rispostaUtente.getTestoRisposta() + " alla seguente domanda :" + domanda.getTestoDomanda() + " .Infine dammi anche un punteggio da 1 a 9 scrivendolo sempre su una nuova riga e sempre nel formato PUNTEGGIO:punteggio";
        // Ottiene una valutazione da GPT
      //DECOMMENTARE DOPO TEST SALVATAGGIO
        String commento = generateGptResponse(prompt);
        // Estrae il punteggio dalla risposta
      // DECOMMENTARE DOPO TEST SALVATAGGIO (Long)
        int punteggio = estrarrePunteggioDaRispostaGpt(commento);


        //TEST: ////////////////////////////
        /*
        String commento = "Risposta da GPT (Commento)";
        int punteggio = 7;
        ///////////////////////////////////

         */
       // Utente utente = colloquio.getUtente();

        rispostaUtente.setTestoRisposta(rispostaUtente.getTestoRisposta());
        rispostaUtente.setTestoValutazioneGpt(commento);
        rispostaUtente.setPunteggio(punteggio);

        //NUOVO CODICe
        rispostaUtente.setDomanda(domanda);
        rispostaRepository.save(rispostaUtente);

       // rispostaUtente.setUtenteId(colloquio.getUtente());
        ///////
        domanda.setRisposta(rispostaUtente);
        domandaRepository.save(domanda);

        return commento;

    }


    private int estrarrePunteggioDaRispostaGpt(String gptResponse) {
        // Analizza la risposta GPT per estrarre il punteggio
        // Implementa la logica per estrarre il punteggio dal testo restituito da GPT
        // Ad esempio cercare "Punteggio: X" nella risposta estrarre X come punteggio.


        int punteggio = 0;
        String[] lines = gptResponse.split("\n");
        for (String line : lines) {
            if (line.contains("Punteggio: ")) {
                String[] parts = line.split(": ");
                if (parts.length >= 2) {
                    try {
                        punteggio = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        // Gestire l'eccezione se il punteggio non è un numero valido

                    }
                }
                break; // Esci dal ciclo una volta che hai trovato il punteggio
            }
        }

        return punteggio;
    }

    private String generateGptResponse(String prompt) {

        try {
            URL obj = new URL(GPT_API_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response to get generated content
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                String generatedContent = firstChoice.getJSONObject("message").optString("content", "");
                return generatedContent;
            } else {
                throw new RuntimeException("Generated text not found in API response.");
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }



}
