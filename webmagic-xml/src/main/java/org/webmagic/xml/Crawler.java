package org.webmagic.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.webmagic.xml.bean.Spider;

import us.codecraft.webmagic.model.xml.Xml2Models;

public class Crawler {

    public static void main(String[] args) throws IOException {
        if (null != args && args.length == 2) {
            if (args[0].equals("--create")) {
                File fe = new File("./" + args[1]);
                FileOutputStream fo = new FileOutputStream(fe);
                fo.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes("UTF-8"));
                fo.write("<spider>\r\n".getBytes("UTF-8"));
                fo.write("	<site>\r\n".getBytes("UTF-8"));
                fo.write("		<domain>iqiyi</domain>\r\n".getBytes("UTF-8"));
                fo.write("		<userAgent>\r\n".getBytes("UTF-8"));
                fo.write("		  <![CDATA[\r\n".getBytes("UTF-8"));
                fo.write(
                        "		      Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 QIHU 360SE\r\n"
                                .getBytes("UTF-8"));
                fo.write("			]]>\r\n".getBytes("UTF-8"));
                fo.write("		</userAgent>\r\n".getBytes("UTF-8"));
                fo.write("		<charset>UTF-8</charset>\r\n".getBytes("UTF-8"));
                fo.write("		<sleepTime>200</sleepTime>\r\n".getBytes("UTF-8"));
                fo.write("		<retryTimes>1</retryTimes>\r\n".getBytes("UTF-8"));
                fo.write("		<cycleRetryTimes>1</cycleRetryTimes>\r\n".getBytes("UTF-8"));
                fo.write("		<retrySleepTime>200</retrySleepTime>\r\n".getBytes("UTF-8"));
                fo.write("		<timeout>5000</timeout>\r\n".getBytes("UTF-8"));
                fo.write("		<useGzip>true</useGzip>\r\n".getBytes("UTF-8"));
                fo.write("		<disableCookieManagement>false</disableCookieManagement>\r\n".getBytes("UTF-8"));
                fo.write("		<threadSize>10</threadSize>\r\n".getBytes("UTF-8"));
                fo.write("		<resetQueue>false</resetQueue>\r\n".getBytes("UTF-8"));
                fo.write("		<usePriority>true</usePriority>\r\n".getBytes("UTF-8"));
                fo.write("		<useRedis>false</useRedis>\r\n".getBytes("UTF-8"));
                fo.write("		<useDb>false</useDb>\r\n".getBytes("UTF-8"));
                fo.write("		<url>\r\n".getBytes("UTF-8"));
                fo.write("            <![CDATA[\r\n".getBytes("UTF-8"));
                fo.write(
                        "                http://www.iqiyi.com/lib/s_215641805.html , http://www.iqiyi.com/lib/s_234967205.html\r\n"
                                .getBytes("UTF-8"));
                fo.write("            ]]>\r\n".getBytes("UTF-8"));
                fo.write("		</url>\r\n".getBytes("UTF-8"));
                fo.write("		<acceptStatCode>200</acceptStatCode>\r\n".getBytes("UTF-8"));
                fo.write("\r\n".getBytes("UTF-8"));
                fo.write("		<!-- 字符串格式 -->\r\n".getBytes("UTF-8"));
                fo.write("		<!-- <cookie> <![CDATA[ __guid=26936763.670147605910933600.1510325694223.3628; \r\n"
                        .getBytes("UTF-8"));
                fo.write(
                        "			monitor_count=1; __utma=55973678.1155684392.1510325696.1510325696.1510325696.1; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			__utmc=55973678; __utmz=55973678.1510325696.1.1.utmccn=(organic)|utmcsr=baidu|utmctr=|utmcmd=organic \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			]]> </cookie> <header> <![CDATA[ Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			Accept-Encoding:gzip, deflate, sdch Accept-Language:zh-CN,zh;q=0.8 Cache-Control:max-age=0 \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			Connection:keep-alive Cookie:__guid=26936763.670147605910933600.1510325694223.3628; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			monitor_count=1; __utma=55973678.1155684392.1510325696.1510325696.1510325696.1; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			__utmc=55973678; __utmz=55973678.1510325696.1.1.utmccn=(organic)|utmcsr=baidu|utmctr=|utmcmd=organic \r\n"
                                .getBytes("UTF-8"));
                fo.write("			Host:www.tutorialspoint.com Upgrade-Insecure-Requests:1 User-Agent:Mozilla/5.0 \r\n"
                        .getBytes("UTF-8"));
                fo.write(
                        "			(Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 \r\n"
                                .getBytes("UTF-8"));
                fo.write("			Safari/537.36 ]]> </header> -->\r\n".getBytes("UTF-8"));
                fo.write("		<!-- 多个值\",\"分隔 -->\r\n".getBytes("UTF-8"));
                fo.write("\r\n".getBytes("UTF-8"));
                fo.write("		<!-- 对象格式 -->\r\n".getBytes("UTF-8"));
                fo.write("		<!-- <cookies> <cookie key=\"a\">1</cookie> <cookie key=\"c\">2</cookie> <cookie \r\n"
                        .getBytes("UTF-8"));
                fo.write("			key=\"c\">3</cookie> </cookies> <headers> <header key=\"e\">4</header> <header \r\n"
                        .getBytes("UTF-8"));
                fo.write(
                        "			key=\"f\">5</header> <header key=\"g\">6</header> </headers> <urls> <url>f</url> \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			<url>e</url> <url>v</url> </urls> <acceptStatCodes> <acceptStatCode>200</acceptStatCode> \r\n"
                                .getBytes("UTF-8"));
                fo.write("			</acceptStatCodes> -->\r\n".getBytes("UTF-8"));
                fo.write("\r\n".getBytes("UTF-8"));
                fo.write(
                        "		<!-- <requests> <request> <url>aa</url> <method>post</method> <requestBody>sss</requestBody> \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			<priority>11</priority> <cookie> <![CDATA[ __guid=26936763.670147605910933600.1510325694223.3628; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			monitor_count=1; __utma=55973678.1155684392.1510325696.1510325696.1510325696.1; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			__utmc=55973678; __utmz=55973678.1510325696.1.1.utmccn=(organic)|utmcsr=baidu|utmctr=|utmcmd=organic \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			]]> </cookie> <header> <![CDATA[ Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			Accept-Encoding:gzip, deflate, sdch Accept-Language:zh-CN,zh;q=0.8 Cache-Control:max-age=0 \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			Connection:keep-alive Cookie:__guid=26936763.670147605910933600.1510325694223.3628; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			monitor_count=1; __utma=55973678.1155684392.1510325696.1510325696.1510325696.1; \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			__utmc=55973678; __utmz=55973678.1510325696.1.1.utmccn=(organic)|utmcsr=baidu|utmctr=|utmcmd=organic \r\n"
                                .getBytes("UTF-8"));
                fo.write("			Host:www.tutorialspoint.com Upgrade-Insecure-Requests:1 User-Agent:Mozilla/5.0 \r\n"
                        .getBytes("UTF-8"));
                fo.write(
                        "			(Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 \r\n"
                                .getBytes("UTF-8"));
                fo.write(
                        "			Safari/537.36 ]]> </header> 对象格式 <cookies> <cookie key=\"a\">1</cookie> <cookie \r\n"
                                .getBytes("UTF-8"));
                fo.write("			key=\"c\">2</cookie> <cookie key=\"c\">3</cookie> </cookies> <headers> <header \r\n"
                        .getBytes("UTF-8"));
                fo.write("			key=\"e\">4</header> <header key=\"f\">5</header> <header key=\"g\">6</header> \r\n"
                        .getBytes("UTF-8"));
                fo.write("			</headers> </request> </requests> -->\r\n".getBytes("UTF-8"));
                fo.write("	</site>\r\n".getBytes("UTF-8"));
                fo.write("	<redis>\r\n".getBytes("UTF-8"));
                fo.write("		<host>127.0.0.1</host>\r\n".getBytes("UTF-8"));
                fo.write("		<port>6379</port>\r\n".getBytes("UTF-8"));
                fo.write("		<password></password>\r\n".getBytes("UTF-8"));
                fo.write("		<timeout></timeout>\r\n".getBytes("UTF-8"));
                fo.write("		<dbIndex></dbIndex>\r\n".getBytes("UTF-8"));
                fo.write("	</redis>\r\n".getBytes("UTF-8"));
                fo.write("  <!-- mysql,oracle,pgsql,sqlserver,sqlite3,ansi -->\r\n".getBytes("UTF-8"));
                fo.write("	<database name=\"mysqldb\" type=\"mysql\">\r\n".getBytes("UTF-8"));
                fo.write("		<driver>com.mysql.jdbc.Driver</driver>\r\n".getBytes("UTF-8"));
                fo.write("		<url>\r\n".getBytes("UTF-8"));
                fo.write("		  <![CDATA[\r\n".getBytes("UTF-8"));
                fo.write(
                        "		      jdbc:mysql://ip:port/table?characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\n"
                                .getBytes("UTF-8"));
                fo.write("		  ]]>\r\n".getBytes("UTF-8"));
                fo.write("		</url>\r\n".getBytes("UTF-8"));
                fo.write("		<userName>111</userName>\r\n".getBytes("UTF-8"));
                fo.write("		<password>222</password>\r\n".getBytes("UTF-8"));
                fo.write("		<initialSize>2</initialSize>\r\n".getBytes("UTF-8"));
                fo.write("		<minIdle>1</minIdle>\r\n".getBytes("UTF-8"));
                fo.write("		<maxActive>5</maxActive>\r\n".getBytes("UTF-8"));
                fo.write("		<maxIdleTime>120</maxIdleTime>\r\n".getBytes("UTF-8"));
                fo.write("		<idleConnectionTestPeriod>60</idleConnectionTestPeriod>\r\n".getBytes("UTF-8"));
                fo.write("		<maxPoolSize>15</maxPoolSize>\r\n".getBytes("UTF-8"));
                fo.write("		<minPoolSize>10</minPoolSize>\r\n".getBytes("UTF-8"));
                fo.write("		<initialPoolSize>3</initialPoolSize>\r\n".getBytes("UTF-8"));
                fo.write("	</database>\r\n".getBytes("UTF-8"));
                fo.write("	<pipeline>\r\n".getBytes("UTF-8"));
                fo.write("	    <![CDATA[\r\n".getBytes("UTF-8"));
                fo.write("      import org.webmagic.*;\r\n".getBytes("UTF-8"));
                fo.write("      def pipeline(bean,task){\r\n".getBytes("UTF-8"));
                fo.write("	       println(bean);\r\n".getBytes("UTF-8"));
                fo.write("           println(bean.iqiyisub.name);\r\n".getBytes("UTF-8"));
                fo.write("      }\r\n".getBytes("UTF-8"));
                fo.write("	     ]]>\r\n".getBytes("UTF-8"));
                fo.write("	</pipeline>\r\n".getBytes("UTF-8"));
                fo.write("	<models>\r\n".getBytes("UTF-8"));
                fo.write("		<model>\r\n".getBytes("UTF-8"));
                fo.write("			<bean name=\"IqiyiPage\">\r\n".getBytes("UTF-8"));
                fo.write("				<field name=\"name\">\r\n".getBytes("UTF-8"));
                fo.write("					<extract expression=\"xpath('//h1[@itemprop='name']/text()')\" />\r\n"
                        .getBytes("UTF-8"));
                fo.write("				</field>\r\n".getBytes("UTF-8"));
                fo.write("				<field name=\"names\">\r\n".getBytes("UTF-8"));
                fo.write("					<extract expression=\"xpath('//h1[@itemprop='name']/text()')\"\r\n"
                        .getBytes("UTF-8"));
                fo.write("						notnull=\"false\" />\r\n".getBytes("UTF-8"));
                fo.write("				</field>\r\n".getBytes("UTF-8"));
                fo.write("\r\n".getBytes("UTF-8"));
                fo.write("				<field name=\"iqiyisub\" type=\"object\" leafid=\"iqiyi1\">\r\n"
                        .getBytes("UTF-8"));
                fo.write("					<extract expression=\"*\" />\r\n".getBytes("UTF-8"));
                fo.write("				</field>\r\n".getBytes("UTF-8"));
                fo.write("			</bean>\r\n".getBytes("UTF-8"));
                fo.write("			<tagurl>\r\n".getBytes("UTF-8"));
                fo.write("				<expression>http://www.iqiyi.com/lib/s_\\d+.html</expression>\r\n"
                        .getBytes("UTF-8"));
                fo.write("			</tagurl>\r\n".getBytes("UTF-8"));
                fo.write("		</model>\r\n".getBytes("UTF-8"));
                fo.write("		<model>\r\n".getBytes("UTF-8"));
                fo.write("			<bean name=\"iqiyi1\" leaf=\"true\">\r\n".getBytes("UTF-8"));
                fo.write("				<field name=\"name\">\r\n".getBytes("UTF-8"));
                fo.write("					<extract expression=\"//h1[@itemprop='name']/text()\" />\r\n"
                        .getBytes("UTF-8"));
                fo.write("				</field>\r\n".getBytes("UTF-8"));
                fo.write("			</bean>\r\n".getBytes("UTF-8"));
                fo.write("		</model>\r\n".getBytes("UTF-8"));
                fo.write("	</models>\r\n".getBytes("UTF-8"));
                fo.write("	<!--<task>* * * * *</task>-->\r\n".getBytes("UTF-8"));
                fo.write("</spider>\r\n".getBytes("UTF-8"));
                fo.flush();
                fo.close();
            } else if (args[0].equals("--run")) {
                Spider spider = Xml2Models.parse(args[1], Spider.class);
                spider.run();
            }
        } else {
            System.out.println("you can create a new spider config like this:");
            System.out.println("java -jar webmagic-xml.jar --create config.xml");
            System.out.println("you can run a spider like this:");
            System.out.println("java -jar webmagic-xml.jar --run config.xml");
        }
    }

}
