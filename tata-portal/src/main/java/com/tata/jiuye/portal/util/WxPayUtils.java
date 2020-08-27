package com.tata.jiuye.portal.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author tangxs
 * @date 18
 */
public class WxPayUtils {
    //微信支付工具类
    private static String originKey;
    static {
        WxConfig config = null;
        try {
            config = new WxConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String key = config.getKey();
        originKey =key;
    }

    public static String createSign(String characterEncoding,SortedMap<String,String> parameters) throws Exception {
        WxConfig config = new WxConfig();
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"paySign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" +config.getKey());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = WxPayUtils.getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(WxPayUtils.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toLowerCase();
    }

    public static TreeMap<String, String> strToTreeMap(String str) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        String data[] = str.split("&");
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            map.put(tmp[0], tmp[1].trim());
        }
        return map;
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }





    private static SecretKeySpec key =
            new SecretKeySpec(MD5Util.MD5Encode(originKey, "UTF-8").
                    toLowerCase().getBytes(), "AES");

    public static byte[] decode(String  key) throws Exception {
        System.out.println(new BASE64Decoder().decodeBuffer(key));
        return new BASE64Decoder().decodeBuffer(key);

    }
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    public static String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(decode(base64Data)),"UTF-8");
    }

    // 获取随机字符串
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // 获取xml格式的签名
    public static String getRequestXml(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"paySign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    // 获取当前机器的ip地址
    public static String getLocalhostIp(){
        String ip="";
        try {
            ip= InetAddress.getLocalHost().toString();
            ip=ip.substring(ip.indexOf("/")+1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
