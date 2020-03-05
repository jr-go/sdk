package org.lkchain.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    public Account createAccount(String passwd, String filePath) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        try{
            Account account = new Account(passwd);
            //钱包文件保持路径，请替换位自己的某文件夹路径
            File file = new File(filePath);
            if  (!file .exists()  && !file .isDirectory())
            {
                file .mkdir();
            }
            String walletFileName = WalletUtils.generateNewWalletFile(account.getPasswd(), file, false);
            String walletFilePath = filePath + walletFileName;
            File walletFile = new File(walletFilePath);
            String[] sourceStrArray = walletFilePath.split(".json");
            walletFile.renameTo(new File(sourceStrArray[0]));
            log.info("walletFilePath: "+sourceStrArray[0]);
            account.setWalletFileName(sourceStrArray[0]);
            Credentials credentials = WalletUtils.loadCredentials(account.getPasswd(), account.getWalletFileName());
            account.setCredentials(credentials);
            return account;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
            e.printStackTrace();
            return null;
        }
    }

    public Account loadAccount(String passwd,String filePath) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        try{
            Account account = new Account(passwd);
            //钱包文件路径，请替换位自己的某文件夹路径
            Credentials credentials = WalletUtils.loadCredentials(account.getPasswd(), filePath);
            account.setCredentials(credentials);
            account.setPasswd(passwd);
            account.setWalletFileName(filePath);
            return account;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
            e.printStackTrace();
            return null;
        }
    }
}
