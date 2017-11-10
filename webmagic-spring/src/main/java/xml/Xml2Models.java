package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public final class Xml2Models {

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
