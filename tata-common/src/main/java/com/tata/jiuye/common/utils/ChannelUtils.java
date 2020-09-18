package com.tata.jiuye.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChannelUtils {
    private static final String charset = "UTF-8";
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String)keys.get(i);
            if (!key.equals("sign"))
            {
                String value = (String)params.get(key);
                if (StringUtils.isBlank(value)) {
                    System.err.print("请求参数为空或者空字符串不参与验签:" + key);

                }
                else {
                    if (encode) {
                        try {
                            value = URLEncoder.encode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        }
        return prestr;
    }

    public static String getContent(Map<String, String> params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String)keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
