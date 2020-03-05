package org.lkchain.transaction;

import org.web3j.protocol.websocket.events.Log;

import java.util.List;

/**
 * @program: sdk
 * @description: 交易收据
 * @author: liu yan
 * @create: 2020-02-24 21:16
 */
public class Receipt {

    /**
     * blockHash : 0x0fd51083a90509f2a00201a6d9d963719a5302447e1885024c0a8f95fbf42ea5
     * blockNumber : 0x7c39
     * contractAddress : null
     * cumulativeGasUsed : 0x5dd5
     * from : 0xc8c79562c818ed5337df13c636a8b38c55e78590
     * gasUsed : 0x5dd5
     * logs : []
     * logsBloom : 0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
     * status : 0x0
     * to : 0xfe8f4e74254cb69e2656f82b0925759b44eec0ba
     * tokenAddress : 0x0000000000000000000000000000000000000000
     * transactionHash : 0x33a45cecb4ef76da274156f563e6a2a792d00f422b68ad1ced30b1cdc9b08e3f
     * transactionIndex : 0x0
     * vmerr : vm: execution reverted: balance not enough
     */

    private String blockHash;
    private String blockNumber;
    private Object contractAddress;
    private String cumulativeGasUsed;
    private String from;
    private String gasUsed;
    private String logsBloom;
    private String status;
    private String to;
    private String tokenAddress;
    private String transactionHash;
    private String transactionIndex;
    private String vmerr;
    private List<Log> logs;

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Object getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(Object contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getVmerr() {
        return vmerr;
    }

    public void setVmerr(String vmerr) {
        this.vmerr = vmerr;
    }

    public List<?> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
