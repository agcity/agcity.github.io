package io.github.agcity.netty.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @PostMapping(value = "/socket/init")
    @ResponseBody
    public String socketTest(HttpServletRequest request, String code) {
        logger.info("================10. http请求开始！！========================");
        String reqMsg = getReqString(request);
        Socket socket = null;
        PrintWriter pw = null;
        DataInputStream is = null;
        String retMsg = "";
        try {
            socket = new Socket("127.0.0.1", 58765);
            pw = new PrintWriter(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
            // 发送数据
            logger.info("================11. 发送Socket数据开始！！========================");
            pw.println(reqMsg);
            logger.info("================12. 发送Socket数据结束！！========================");
            pw.flush();
            socket.shutdownOutput();
            int len;
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            retMsg = sb.toString();
            logger.info("================19. 接受Socket服务端返回处理数据内容为：" + retMsg + "========================");
        } catch (Exception e) {
            logger.error("客户端代码发送失败!", e);
            retMsg = "请求处理发生异常，请稍后重试!";
        } finally {
            try {
                is.close();
                pw.close();
                socket.close();
            } catch (IOException e) {
            }
        }
        return retMsg;
    }

    private String getReqString(HttpServletRequest req) {
        logger.info("[获取请求报文内容][开始]");

        String reqXml = "";
        // 输入流
        InputStream inputStream = null;
        // 输出流
        ByteArrayOutputStream outputStream = null;

        try {
            inputStream = req.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 创建缓冲区，大小1024字节
            byte[] buffer = new byte[1024];
            int len = -1;
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            reqXml = new String(outputStream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            logger.error("[获取请求报文内容][发生异常][异常为：" + e.getMessage() + "]" + e);
        } finally {
            // 关闭输入流
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
            // 关闭输出流
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            inputStream = null;
            outputStream = null;
        }
        logger.info("[获取请求报文内容][报文内容为：" + reqXml + "]");
        logger.info("[获取请求报文内容][结束]");
        return reqXml;
    }

}
