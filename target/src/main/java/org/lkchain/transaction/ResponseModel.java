package org.lkchain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private String id;
    private String jsonrpc;
    private T result;
    private RPCError error;
}


