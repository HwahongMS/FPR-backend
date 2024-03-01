package com.fpr.gpts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpr.gpts.dto.ChatGPTRequestDTO;
import com.fpr.gpts.dto.ChatGPTResponseDTO;
import com.fpr.gpts.dto.Choice;
import com.fpr.gpts.dto.Message;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class GptService {

    @Value("${gpt.api_key}")
    private String gptApiKey;

    public static String historyContent = "";
    public String getKnowledge() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        StringBuilder promptSystem = new StringBuilder();
        StringBuilder promptUser = new StringBuilder();
        // 시스템 역할 설정
        promptSystem.append("너는 금융전문가로서 금융 지식을 하나 알려주는 역할이야." +
                "주제 하나를 잡고 그에 대해서 설명해줘" +
                "주제에는 쌍따옴표로 강조해줘");
        messages.add(new Message("system", promptSystem.toString()));

        // 사용자의 질문

        promptUser.append(String.format("금융 지식의 주제 중 %s를 제외하고", historyContent));
        promptUser.append("금융 지식을 알려줘");
        promptUser.append("\n사담은 필요없어.");
        messages.add(new Message("user", promptUser.toString()));

        // temperature 는 답변의 창의성을 나타냄 온도가 낮을수록 정보성의 글
        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-3.5-turbo", messages, 0.3);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        System.out.println("apikey : " + gptApiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + gptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTResponseDTO chatGPTResponseDTO = objectMapper.readValue(response.body(), ChatGPTResponseDTO.class);
        List<Choice> choices = chatGPTResponseDTO.getChoices();

        String content = choices.get(0).getMessage().getContent();
        String subject = "";
        try {
            String[] splitContent = content.split("\"");
            subject = splitContent[1];
            historyContent = historyContent + "," + splitContent[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("historyContent = " + historyContent);
        System.out.println("subject = " + subject);
        System.out.println("content = " + content);
        return response.body();
    }

}
