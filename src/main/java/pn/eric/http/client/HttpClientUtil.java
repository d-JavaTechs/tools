package pn.eric.http.client;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
/**
 * @author Shadow
 * @date
 */
public class HttpClientUtil {
    public static String methodPost(String url,NameValuePair[] data){

        String response= "";//要返回的response信息
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        // 将表单的值放入postMethod中
//        postMethod.setRequestBody(data);
        // 执行postMethod
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
        // 301或者302
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            // 从头中取出转向的地址
            Header locationHeader = getMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("The page was redirected to:" + location);
                response= methodPost(location,data);//用跳转后的页面重新请求。
            } else {
                System.err.println("Location field value is null.");
            }
        } else {
            System.out.println(getMethod.getStatusLine());

            try {
                response= getMethod.getResponseBodyAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getMethod.releaseConnection();
        }
        return response;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String url = "https://www.tmall.com";


//        NameValuePair name=new NameValuePair("name", "allen");
//        NameValuePair password=new NameValuePair("password", "allen");
//
//        NameValuePair[] data = {name,password};


        String response= methodPost(url,null);

        System.out.println("********"+response);


    }
}
