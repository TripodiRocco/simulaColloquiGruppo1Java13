package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Risposta;
import com.example.chatbotColloquio.repository.ColloquioRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGptService implements GptService{
    // private static final String GPT_API_URL = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private String apiKey = "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"; // chiave API
    private String model = "gpt-3.5-turbo";

    @Autowired
    private ColloquioRepository colloquioRepository;


    @Override
    public Domanda generaDomanda(Colloquio colloquio) {
        Domanda domandaGenerata = chiamataApiGenerazioneGpt(colloquio);


        // Aggiungi la domanda generata alla lista di domande del colloquio
        colloquio.getDomandaList().add(domandaGenerata);


        // Salva il colloquio nel database
        colloquioRepository.save(colloquio);


        return domandaGenerata;
    }


    @Override
    public void valutaERispondi(Colloquio colloquio, Domanda domanda, Risposta rispostaUtente) {
        int punteggioDaGpt = chiamataApiValutazioneGpt(rispostaUtente.getTestoRisposta());


        // Aggiorna la risposta utente con il punteggio ottenuto
        rispostaUtente.setPunteggio(punteggioDaGpt);


        // Collega la risposta utente alla domanda
        domanda.setRisposta(rispostaUtente);


        // Salva il colloquio nel repository
        colloquioRepository.save(colloquio);
    }


    private Domanda chiamataApiGenerazioneGpt(Colloquio colloquio) {
        // Crea un prompt basato sull'argomento del colloquio.....AGGIUNGERE IL NUMERO DI DOMANDE
        String prompt = "Simula 3 domanda di un colloquio di lavoro su: " + colloquio.getArgomentoColloquio();

        // Ottieni una risposta generata da GPT utilizzando il prompt
        String gptResponse = generateGptResponse(prompt);

        // Crea una nuova domanda con il testo generato da GPT
        Domanda nuovaDomanda = new Domanda();
        nuovaDomanda.setTestoDomanda(gptResponse);

        return nuovaDomanda;
    }




    //VEDERE QUALE CODICE FUNZIONA MEGLIO TRA I DUE

    /*
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
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            String substring = (response.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);
            return substring;

        }catch(IOException e){
            throw new RuntimeException(e);
        }


    }
*/
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

    private int chiamataApiValutazioneGpt(String rispostaUtente) {
        // Effettua una chiamata all'API di ChatGPT per ottenere il punteggio di valutazione
        // Restituisci il punteggio ottenuto
        return 10; //punteggio;
    }

}
