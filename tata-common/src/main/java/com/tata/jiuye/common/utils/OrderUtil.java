package com.tata.jiuye.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {

	 private static long orderNum = 0l;
	 private static String date ;
	 private static String BHorderHead="BH";
	 private static String CHorderHead="CH";
    private static String TKorderHead="TK";

	 /**
     * 生成补货单编号
     * @return
     */
    public static synchronized String getBhOrderNo() {
        String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
//            orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 100;
        orderNo += orderNum;
        return BHorderHead+orderNo;
    }

    public static synchronized String getChOrderNo() {
        String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
//            orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 100;
        orderNo += orderNum;
        return CHorderHead+orderNo;
    }

    public static synchronized String getTKOrderNo() {
        String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
//            orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 100;
        orderNo += orderNum;
        return TKorderHead+orderNo;
    }


}
