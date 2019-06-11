package com.lnsafety.train;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import us.codecraft.webmagic.selector.Selectors;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        String loginPage = "http://train.lnsafety.com/mnzxlogin.asp";
        String vcodeUrl = "http://train.lnsafety.com/imgchk/validatecode.asp?r=";
        String listPage = "http://train.lnsafety.com/mnzxksset.asp";
        String index = "http://train.lnsafety.com/mnzxks/index1.html";
        String cotentPage = "http://train.lnsafety.com/mnzxks/zxks.asp";

        // 访问登录页拿cookie
        Response res = Jsoup.connect(loginPage).execute();
        Map<String, String> cookies = res.cookies();

        String pwd = Selectors.$("#pwd", "value").select(res.body());
        String username = "510823198603117772";

        // 带cookie拿验证码

        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE'%20'MMM'%20'dd'%20'yyyy'%20'HH:mm:ss'%20GMT+0800%20(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)'",
                Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        res = Jsoup.connect(vcodeUrl + sdf.format(new Date())).ignoreContentType(true).cookies(cookies).execute();
        FileUtils.writeByteArrayToFile(new File("d:/validatecode/aa.dib"), res.bodyAsBytes());

        Map<String, String> data = new HashMap<String, String>();
        data.put("lx", "2");
        data.put("username", username);
        data.put("pwd", pwd);
        data.put("vold", "7374");

        // 模拟登录
        res = Jsoup.connect(loginPage).data(data).cookies(cookies).method(Method.POST).followRedirects(true).execute();
        cookies.putAll(res.cookies());

        String body = Selectors.mixe("xpath('//script').filter('zcx=').regex('zcx=\"(\\S+)\";')").select(res.body());

        // data = new HashMap<String, String>();
        // data.put("id", "");
        // data.put("lx", "");
        // data.put("gz", "5");
        // data.put("zcx", "%CF%F5%BB%AF%B9%A4%D2%D5%D7%F7%D2%B5");
        // data.put("kszl", "1");
        //
        // res =
        // Jsoup.connect(listPage).data(data).cookies(cookies).method(Method.POST).execute();

        cookies.putAll(res.cookies());

        String[] menu = body.split(",");
        for (String mu : menu) {
            if (StringUtils.isNotBlank(mu)) {
                String[] item = mu.split("~");
                String gz = item[0];
                data.put("gz", gz);
                String[] zcxs = item[1].split("\\^");
                for (String zcx : zcxs) {
                    if (StringUtils.isNotBlank(zcx)) {
                        data.put("zcx", URLEncoder.encode(zcx, "GBK"));
                        System.out.println("http://train.lnsafety.com/mnzxksset.asp?{post}id=&lx=&gz=" + gz + "&zcx="
                                + URLEncoder.encode(zcx, "GBK") + "&kszl=1{/post}");

                        // System.out.println(data);
                        // res =
                        // Jsoup.connect(listPage).data(data).cookies(cookies).method(Method.POST).execute();
                        //
                        // cookies.putAll(res.cookies());
                        // System.out.println(cookies);
                        // System.out.println(Jsoup.connect(index).cookies(cookies).execute().body());
                        // System.out.println("___________________________");
                        // System.out.println(Jsoup.connect(cotentPage).cookies(cookies).execute().body());
                        // System.out.println("___________________________");
                    }
                }
            }
        }
    }

}
