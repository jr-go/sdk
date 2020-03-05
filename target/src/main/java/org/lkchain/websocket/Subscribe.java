package org.lkchain.websocket;

import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.websocket.events.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * @program: demo
 * @description: 事件订阅处理
 * @author: JR
 * @create: 2020-02-20 13:14
 */
public class Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Subscribe.class);
    private static final Gson gson = new Gson();
    public static final String SubscribeMethod = "lk_subscription";
    public static final String LogsSubscribe = "logsSubscribe";


    private LogsListener listener;
    private String url;
    public Subscribe(String url,LogsListener l) {
        this.url = url;
        this.listener = l;
    }
    private WebSocketClient mWebSocketClient;

    /**
     * 订阅事件
     * @param filter 过滤参数
     * @return
     */
    public void subscribeLogs(FilterCriteria filter) throws URISyntaxException {
        URI uri;
        uri = new URI(this.url);

        if(this.mWebSocketClient == null){
            mWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("Websocket", "Opened");
                }

                @Override
                public void onMessage(String s) {
                    log.info("received:" + s);
                    try {
                        Message msg = gson.fromJson(s, Message.class);
                        // 判断是否是订阅的消息
                        if (msg != null && Subscribe.SubscribeMethod.equals(msg.getMethod())) {
                            // 获取订阅消息中的log数据
                            Log log = msg.getParams().getResult();
                            listener.onLogs(log);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("Websocket", "Closed " + s);
                    listener.onClose(i, s, b);
                }

                @Override
                public void onError(Exception e) {
                    log.info("Websocket", "Error " + e.getMessage());
                    listener.onError(e);
                }
            };
            mWebSocketClient.connect();
            List params = Arrays.asList(this.LogsSubscribe,filter);
            RequestModel RequestModel = new RequestModel("lk_subscribe", "1", "2.0", params);
            String json = gson.toJson(RequestModel);
            mWebSocketClient.send(json);
        }
    }
}
