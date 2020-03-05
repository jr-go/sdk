package org.lkchain.websocket;

import org.web3j.protocol.websocket.events.Log;

public interface LogsListener {
    void onError(Exception e);
    void onLogs(Log l);
    void onClose(int i, String s, boolean b);
}
