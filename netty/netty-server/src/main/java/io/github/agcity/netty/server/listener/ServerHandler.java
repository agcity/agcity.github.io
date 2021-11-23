package io.github.agcity.netty.server.listener;

import io.github.agcity.netty.server.context.MessageContext;
import io.github.agcity.netty.server.service.IMessageService;
import io.github.agcity.netty.server.util.SpringContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author agcity
 * @version 1.0
 * @date 2021/11/23 3:50 下午
 * @description: TODO
 */
@Slf4j
@Service
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("================13. 通过活跃中....========================");
    }

    /**
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("--------Netty Disconnect Client IP is :" + ctx.channel().id().asShortText() + " "
                + ctx.channel().remoteAddress() + "--------");
        log.info("================9========================");
        ctx.close();
    }

    /**
     * 功能：读取服务器发送过来的信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("================14. 通道读取服务器发送过来的信息========================");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        buf.release();
        log.info("[请求报文：][" + body + "]");

        handleMessage(ctx, body);
    }

    private void handleMessage(ChannelHandlerContext ctx, String body) {
        log.info("================15. 业务逻辑处理开始========================");
        MessageContext messageContext = (MessageContext) SpringContext.getBean("messageContext");
        String code = "201";
        IMessageService<String> service = messageContext.get(code);
        String resXml = (String) service.handle(code, body);

        log.info("================18. 返回给Socket请求客户端的处理结果为：" + resXml + "========================");
        ctx.writeAndFlush(Unpooled.copiedBuffer(resXml.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("================17. Netty读取信息已经完成啦！！========================");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("--------Netty Exception ExceptionCaught :" + ctx.channel().id().asShortText() + " "
                + cause.getMessage() + "=======================", cause);
        ctx.close();
    }

}
