package com.ethan.passwordbox.encrypt;

import android.util.Base64;
import android.util.Log;

import com.ethan.passwordbox.config.Cons;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * NOTE:
 *
 * @author Ethan 2022/7/10
 */
public class AES {
    private static final String TAG = "AESCBCUtils";

    // CBC(Cipher Block Chaining, 加密块链)模式，PKCS5Padding补码方式
    // AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";

    /**
     * AES 加密
     *
     * @param strClearText    待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt_AES(String strClearText) {

        try {
            byte[] raw = Cons.Encrypt.PRIVATE_KEY.getBytes();
            // 创建AES密钥
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            // 创建偏移量
            IvParameterSpec iv = new IvParameterSpec(Cons.Encrypt.IV_PARAMETER.getBytes());
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            // 执行加密操作
            byte[] cipherText = cipher.doFinal(strClearText.getBytes());
            Log.d(TAG, "encrypt result(not BASE64): " + Arrays.toString(cipherText));
            // encode it by BASE64 again
            String strBase64Content = Base64.encodeToString(cipherText, Base64.DEFAULT);
            Log.d(TAG, "encrypt result(BASE64): " + strBase64Content);
            // strBase64Content = strBase64Content.replaceAll(System.getProperty("line.separator"), "");

            return strBase64Content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES 解密
     *
     * @param strCipherText   待解密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String decrypt(String strCipherText) throws Exception {

        try {
            byte[] raw = Cons.Encrypt.PRIVATE_KEY.getBytes(StandardCharsets.US_ASCII);
            // 创建AES秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            // 创建偏移量
            IvParameterSpec iv = new IvParameterSpec(Cons.Encrypt.IV_PARAMETER.getBytes());
            // 初始化解密器
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 执行解密操作 decode by BASE64 first
            byte[] cipherText = Base64.decode(strCipherText, Base64.DEFAULT);
            Log.d(TAG, "BASE64 decode result(): " + Arrays.toString(cipherText));
            byte[] clearText = cipher.doFinal(cipherText);
            String strClearText = new String(clearText);
            Log.d(TAG, "decrypt result: " + strClearText);

            return strClearText;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
