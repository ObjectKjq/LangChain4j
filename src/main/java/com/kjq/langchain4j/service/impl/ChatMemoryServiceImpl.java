package com.kjq.langchain4j.service.impl;

import com.kjq.langchain4j.model.entity.ChatMemory;
import com.kjq.langchain4j.mapper.ChatMemoryMapper;
import com.kjq.langchain4j.service.ChatMemoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用表结构 服务实现类
 * </p>
 *
 * @author kongdefang
 * @since 2025-06-22
 */
@Service
public class ChatMemoryServiceImpl extends ServiceImpl<ChatMemoryMapper, ChatMemory> implements ChatMemoryService {

}
