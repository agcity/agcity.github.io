package io.github.agcity.netty.server.context;

import io.github.agcity.netty.server.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MessageContext {


    private Map<String, IMessageService<String>> handlers = new HashMap<>();

    public void register(String type, IMessageService<String> handler) {
        log.info("================6.3 code和service以键值对的方式放入map中========================");
        handlers.put(type, handler);
    }

    public IMessageService<String> get(String type) {
        log.info("================16. 根据code获取相应的Service========================");
        IMessageService<String> handler = handlers.get(type);
        return handler;
    }

}