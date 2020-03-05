package org.lkchain.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String walletFileName;
    private Credentials credentials;
    private String passwd;
    private BigInteger balance;

    @NonNull
    public Account(String passwd) throws Exception{
        if(passwd.length() < 8){
            throw new Exception("password length must greater than 8");
        }
        this.passwd = passwd;
    }
}
