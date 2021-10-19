package edu.zju.wht;

import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class HttpRequestUtilTest {
    @Test
    public void getXpathTest() throws Exception {
        //从网站获取JSON格式的区块数据
        JsonObject jsonObject=HttpRequestUtil.getXpath("https://blockchain.info/rawblock/200000");
        //提取区块中的各个字段
        String hash=jsonObject.get("hash").toString();
        String ver=new StringBuffer("0000000"+Utils.decToHex(jsonObject.get("ver").toString())).toString();
        String prev_block=new StringBuffer(jsonObject.get("prev_block").toString()).toString();
        String mrkl_root=new StringBuffer(jsonObject.get("mrkl_root").toString()).toString();
        String time=new StringBuffer(Utils.decToHex(jsonObject.get("time").toString())).toString();
        String bits=new StringBuffer(Utils.decToHex(jsonObject.get("bits").toString())).toString();
        String nonce=new StringBuffer(Utils.decToHex(jsonObject.get("nonce").toString())).toString();
        //将要被hash的字段拼接,注意大小端转换
        String value=Utils.hexStrReverse(ver)+Utils.hexStrReverse(prev_block.substring(1,prev_block.length()-1))+Utils.hexStrReverse(mrkl_root.substring(1,mrkl_root.length()-1))+Utils.hexStrReverse(time)+Utils.hexStrReverse(bits)+Utils.hexStrReverse(nonce);
        //System.out.println(hash);
        //将16进制字符串转换为字节数组，进行SHA256的是字节数组而不是字符串
        byte[] bytes=Utils.hexToByteArray(value);
        //SHA256两次，得到摘要的字节数组
        byte[] digest=Utils.sha256(Utils.sha256(bytes,"SHA-256"),"SHA-256");
        //将摘要字节数组转换成字符串
        String ans=Utils.bytesToHex(digest);
        System.out.println(Utils.hexStrReverse(ans));

    }
}
