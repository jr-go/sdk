package org.lkchain.http;

import com.google.gson.Gson;
import okhttp3.*;
import org.lkchain.account.Account;
import org.lkchain.transaction.RPCError;
import org.lkchain.transaction.ResponseModel;
import org.lkchain.websocket.EthCallParam;
import org.lkchain.websocket.RequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.lkchain.transaction.Receipt;
import org.web3j.utils.Numeric;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: demo
 * @description: http请求客户端
 * @author: JR
 * @create: 2020-02-20 12:19
 */
public class RPCClient {

    private OkHttpClient client;

    private String URL;

    private static final Gson gson = new Gson();

    private static final Logger log = LoggerFactory.getLogger(RPCClient.class);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public RPCClient(String url) {
        this.URL = url;
        try {
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时
                    .readTimeout(5, TimeUnit.SECONDS) // 设置读超时
                    .writeTimeout(5, TimeUnit.SECONDS) // 设置写超时
                    .retryOnConnectionFailure(true) // 是否自动重连
                    .sslSocketFactory(createEasySSLContext().getSocketFactory(), new EasyX509TrustManager(null))
                    .build();
            this.client = client;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SSLContext createEasySSLContext() throws IOException {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            return context;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 获取地址nonce值
     * 
     * @param account
     * @return
     */
    public BigInteger GetTransactionCount(Account account) throws Exception {
        List params = Arrays.asList(account.getCredentials().getAddress(), "latest");
        ResponseModel responseModel = this.sendRequest("eth_getTransactionCount", params);
        return parseResponse(responseModel);
    }

    /**
     * 计算执行合约所需的手续费
     * 
     * @param from
     * @param to
     * @param value
     * @param data
     * @return
     */
    public BigInteger EstimateGas(String from, String to, BigInteger value, String data) throws Exception {
        List params = Arrays.asList(new EthCallParam(from, to, "0x" + value.toString(16), data));
        ResponseModel model = this.sendRequest("eth_estimateGas", params);
        return parseResponse(model);
    }

    /**
     * 合约查询
     * 
     * @param to
     * @param data
     * @return
     */
    public ResponseModel EthCall(String to, String data) {
        List params = Arrays.asList(new EthCallParam(to, data), "latest");
        return this.sendRequest("eth_call", params);
    }

    /**
     * 获取地址余额
     * 
     * @param account
     * @return
     */
    public BigInteger GetBalance(Account account) throws Exception {
        List params = Arrays.asList(account.getCredentials().getAddress(), "latest");
        ResponseModel model = this.sendRequest("eth_getBalance", params);
        return parseResponse(model);
    }

    /**
     * 获取交易收据
     * 
     * @param hash
     * @return
     */
    public Receipt GetTransactionReceipt(String hash) throws Exception {
        try {
            List params = Arrays.asList(hash);
            ResponseModel model = this.sendRequest("eth_getTransactionReceipt", params);
            if (model.getResult() != null) {
                Receipt receipt = gson.fromJson(model.getResult().toString(), Receipt.class);
                return receipt;
            }
            if (model.getError() != null) {
                throw new Exception(model.getError().getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        throw new Exception("hash not exists");
    }

    /**
     * 发送普通交易
     * 
     * @param nonce
     * @param account
     * @param toAddress
     * @param gasLimit
     * @param gasPrice
     * @param value
     * @return
     */
    public ResponseModel SendTransaction(BigInteger nonce, Account account, String toAddress, BigInteger gasLimit,
            BigInteger gasPrice, BigInteger value) {
        // 创建交易
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toAddress,
                value);
        // 签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, account.getCredentials());
        String hexValue = Numeric.toHexString(signedMessage);
        List params = Arrays.asList(hexValue);
        return this.sendRequest("eth_sendRawTransaction", params);
    }

    /**
     * 发送合约交易
     * 
     * @param nonce
     * @param account
     * @param toAddress
     * @param gasLimit
     * @param gasPrice
     * @param value
     * @param payload
     * @return
     */
    public ResponseModel SendContractTransaction(BigInteger nonce, Account account, String toAddress,
            BigInteger gasLimit, BigInteger gasPrice, BigInteger value, String payload) {
        // 创建交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, toAddress, value,
                payload);
        // 签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, account.getCredentials());
        List params = Arrays.asList(Numeric.toHexString(signedMessage));
        return this.sendRequest("eth_sendRawTransaction", params);
    }

    private BigInteger parseResponse(ResponseModel responseModel) throws Exception {
        Object result = responseModel.getResult();
        if (responseModel.getError() != null) {
            throw new Exception(responseModel.getError().getMessage());
        }
        if (responseModel.getResult().toString().startsWith("0x")) {
            result = responseModel.getResult().toString().substring(2);
        }
        return new BigInteger(result.toString(), 16);
    }

    /**
     * 发送rpc请求
     * 
     * @param method 请求方法
     * @param params 请求参数
     * @return
     */
    public ResponseModel sendRequest(String method, List params) {
        ResponseModel model;
        try {
            RequestModel RequestModel = new RequestModel(method, "1", "2.0", params);
            String json = gson.toJson(RequestModel);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(this.URL).post(body).build();
            Response response = client.newCall(request).execute();
            String resp = response.body().string();
            System.out.println(resp);
            model = gson.fromJson(resp, ResponseModel.class);
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
