package io.github.agcity.netty.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class SocketServerServiceImpl extends AbstractServiceAdapter {

    @Override
    public String handle(String requestId, String message) {
        log.info("================15. 进去201对应的Service——SocketServerService ========================");
        try {
            // 1.读取配置中心模板
           Map<String, Object> map = getTemplate("读取模板开始");
            // 2.转换为统一出入平台报文
            // 3.发送http请求
            // 4.返回报文转换为dto
            //等等处理业务
            return "处理成功！！！" + message;
        } catch (Exception e) {
            log.error("[交易处理异常]", e);
        }

        return null;
    }

    @Override
    public String getCode() {
        log.info("================6.2 code1的值为：" + 201 + "========================");
        return "201";
    }

}