package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;

public class Database {

    private String name;
    private String type;
    private String driver;
    private String url;
    private String userName;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxIdleTime;
    private int idleConnectionTestPeriod;
    private int maxPoolSize;
    private int minPoolSize;
    private int initialPoolSize;
    private DruidPlugin dp;
    private ActiveRecordPlugin arp;

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    @XmlElement(name = "driver")
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    @XmlElement(name = "url")
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    @XmlElement(name = "userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    @XmlElement(name = "initialSize")
    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    @XmlElement(name = "minIdle")
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    @XmlElement(name = "maxActive")
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    @XmlElement(name = "maxIdleTime")
    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    @XmlElement(name = "idleConnectionTestPeriod")
    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    @XmlElement(name = "maxPoolSize")
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    @XmlElement(name = "minPoolSize")
    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    @XmlElement(name = "initialPoolSize")
    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public void start() {
        if (dp == null) {
            if (driver != null) {
                dp = new DruidPlugin(url, userName, password, driver);
            } else {
                dp = new DruidPlugin(url, userName, password);
            }
        }
        if (arp == null) {
            arp = new ActiveRecordPlugin(getName(), dp);
            Integer dialect = Common.DM_MAP.get(type);
            switch (dialect) {
            case 6:
                arp.setDialect(new AnsiSqlDialect());
                break;
            case 2:
                arp.setDialect(new OracleDialect());
                break;
            case 3:
                arp.setDialect(new PostgreSqlDialect());
                break;
            case 4:
                arp.setDialect(new SqlServerDialect());
                break;
            case 5:
                arp.setDialect(new Sqlite3Dialect());
                break;
            case 1:
            default:
                arp.setDialect(new MysqlDialect());
                break;
            }
        }
        dp.start();
        arp.start();
    }

    public void stop() {
        if (arp != null) {
            dp.stop();
        }
        if (dp != null) {
            dp.stop();
        }
    }

    @Override
    public String toString() {
        return "Database [driver=" + driver + ", url=" + url + ", userName=" + userName + ", password=" + password
                + ", initialSize=" + initialSize + ", minIdle=" + minIdle + ", maxActive=" + maxActive
                + ", maxIdleTime=" + maxIdleTime + ", idleConnectionTestPeriod=" + idleConnectionTestPeriod
                + ", maxPoolSize=" + maxPoolSize + ", minPoolSize=" + minPoolSize + ", initialPoolSize="
                + initialPoolSize + "]";
    }

}
