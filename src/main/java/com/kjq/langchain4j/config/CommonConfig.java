package com.kjq.langchain4j.config;

import com.kjq.langchain4j.aiservice.ConsultantService;
import com.kjq.langchain4j.memory.JdbcChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {
    @Resource
    private OpenAiChatModel model;
    @Resource
    private ChatMemoryStore jdbcChatMemoryStore;
    @Resource
    private EmbeddingModel embeddingModel;

    @Bean
    public ConsultantService consultantService() {
        ConsultantService consultantService = AiServices.builder(ConsultantService.class)
                .chatModel(model)
                .build();
        return consultantService;
    }

    /**
     * 创建基于内存的公共会话记忆
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(4)
                .build();
    }

    /**
     * 会话记忆对象提供者
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(4)
                        .chatMemoryStore(jdbcChatMemoryStore)
                        .build();
            }
        };
    }

    /**
     * 构建向量数据库的存储对象
     * 1. 文档加载器
     * 2. 文档解析器
     * 3. 文档分割器
     * 4. 向量模型
     * 3. 向量数据库
     */
    @Bean
    public EmbeddingStore store() {
        // 加载文档到内存
        // List<Document> documents = ClassPathDocumentLoader.loadDocuments("docs");
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("pdfs", new ApachePdfBoxDocumentParser());  // pdf文档解析器
        // 构建向量数据库
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();
        // 文档分割器
        DocumentSplitter splitter = DocumentSplitters.recursive(100, 50);
        // 构建一个操作对象，完成文本分割和向量化，存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    /**
     * 构建向量数据库检索对象
     */
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .minScore(0.5)
                .maxResults(3)
                .embeddingModel(embeddingModel)
                .build();
    }

}
