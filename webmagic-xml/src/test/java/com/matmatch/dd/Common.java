package com.matmatch.dd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Common {

    private static Start START;

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Common.token = token;
    }

    public static Start getSTART() {
        return START;
    }

    public static void setSTART(Start sTART) {
        START = sTART;
    }

    /**
     * url格式转json对象 https://matmatch.com/search?type=material&filters[0][name]=
     * equivalentStandards&filters[0][type]=materialProperty&filters[0][
     * filterValue]=EN 2.4668 - NiCr19Fe19Nb5Mo3&filters[1]=hahaha
     * 
     * @param str
     *            url
     */
    public static JSONObject changeJSON(String str) {
        Matcher mach = Pattern.compile("([^?=&]+)=([^?=&]+)").matcher(str);
        JSONObject json = new JSONObject();
        while (mach.find()) {
            String key = mach.group(1);
            String value = mach.group(2);
            Matcher mh = Pattern.compile("(\\w+)").matcher(key);
            String parkey = null, pparkey = null;
            while (mh.find()) {
                String ky = mh.group(1);
                JSONObject jb = json;
                if (null != parkey) {
                    JSONObject js = null;
                    // 数组
                    if (ky.matches("^\\d+$")) {
                        int index = Integer.parseInt(ky);
                        JSONArray jsary = null;
                        if (jb.get(parkey) instanceof JSONArray) {
                            jsary = jb.getJSONArray(parkey);
                            if (jsary.size() <= index || jsary.isEmpty()) {
                                jsary.set(index, value);
                            }
                        } else {
                            jsary = new JSONArray();
                            jsary.set(index, value);
                        }
                        jb.put(parkey, jsary);
                    } else {
                        if (parkey.matches("^\\d+$")) {
                            int index = Integer.parseInt(parkey);
                            JSONArray jsary = null;
                            if (jb.get(pparkey) instanceof JSONArray) {
                                jsary = jb.getJSONArray(pparkey);
                                if (jsary.get(index) instanceof JSONObject) {
                                    js = jsary.getJSONObject(index);
                                } else {
                                    js = new JSONObject();
                                    jsary.set(index, js);
                                    jb.put(pparkey, jsary);
                                }
                                js.put(ky, value);
                            }
                        } else {
                            if (jb.get(parkey) instanceof JSONObject) {
                                js = jb.getJSONObject(parkey);
                                js.put(ky, value);
                            } else {
                                js = new JSONObject();
                                js.put(ky, value);
                                jb.put(parkey, js);
                            }
                        }
                    }
                    jb = null == js ? jb : js;
                } else {
                    if (!json.containsKey(ky)) {
                        json.put(ky, value);
                    }
                }
                pparkey = parkey;
                parkey = ky;
            }
        }
        return json;
    }
}
