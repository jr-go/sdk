package org.lkchain.transaction;

import lombok.Data;
import org.lkchain.account.Account;
import org.lkchain.http.RPCClient;

import java.math.BigInteger;
import java.util.List;

/**
 * @program: demo
 * @description: 请求处理
 * @author: JR
 * @create: 2020-02-20 12:14
 */
@Data
public class Transfer {

    private final BigInteger gasPrice = BigInteger.valueOf(100000000000L);

    private RPCClient rpc;

    private String url;

    public Transfer(String url) {
        this.url = url;
        this.rpc = new RPCClient(this.url);
    }

    /**
     * 链克转账
     * 
     * @param account
     * @param toAddress
     * @param value
     * @return
     */
    public ResponseModel transfer(Account account, String toAddress, BigInteger value) {
        ResponseModel model;
        try {
            BigInteger nonce = this.rpc.GetTransactionCount(account);
            BigInteger gasLimit = Helper.CallGasLimit(value, Helper.EvenLianKeFee);
            model = this.rpc.SendTransaction(nonce, account, toAddress, gasLimit, this.gasPrice, value);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            RPCError err = new RPCError(-1, e.getMessage());
            model = new ResponseModel();
            model.setError(err);
            return model;
        }
    }

    /**
     * 执行合约
     * 
     * @param account   from地址
     * @param toAddress 合约地址
     * @param value     转账的链克
     * @param data      合约payload
     * @return
     */
    public ResponseModel callContract(Account account, String toAddress, BigInteger value, String data) {
        ResponseModel model;
        try {
            BigInteger nonce = this.rpc.GetTransactionCount(account);

            BigInteger gasLimit = this.rpc.EstimateGas(account.getCredentials().getAddress(), toAddress, value, data);
            model = this.rpc.SendContractTransaction(nonce, account, toAddress, gasLimit, this.gasPrice, value, data);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            RPCError err = new RPCError(-1, e.getMessage());
            model = new ResponseModel();
            model.setError(err);
            return model;
        }
    }

    /**
     * 执行合约
     * 
     * @param account from地址
     * @param data    合约bytecode
     * @return
     */
    public ResponseModel deployContract(Account account, String data) {
        ResponseModel model;
        try {
            BigInteger nonce = this.rpc.GetTransactionCount(account);

            BigInteger gasLimit = this.rpc.EstimateGas(account.getCredentials().getAddress(),
                    null, BigInteger.ZERO, data);
            model = this.rpc.SendContractTransaction(nonce, account, null, gasLimit, this.gasPrice, BigInteger.ZERO,
                    data);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            RPCError err = new RPCError(-1, e.getMessage());
            model = new ResponseModel();
            model.setError(err);
            return model;
        }
    }
}
