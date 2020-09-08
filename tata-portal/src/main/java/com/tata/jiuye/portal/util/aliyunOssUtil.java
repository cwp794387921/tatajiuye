package com.tata.jiuye.portal.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@Component
public class aliyunOssUtil {
    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret ;
    @Value("${aliyun.oss.bucketName}")
    private  String bucketName ;

    private  String filePath ="https://cscyimages.oss-cn-zhangjiakou.aliyuncs.com/";

    public String uploadFile(File data, String key) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, data);
            ossClient.putObject(putObjectRequest);
            System.out.println("Object：" + key + "存入OSS成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return filePath+key;
    }

    public String uploadByte(byte[] data, String key) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            InputStream is = new ByteArrayInputStream(data);
            ossClient.putObject(bucketName, key, is);
            System.out.println("Object：" + key + "存入OSS成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return filePath+key;
    }

    public void deleteKey(String key){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            ossClient.deleteObject(bucketName, key);
            System.out.println("删除Object：" + key + "成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
}
