package io.github.agcity.netty.server.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author agcity
 * @version 1.0
 * @date 2021/11/23 3:02 下午
 * @description: TODO
 */
@WebListener
@Slf4j
public class SocketListener implements ServletContextListener {

    NettyServer nettyServer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("================1.启动时，开启监听========================");
        if (nettyServer == null) {
            nettyServer = new NettyServer();
            log.info("================2.启动时，NettyServer为null，启动Netty服务========================");
            Thread thread = new Thread(nettyServer);
            thread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("================23========================");
    }
}
