package com.lnsafety.train;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Testr {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {

            String[] Cmd = new String[] { "wscript", "H:/Program Files/次世代/次世代验证码识别系统2.3破解版/各语言调用例子/com/new 1.vbs" };
            Process p = Runtime.getRuntime().exec(Cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
