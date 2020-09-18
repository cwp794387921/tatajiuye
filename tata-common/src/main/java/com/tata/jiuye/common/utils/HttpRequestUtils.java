package com.tata.jiuye.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

public class HttpRequestUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
    private static final int DEFAULT_CONNECT_TIMEOUT = 6000;
    private static final int DEFAULT_READ_TIMEOUT = 12000;
    public static final String ENCODE_UTF8 = "UTF-8";
    public static final String ENCODE_GB2312 = "GB2312";
    public static final String CONTENT_TYPE_DEF = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

    public static String readContentFromGet(String url)
            throws IOException {
        return readContentFromGet(url, 6000, 12000, null);
    }

    public static String readContentFromGet(String url, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromGet(url, connectTimeout, readTimeout, null);
    }

    public static String readContentFromGetOfUTF8(String url)
            throws IOException {
        return readContentFromGet(url, 6000, 12000, "UTF-8");
    }

    public static String readContentFromGetOfUTF8(String url, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromGet(url, connectTimeout, readTimeout, "UTF-8");
    }

    public static String readContentFromGetOfGB2312(String url)
            throws IOException {
        return readContentFromGet(url, 6000, 12000, "GB2312");
    }

    public static String readContentFromGetOfGB2312(String url, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromGet(url, connectTimeout, readTimeout, "GB2312");
    }

    public static String readContentFromGet(String url, int connectTimeout, int readTimeout, String encode)
            throws IOException {
        URL getUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.connect();

        StringBuilder sb = new StringBuilder();
        BufferedReader in;
        if (encode == null)
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        else
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();

        connection.disconnect();
        return sb.toString();
    }

    public static String readContentFromPost(String url, String content)
            throws IOException {
        return readContentFromPost(url, content, 6000, 12000, null);
    }

    public static String readContentFromPost(String url, String content, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromPost(url, content, connectTimeout, readTimeout, null);
    }

    public static String readContentFromPostOfGB2312(String url, String content)
            throws IOException {
        return readContentFromPost(url, content, 6000, 12000, "GB2312");
    }

    public static String readContentFromPostOfGB2312(String url, String content, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromPost(url, content, connectTimeout, readTimeout, "GB2312");
    }

    public static String readContentFromPostOfUTF8(String url, String content)
            throws IOException {
        return readContentFromPost(url, content, 6000, 12000, "UTF-8");
    }

    public static String readContentFromPostOfResStatus(String url, String content, Map<String, String> headers)
            throws IOException {
        return readContentFromPostOfResStatus(url, content, headers, 6000, 12000, "UTF-8");
    }

    public static String readContentFromPostOfUTF8(String url, String content, int connectTimeout, int readTimeout)
            throws IOException {
        return readContentFromPost(url, content, connectTimeout, readTimeout, "UTF-8");
    }

    public static String readContentFromPost(String url, String content, int connectTimeout, int readTimeout, String encode)
            throws IOException {
        URL postUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestMethod("POST");

        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestProperty("referer", "http://shop.byzy-pay.com");
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(content.getBytes());
        out.flush();
        out.close();
        BufferedReader reader;
        if (encode == null)
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        connection.disconnect();
        return sb.toString();
    }

    public static String readContentFromPostOfResStatus(String url, String content, Map<String, String> headersMap, int connectTimeout, int readTimeout, String encode)
            throws IOException {
        URL postUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestMethod("POST");

        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
        if ((headersMap != null) && (headersMap.size() > 0)) {
            for (Map.Entry header : headersMap.entrySet()) {
                connection.addRequestProperty((String) header.getKey(), (String) header.getValue());
            }
        }
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(content.getBytes());
        out.flush();
        out.close();
        BufferedReader reader;
        if (encode == null)
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        int httpStatus = connection.getResponseCode();

        connection.disconnect();
        return new StringBuilder().append(httpStatus).append(",").append(sb.toString()).toString();
    }

    public static String readContentFromPostOfHeader(String url, String content, Map<String, String> headersMap)
            throws IOException {
        return readContentFromPostOfHeader(url, content, headersMap, 6000, 12000, "UTF-8");
    }

    public static String readContentFromPostOfHeader(String url, String content, Map<String, String> headersMap, int connectTimeout, int readTimeout, String encode)
            throws IOException {
        URL postUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestMethod("POST");

        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
        if ((headersMap != null) && (headersMap.size() > 0)) {
            for (Map.Entry header : headersMap.entrySet()) {
                connection.addRequestProperty((String) header.getKey(), (String) header.getValue());
            }
        }
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(content.getBytes());
        out.flush();
        out.close();
        BufferedReader reader;
        if (encode == null)
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        connection.disconnect();
        return sb.toString();
    }

    public static String readContentFromPostJson(String url, String content, Map<String, String> headersMap)
            throws IOException {
        return readContentFromPostJson(url, content, headersMap, 6000, 12000);
    }

    public static String readContentFromPostJson(String url, String content, Map<String, String> headersMap, int connectTimeout, int readTimeout)
            throws IOException {
        URL postUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.addRequestProperty("Accept-Charset", "UTF-8");
        if ((headersMap != null) && (headersMap.size() > 0)) {
            for (Map.Entry header : headersMap.entrySet()) {
                connection.addRequestProperty((String) header.getKey(), (String) header.getValue());
            }
        }
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
        out.println(content);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        connection.disconnect();
        return sb.toString();
    }

    public static String readContentFromPostJson(String url, String content)
            throws IOException {
        return readContentFromPostJson(url, content, 6000, 12000);
    }

    public static String readContentFromPostJson(String url, String content, int connectTimeout, int readTimeout)
            throws IOException {
        URL postUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.addRequestProperty("Accept-Charset", "UTF-8");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
        out.println(content);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        connection.disconnect();
        return sb.toString();
    }

    public static String readContentFormRequest(HttpServletRequest request)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static <T> T readContentFormRequest(HttpServletRequest request, Class<T> jsonClass)
            throws IOException {
        String json = readContentFormRequest(request);
        if (StringUtils.isBlank(json)) {
            throw new IOException();
        }
        return JSONObject.parseObject(json, jsonClass);
    }

    public static JSONObject readContentFormRequestToJsonObj(HttpServletRequest request)
            throws IOException {
        String json = readContentFormRequest(request);
        if (StringUtils.isBlank(json)) {
            throw new IOException();
        }
        return JSONObject.parseObject(json);
    }

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return null;
    }

    public static String sendHttpsRequest(String requestUrl, String requestMethod, String content) {
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            if (null != content) {
                OutputStream outputStream = conn.getOutputStream();

                outputStream.write(content.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            logger.error("发送HTTPS请求:连接超时!", ce);
            ce.printStackTrace();
        } catch (Exception e) {
            logger.error("发送HTTPS请求:异常!", e);
            e.printStackTrace();
        }
        return null;
    }

    public static String sendHttpsRequest(String requestUrl, String requestMethod, String contentType, String content) {
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", contentType);

            if (null != content) {
                OutputStream outputStream = conn.getOutputStream();

                outputStream.write(content.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            logger.error("发送HTTPS请求:连接超时!", ce);
            ce.printStackTrace();
        } catch (Exception e) {
            logger.error("发送HTTPS请求:异常!", e);
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPostWithCert(String certPath, String certPwd, String requestUrl, String xmlContent)
            throws Exception {
        KeyStore keyStore = getKeyStore(certPath, certPwd);
        SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, certPwd.toCharArray()).build();
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(requestUrl);
        HttpsURLConnection conn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer = null;
        try {
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml");

            if (null != xmlContent) {
                OutputStream outputStream = conn.getOutputStream();

                outputStream.write(xmlContent.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
        }

        return buffer.toString();
    }

    private static KeyStore getKeyStore(String keyStorePath, String pwd)
            throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream in = new FileInputStream(keyStorePath);
        ks.load(in, pwd.toCharArray());
        in.close();
        return ks;
    }

    public static void main(String[] args) throws IOException {
        String content = "merchantId=5920912062414103&reqMsgId=201711140908320616072&sign=fa5335698a909b278223e2a961ea47d0";
        System.out.println(readContentFromPost("http://www.yum-pay.com:10005/YUMPay/quickQuery.do ", content));
    }
}
