package com.tata.jiuye.utils;

import com.tata.jiuye.common.api.CommonResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpTools {
    public static CommonResult sendGetRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        client.getForObject(url,CommonResult.class);
        CommonResult commonResult = client.getForObject(url,CommonResult.class,requestEntity);
        return commonResult;
    }

    public static CommonResult sendPostRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        CommonResult commonResult = client.postForObject(url,requestEntity,CommonResult.class);
        return commonResult;
    }


    public static CommonResult sendPostRequestForRequestBody(String url, String paramJson) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> strEntity = new HttpEntity<String>(paramJson,headers);
        CommonResult commonResult = client.postForObject(url,strEntity,CommonResult.class);
        return commonResult;
    }
}
