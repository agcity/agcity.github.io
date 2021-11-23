package io.github.agcity.netty.server.service;

public interface IMessageService<String> {

    Object handle(String requestId, String message);

    String getCode();

}
