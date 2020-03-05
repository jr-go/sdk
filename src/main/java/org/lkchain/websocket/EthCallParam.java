package org.lkchain.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EthCallParam {
    //发送请求的地址，可选
    private String from;
    //要查询的合约地址
    private String to;
    //手续费，eth_call 不需要支付手续费，可选
    private String gas;
    //手续费单价，可选
    private String gasPrice;
    //要交易的token，默认为链克交易，可选
    private String tokenAddress;
    //交易金额，可选
    private String value;
    //执行的合约函数的签名和编码后的参数，可选
    private String data;

    public EthCallParam(String to, String data) {
        this.to = to;
        this.data = data;
    }

    public EthCallParam(String from,String to,String value, String data) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.data = data;
    }
}
