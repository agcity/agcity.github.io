package io.github.agcity.netty.server.service;

import io.github.agcity.netty.server.context.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@Slf4j
public abstract class AbstractServiceAdapter implements IMessageService<String> {

    @Autowired
    private MessageContext messageContext;

	/**
	 * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用
	 */
    @PostConstruct
    public void register() {
        log.info("================6.1 依赖注入完整后需要执行的方法：获取code值（根据code值判定处理不同的业务逻辑） ！！========================");
        messageContext.register(getCode(), this);
    }

    protected Map<String, Object> getTemplate(String messsage) {
        try {
            log.info("================16. 我来读取一些信息，哈哈哈 ========================");
            return null;
        } catch (Exception e) {
            log.error("模板转换失败", e);
        }
        return Collections.emptyMap();
    }
}
