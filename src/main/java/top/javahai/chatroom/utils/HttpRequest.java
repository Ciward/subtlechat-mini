package top.javahai.chatroom.utils;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public class HttpRequest {

    /**
     * post/GET远程请求接口并得到返回结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法post/GET
     * @return
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,String contentType) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            //设置头信息
//            httpUrlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            httpUrlConn.setRequestProperty("Content-Type",contentType);
            httpUrlConn.setRequestProperty("Accept","application/json;charset=UTF-8");
            // 设置连接主机服务器的超时时间：15000毫秒
            httpUrlConn.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            httpUrlConn.setReadTimeout(60000);
            if ("GET".equalsIgnoreCase(requestMethod)){
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
        return jsonObject;
    }

}