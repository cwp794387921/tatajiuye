package com.tata.jiuye.portal.util.inviteQrCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.tata.jiuye.portal.util.file.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InviteQrCode {

    private static String APIKEY;

    @Value("${auth.wechat.appId}")
    public void setAppId(String appId){
        APIKEY = appId;
    }

    private static String SECRETKEY;
    @Value("${auth.wechat.secret}")
    public void setSecret(String secret){
        SECRETKEY = secret;
    }

    private static String FILEPATH_LINUX;
    @Value("${file.linux.path}")
    public void setFilepathLinux(String path){
        FILEPATH_LINUX = path;
    }


    private static String FILEPATH_WIN;
    @Value("${file.windows.path}")
    public void setFilepathWin(String path){
        FILEPATH_WIN = path;
    }
    /**
     * win 系统
     */
    public static final String WIN = "win";

    /**
     * linux 系统
     */
    public static final String LINUX = "linux";
    /**
     * 用于获取access_token
     * @return  access_token
     * @throws Exception
     */
    public static String postToken() throws Exception {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APIKEY+"&secret="+SECRETKEY;
        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes("");
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        if (requestUrl.contains("nlp"))
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        else
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        JSONObject jsonObject = JSON.parseObject(result);
        String accesstoken=jsonObject.getString("access_token");
        return accesstoken;
    }


    /**
     * 生成带参小程序二维码
     * @param sceneStr	参数
     * @param accessToken	token
     */
    public static void getminiqrQr(String sceneStr, String accessToken) {
        try
        {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", sceneStr);
            //paramJson.put("page", "pages/home/index/index");//首页
            paramJson.put("page", "pages/login/register/index");//page home index index
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());

            //输出
            BufferedImage bufferedImage = ImageIO.read(httpURLConnection.getInputStream());

            OutputStream os = new FileOutputStream(new File("C:/Users/Acer/Desktop/testQrCode.jpg"));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 生成带参小程序二维码
     * @param sceneStr	参数
     * @param accessToken	token
     */
    public static BufferedImage getminiqrQrToBufferedImage(String sceneStr, String accessToken,int size) {
        try
        {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", sceneStr);
            //paramJson.put("page", "pages/home/index/index");//page home index index
            paramJson.put("page", "pages/index/main");
            paramJson.put("width", size);
            paramJson.put("auto_color", true);
            paramJson.put("is_hyaline",false);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            //输出
            BufferedImage bufferedImage = ImageIO.read(httpURLConnection.getInputStream());
            log.info("--------------------图片资源"+bufferedImage.toString());
            return bufferedImage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 合并图片和二维码
     * @param imgPath   模板途径
     * @param qrContent 二维码内容
     * @param size      //二维码尺寸
     * @param x         //二维码贴的位置x
     * @param y         //二维码贴的位置y
     * @return
     */
    public static BufferedImage mergeImageAndQR(String imgPath, String qrContent, int size, int x, int y,String accessToken) throws IOException {
        //try (InputStream is = Files.newInputStream(Paths.get(imgPath))) {
        BufferedImage bi = null;
        try {
            URL url = new URL(imgPath);
            HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            conn.connect();
            InputStream is = conn.getInputStream();
            bi = ImageIO.read(is);
            Graphics2D g2 = bi.createGraphics();
            BufferedImage bufferedImage = getminiqrQrToBufferedImage(qrContent,accessToken,size);
            //消除文字锯齿
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除画图锯齿
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(bufferedImage, x, y, null);
            g2.dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bi;
    }


    /**
     * 合并图片和二维码
     * @param imgPath   模板途径
     * @param qrContent 二维码内容
     * @param size      //二维码尺寸
     * @param x         //二维码贴的位置x
     * @param y         //二维码贴的位置y
     * @return
     */
    public static String mergeImageAndQRToFileUrl(String imgPath, String qrContent, int size, int x, int y,String accessToken) throws IOException {
        //try (InputStream is = Files.newInputStream(Paths.get(imgPath))) {
        BufferedImage bi = null;
        try {
            URL url = new URL(imgPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            conn.connect();
            InputStream is = conn.getInputStream();
            bi = ImageIO.read(is);
            Graphics2D g2 = bi.createGraphics();
            BufferedImage bufferedImage = getminiqrQrToBufferedImage(qrContent, accessToken, size);
            //消除文字锯齿
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除画图锯齿
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(bufferedImage, x, y, null);
            g2.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream inputStream = FileUtil.bufferedImageToInputStream(bi);
        MultipartFile multipartFile = new MockMultipartFile("haibaoQrCode.png","haibaoQrCode.png","png", inputStream);
        String filePath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(WIN)) {
            filePath = FILEPATH_WIN;
        }
        else if(os.toLowerCase().startsWith(LINUX)){
            filePath = FILEPATH_LINUX;
        }
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, filePath+type+File.separator);
        String resultUrl = "https://www.cyjyt.com:8085/file/img/"+file.getName();
        return resultUrl;
    }


    /**
     * 生成二维码
     *
     * @param content
     * @param size
     * @return
     */
    public static BitMatrix genQrCode(String content, int size) {
        //设置图片的文字编码以及内边框
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //边框距
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix;
        try {
            //参数分别为：编码内容、编码类型、图片宽度、图片高度，设置参数
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        return bitMatrix;
    }



    /**
     * 二维码转字节
     *
     * @param content
     * @param size
     * @return
     * @throws IOException
     */
    public static byte[] getQrBuffer(String content, int size) throws IOException {
        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(genQrCode(content, size), "png", byteArray);
            return byteArray.toByteArray();
        }
    }



    /**
     * 二维码转图片
     *
     * @param content
     * @param size
     * @return
     */
    public static BufferedImage getQRImg(String content, int size) {
        return MatrixToImageWriter.toBufferedImage(genQrCode(content, size));
    }

    /**
     * 生成带logo的二维码图片
     *
     * @param logoFile logo路径
     * @param content  内容
     * @param size     二维码大小
     * @param angel
     */
    public static BufferedImage drawLogoQRCode(File logoFile, String content, int size, double angel) throws IOException {
        BufferedImage image = Thumbnails.of(getQRImg(content, size)).size(size, size).rotate(angel).asBufferedImage();
        int width = image.getWidth();
        int height = image.getHeight();
        //绘制logo
        // 构建绘图对象
        Graphics2D g2 = image.createGraphics();
        // 读取Logo图片
        int x = width * 2 / 5;
        int y = height * 2 / 5;
        int cWidth = width * 2 / 10;
        int cHeight = height * 2 / 10;
        BufferedImage bufferedImage = Thumbnails.of(ImageIO.read(logoFile)).size(width, height).rotate(angel).asBufferedImage();
        // 开始绘制logo图片
        //消除文字锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //消除画图锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(bufferedImage, x, y, cWidth, cHeight, null);
        g2.dispose();
        bufferedImage.flush();
        image.flush();
        return image;
    }
}
