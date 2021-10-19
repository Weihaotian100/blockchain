package edu.zju.wht;

import com.google.gson.JsonObject;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Application {

    public static void main(String args[]) {
        System.out.println("please input the number of the block:");
        Scanner sc=new Scanner(System.in);
        int block=sc.nextInt();
        //从网站获取JSON格式的区块数据
        JsonObject jsonObject = HttpRequestUtil.getXpath("https://blockchain.info/rawblock/"+block);
        //提取区块中的各个字段
        String hash = jsonObject.get("hash").toString();
        String ver = new StringBuffer("0000000" + Utils.decToHex(jsonObject.get("ver").toString())).toString();
        String prev_block = new StringBuffer(jsonObject.get("prev_block").toString()).toString();
        String mrkl_root = new StringBuffer(jsonObject.get("mrkl_root").toString()).toString();
        String time = new StringBuffer(Utils.decToHex(jsonObject.get("time").toString())).toString();
        String bits = new StringBuffer(Utils.decToHex(jsonObject.get("bits").toString())).toString();
        String nonce = new StringBuffer(Utils.decToHex(jsonObject.get("nonce").toString())).toString();
        //将要被hash的字段拼接,注意大小端转换
        String value = Utils.hexStrReverse(ver) + Utils.hexStrReverse(prev_block.substring(1, prev_block.length() - 1)) + Utils.hexStrReverse(mrkl_root.substring(1, mrkl_root.length() - 1)) + Utils.hexStrReverse(time) + Utils.hexStrReverse(bits) + Utils.hexStrReverse(nonce);
        //System.out.println(hash);
        //将16进制字符串转换为字节数组，进行SHA256的是字节数组而不是字符串
        byte[] bytes = Utils.hexToByteArray(value);
        //SHA256两次，得到摘要的字节数组
        byte[] digest = new byte[0];
        try {
            digest = Utils.sha256(Utils.sha256(bytes, "SHA-256"), "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //将摘要字节数组转换成字符串
        String ans = Utils.bytesToHex(digest);
        System.out.println("the hash of the "+block+"st block:"+hash.substring(1,hash.length()-1));
        System.out.println("the calculated hash:        "+Utils.hexStrReverse(ans));
        if(hash.substring(1,hash.length()-1).equals(Utils.hexStrReverse(ans)))
            System.out.println("validation passed");
        else
            System.out.println("validation failed1");
    }
}
