package org.lkchain.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.protocol.websocket.events.Log;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsMessage {
    private String subscription;
    private Log result;

    @Override
    public String toString() {
        return result.toString();
    }

}
