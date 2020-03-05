package org.lkchain.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
/*
* 事件监听条件过滤
* */
public class FilterCriteria {

    //监听事件的起始区块,不指定默认从最新出的块开始监听
    private BigInteger from;
    //结束区块,不指定默认监听所有新出的块
    private BigInteger to;
    //要监听的合约地址,不指定默认监听所有合约地址
    private List<String> addrs;
    //要监听的事件topics,不指定默认监听所有事件
    private List<List<String>> topics;

    public FilterCriteria(BigInteger from, BigInteger to, List<String> addrs) {
        this.from = from;
        this.to = to;
        this.addrs = addrs;
        this.topics = new ArrayList<>();
    }

    public void addSingleTopic(String topic) {
        List<String> t = new ArrayList<>();
        t.add(topic);
        this.topics.add(t);
    }
}