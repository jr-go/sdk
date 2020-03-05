package org.lkchain.transaction;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: demo
 * @description: 交易辅助
 * @author: JR
 * @create: 2020-02-20 12:12
 */
public class Helper {

    public static final BigInteger MaxGasLimit = BigInteger.valueOf(5000000000L);
    public static final BigInteger MinGasLimit = BigInteger.valueOf(500000L);
    public static final BigInteger GasPrice = BigInteger.valueOf(100000000000L);
    public static final BigInteger GasToLianKeRate = BigInteger.valueOf(10000000L);
    public static final BigInteger EvenLianKeFee = BigInteger.valueOf(50000L);
    public static final BigInteger LianKe = BigInteger.valueOf(1000000000000000000L);


    public static String SHA(final String strText, final String strType) {
        String strResult = null;
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    public static String md5(String plainText) {
        // 定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 对字符串进行加密
            md.update(plainText.getBytes());
            // 获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        // 将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }


    /**
     * 计算转账手续费
     * @param value
     * @param feeRule
     * @return
     */
    public static BigInteger CallGasLimit(BigInteger value, BigInteger feeRule) {
        BigInteger liankeCount = BigInteger.ZERO;
        BigInteger lianke = GasPrice.multiply(GasToLianKeRate);
        if (value.mod(lianke).longValue() != 0) {
            liankeCount = value.divide(lianke);
            liankeCount = liankeCount.add(BigInteger.valueOf(1));
        } else {
            liankeCount = value.divide(lianke);
        }
        BigInteger fee = liankeCount.multiply(feeRule);
        if (fee.compareTo(MinGasLimit) < 0) {
            fee = MinGasLimit;
        }
        if (fee.compareTo(MaxGasLimit) > 0) {
            fee = MaxGasLimit;
        }
        return fee;
    }

    /**
     * 将十六进制字符串转为byte数组
     * @param hex
     * @return
     */
    public static byte[] Hex2ByteArray(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }
}
