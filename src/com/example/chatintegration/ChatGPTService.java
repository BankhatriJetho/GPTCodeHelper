package com.example.chatintegration;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;


public class ChatGPTService {
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "your-api-key"; // Replace with your OpenAI API key

    public String generateCode(String prompt) {
        OkHttpClient client = new OkHttpClient();

        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo"); // change the model when needed.
        json.add("messages", new JsonArray());
        json.getAsJsonArray("messages").add(message);
        json.addProperty("max_tokens", 150);

        RequestBody body = RequestBody.create(
            json.toString(),
            MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + API_KEY)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                //System.out.println("Raw Response: " + responseBody); // debug line
                JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();
                return responseJson.getAsJsonArray("choices")
                                   .get(0)
                                   .getAsJsonObject()
                                   .get("message")
                                   .getAsJsonObject()
                                   .get("content")
                                   .getAsString();
            } else {
                System.out.println("Error Response Code: " + response.code());
                System.out.println("Error Response Body: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating code: " + e.getMessage();
        }
        return "Error generating code.";
    }
	
}
