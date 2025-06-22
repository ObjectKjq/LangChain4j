package com.kjq.langchain4j;

import com.kjq.langchain4j.config.ApiKeyConfig;
import com.kjq.langchain4j.memory.JdbcChatMemoryStore;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
public class LangChain4J {

    interface Assistant {
        @SystemMessage("你是一个医疗只能助手，能帮助用户解决各种疾病问题。")
        String chat(String userMessage);
    }

    @Test
    public void testAIServices() {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeyConfig.apiKey)
                .baseUrl("https://api.deepseek.com/v1")
                .modelName("deepseek-chat")
                .build();
        Assistant assistant = AiServices.create(Assistant.class, model);
        String answer = assistant.chat("你好");
        System.out.println(answer); // Hello, how can I help you?
    }

    @Test
    public void testStream() throws InterruptedException {
        OpenAiStreamingChatModel model = OpenAiStreamingChatModel.builder()
                .apiKey(ApiKeyConfig.apiKey)
                .baseUrl("https://api.deepseek.com/v1")
                .modelName("deepseek-chat")
                .build();


        String userMessage = "你好，我叫杰克？";
        model.chat(userMessage, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.println("不完整的消息: " + partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                System.out.println("完整消息: " + completeResponse);
            }

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
        Thread.sleep(10000);
        // System.out.println(answer); // Hello World

    }
}
