package com.vainglory.utils;

/*
 * wgy 2019/9/10 16:22
 * 佛祖保佑，永无BUG!
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

//生成邮箱验证 激活码 使用的随机数
public class RandomUtils {
    //当前时间 + 随机数
    public static String createActive(){

        return getTime()+Integer.toHexString(new Random().nextInt(900)+100);
    }
    public static String getTime(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
    }
    //生成订单编号
    public static String createOrderId(){
        return getTime();
    }
}
