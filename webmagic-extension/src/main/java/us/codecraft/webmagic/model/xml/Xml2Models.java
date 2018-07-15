package us.codecraft.webmagic.model.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import us.codecraft.webmagic.model.xml.bean.Models;

public final class Xml2Models {
    private static final Logger LOG = LoggerFactory.getLogger(Xml2Models.class);

    private String[] paths;

    private Xml2Models(String... path) {
        this.paths = path;
    }

    public static Models create(String... path) {
        return new Xml2Models(path).parseModels();
    }

    public static Models create(List<String> path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        return create(path.toArray(new String[] {}));
    }

    private Models parseModels() {
        Models mds = null;
        if (null == paths || paths.length == 0) {
            LOG.error("The Model XML path was null");
            return null;
        }
        for (String path : paths) {
            Models models = parseModel(path);
            if (null != mds) {
                mds.addModel(models);
            } else {
                mds = models;
            }
        }
        return mds;
    }

    private Models parseModel(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        // 获取请求对象并进行JAXB转换处理
        // 定义对象至XML对象
        InputStream is = null;
        try {
            // 创建解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            path = path.trim();
            File fe = new File(path);
            if (fe.exists()) {
                is = new FileInputStream(fe);
            } else {
                URL url = getClass().getClassLoader().getResource(path);
                if (null == url) {
                    // 从jar包里面读取模板文件
                    is = getClass().getResourceAsStream(path);
                } else {
                    is = url.openStream();
                }
            }
            InputSource inputSource = new InputSource(is);
            Source xmlSource = new SAXSource(factory.newSAXParser().getXMLReader(), inputSource);

            // 初始化上下文对象
            JAXBContext jaxb = JAXBContext.newInstance(Models.class);
            // XML初始化对象至对象
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Models models = (Models) unmarshaller.unmarshal(xmlSource);
            return models;
        } catch (ParserConfigurationException e) {
        } catch (SAXNotRecognizedException e) {
            e.printStackTrace();
        } catch (SAXNotSupportedException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T parse(String path, Class<?> clazz) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        // 获取请求对象并进行JAXB转换处理
        // 定义对象至XML对象
        InputStream is = null;
        try {
            // 创建解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            path = path.trim();
            File fe = new File(path);
            if (fe.exists()) {
                is = new FileInputStream(fe);
            } else {
                URL url = Xml2Models.class.getClassLoader().getResource(path);
                if (null == url) {
                    // 从jar包里面读取模板文件
                    is = Xml2Models.class.getResourceAsStream(path);
                } else {
                    is = url.openStream();
                }
            }
            if (is == null) {
                throw new NullPointerException("The xml model is not exist!");
            }
            InputSource inputSource = new InputSource(is);
            Source xmlSource = new SAXSource(factory.newSAXParser().getXMLReader(), inputSource);

            // 初始化上下文对象
            JAXBContext jaxb = JAXBContext.newInstance(clazz);
            // XML初始化对象至对象
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            return (T) unmarshaller.unmarshal(xmlSource);
        } catch (ParserConfigurationException e) {
        } catch (SAXNotRecognizedException e) {
            e.printStackTrace();
        } catch (SAXNotSupportedException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
