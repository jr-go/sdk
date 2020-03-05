package org.lkchain.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestModel<S> {
    private String method;
    private String id;
    private String jsonrpc;
    private List<S> params;
}