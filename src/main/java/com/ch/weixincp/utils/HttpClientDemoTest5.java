//package com.ch.weixincp.utils;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.ArrayList;
//
//public class HttpClientDemoTest5 {
//    public static final String BASEURL = "http://xxx.xxx.xxx/";
//    public static final String LOGIN = "default2.aspx";
//    public static final String HIDDEN_NAME = "__VIEWSTATE";
//    public static final String HIDDEN_VALUE = "dDwxNTMxMDk5Mzc0Ozs+lYSKnsl/mKGQ7CKkWFJpv0btUa8=";
//    public static final String LOGIN_USERNAME = "txtUserName";
//    public static final String LOGIN_PASSWORD = "TextBox2";
//    public static final String LOGIN_VALIDE_CODE = "txtSecretCode";
//    public static final String URL_VALIDE_CODE = "CheckCode.aspx";
//
//    public static void main(String[] args) throws IOException {
//
//        // 创建一个HttpClient
//        HttpClient httpClient = HttpClients.createDefault();
//        // 首先发送一个请求，和该网站建立连接
//        HttpGet httpGet = new HttpGet(BASEURL);
//        // 发送请求，得到响应HttpResponse
//        HttpResponse first = httpClient.execute(httpGet);
//        // 获取headers中的Cookie，因为Cookie中有sessionID
//        Header[] headers = first.getHeaders("Set-Cookie");
//        String sessionId = headers[0].getValue();
//        // 重新建立一个header
//        Header session = new BasicHeader("Cookie", sessionId);
//        // 获取验证码的请求
//        HttpGet validateCode = new HttpGet(BASEURL + "/" + URL_VALIDE_CODE);
//        // 它的响应是一张图片，将图片写入文件保存
//        HttpResponse response = httpClient.execute(validateCode);
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            File file = new File("validate.png");
//            if (file.exists())
//                file.delete();
//            InputStream inputStream = entity.getContent();
//            Files.copy(inputStream, file.toPath());
//        }
//        // 发送POST请求，模拟登录
//        HttpPost httpPost = new HttpPost("http://222.24.62.120/" + LOGIN);
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair(HIDDEN_NAME, HIDDEN_VALUE));
//        params.add(new BasicNameValuePair(LOGIN_USERNAME, "xxxxxxx"));
//        params.add(new BasicNameValuePair("TextBox1", "xxxxxxx"));
//        params.add(new BasicNameValuePair(LOGIN_PASSWORD, "xxxxxxxxx"));
//        params.add(new BasicNameValuePair("RadioButtonList1", "学生"));
//        params.add(new BasicNameValuePair("Button1", ""));
//        Scanner scanner = new Scanner(System.in);
//        // 输入验证码
//        String code = scanner.nextLine();
//        params.add(new BasicNameValuePair(LOGIN_VALIDE_CODE, code));
//        httpPost.setEntity(new UrlEncodedFormEntity(params));
//        // 如果状态是302，那么表示登录成功。这里是有一个跳转，但是HttpClient并不会跳转
//        if (response.getStatusLine().getStatusCode() == 302) {
//            System.out.println("登录成功");
//        }
//        // 获取跳转的url
//        Header headerLocation = response.getFirstHeader("Location");
//        String location =  headerLocation.getValue();
//        System.out.println(location);
//        HttpGet mypage = new HttpGet(BASEURL + location);
//        // 这个需要添加上面保存的session，是因为下面的httpClient发送请求的时候会一直发送，无法产生结果
//        // 这个具体不知道是为什么，所以在这里重新创建了一个httpClient，如下
//        mypage.setHeader(session);
////        HttpResponse mypageResponse = httpClient.execute(mypage);
//        HttpResponse mypageResponse = HttpClients.createDefault().execute(mypage);
//        HttpEntity mypageEntiry = mypageResponse.getEntity();
//        if (mypageEntiry != null) {
//            InputStream content = mypageEntiry.getContent();
//            byte[] bytes = new byte[1024];
//            while (content.read(bytes) != -1)
//                System.out.println(new String(bytes, "GBK"));
//        }
//    }
//
//}