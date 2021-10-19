package edu.zju.wht;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//工具包
public class Utils{
    //十进制转十六进制
    public static String decToHex(String str){
        return Long.toHexString(Long.valueOf(str));
    }
    //大端序转小端序
    public static String hexStrReverse(String str) {
        int length = str.length();
        String[] arr = new String[str.length() / 2];
        int j = 0;
        for (int i = length; i > 1; i = i - 2) {
            arr[j] = str.substring(i - 2, i);
            j++;
        }
        StringBuffer sb = new StringBuffer();

        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString().toLowerCase();
    }
    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte
     */
    public static byte hexToByte(String inHex){
       return (byte)Integer.parseInt(inHex,16);
    }
    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }
    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return  转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }
    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    public static byte[] sha256(byte[] content, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(content);
        byte tmp[] = messageDigest.digest();
        return tmp;
    }
}
