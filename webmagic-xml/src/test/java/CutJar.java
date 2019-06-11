import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class CutJar {
    private static String sourse = "C:/Users/Administrator/Desktop/webmagic-xml-1.0.0/crawler/class.txt";
    private static String copypath = "D:/JAVA/jdk1.8.0_181/jre/lib/";
    private static String pastpath = "C:/Users/Administrator/Desktop/aaa/jre/lib/";

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("参数错误  clss列表文件，jre的lib目录（jar已解压），class文件复制到的lib目录");
            return;
        }
        sourse = args[0];
        copypath = args[1];
        pastpath = args[2];

        List<String> list = FileUtils.readLines(new File(sourse));
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        Pattern pat = Pattern.compile("^\\[Loaded (.*?) from (.*?)([^\\\\]+)\\.jar\\]$");

        for (String str : list) {
            Matcher ma = pat.matcher(str);
            if (ma.find()) {
                String clazz = ma.group(1).replaceAll("\\.", "/");
                String path = ma.group(2);
                String jar = ma.group(3);

                if (path.contains("jre1.")) {
                    if (map.containsKey(jar)) {
                        map.get(jar).add(clazz);
                    } else {
                        Set<String> st = new HashSet<String>();
                        st.add(clazz);
                        map.put(jar, st);
                    }
                }
            }
        }

        for (String key : map.keySet()) {
            FileUtils.copyFileToDirectory(new File(copypath + "/" + key + ".jar"), new File(pastpath + "/"));
        }

        for (Entry<String, Set<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<String> value = entry.getValue();
            for (String string : value) {
                String path = "/" + key + "/" + string + ".class";
                File fe = new File(copypath + path);
                if (fe.exists()) {
                    FileUtils.copyFile(new File(copypath + path), new File(pastpath + path));
                } else {
                    System.err.println(path + " not exists!");
                }
            }
        }

    }
}
