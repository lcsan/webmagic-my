package us.codecraft.webmagic.model.xml.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.Flag;
import us.codecraft.webmagic.model.formatter.BasicTypeFormatter;
import us.codecraft.webmagic.model.formatter.ObjectFormatter;
import us.codecraft.webmagic.model.formatter.ObjectFormatterBuilder;
import us.codecraft.webmagic.model.formatter.ObjectFormatters;

public class Field {
    private static final Logger LOGGER = LoggerFactory.getLogger(Field.class);

    private static final Map<String, Class<?>> TPMAP = new HashMap<String, Class<?>>();

    // 抽取结果名
    private String name;

    // 抽取结果类型
    private String type;

    // 抽取结果类型class
    private Class<?> clazz;

    // 结果值
    // private Object value;

    // 子模板id
    private String leafid;

    // url发现标识
    private boolean foundflag = false;

    // 值往后传递
    private boolean transmitflag = false;

    // 保存在对象结果
    private boolean saveflag = true;

    // 对应规则
    private Extract extract;

    // 格式化规则
    private Format format;

    private java.lang.reflect.Field field;

    // 格式化工具
    private ObjectFormatter<?> objectFormatter;

    public Field() {
        super();
    }

    public Field(java.lang.reflect.Field field) {
        this.field = field;
        this.name = field.getName();
        this.clazz = field.getType();
        this.type = clazz.getName();
        // 基础类型或基础对象类型
        if (!clazz.isPrimitive() && !type.startsWith("java.lang")) {
            // List,Map,Set等类型
            if (type.startsWith("java.util")) {
                // 关键的地方，如果是List类型，得到其Generic的类型(泛型)
                Type fc = field.getGenericType();
                if (fc != null) {
                    // 如果是泛型参数的类型
                    if (fc instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) fc;
                        // 得到泛型里的class类型对象。
                        Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                        String typeName = genericClazz.getName();
                        if (!typeName.startsWith("java.lang") && !typeName.startsWith("java.util")) {
                            // 子模板抽取
                            this.leafid = typeName;
                        }
                    }
                }
            } else {
                this.leafid = type;
            }
        }
        ExtractBy extractBy = field.getAnnotation(ExtractBy.class);
        if (null != extractBy) {
            this.extract = new Extract(extractBy);
        }
        ExtractByUrl extractByUrl = field.getAnnotation(ExtractByUrl.class);
        if (null != extractByUrl) {
            this.extract = new Extract(extractByUrl);
        }
        ComboExtract comboExtract = field.getAnnotation(ComboExtract.class);
        if (null != comboExtract) {
            this.extract = new Extract(comboExtract);
        }
        this.objectFormatter = new ObjectFormatterBuilder().setField(field).build();

        Flag flag = field.getAnnotation(Flag.class);
        if (null != flag) {
            this.foundflag = flag.isAddTagUrl();
            this.transmitflag = flag.isTransmit();
            this.saveflag = flag.isSave();
        }
    }

    static {
        if (TPMAP.isEmpty()) {
            TPMAP.put("byte", Byte.class);
            TPMAP.put("char", Character.class);
            TPMAP.put("character", Character.class);
            TPMAP.put("short", Short.class);
            TPMAP.put("int", Integer.class);
            TPMAP.put("integer", Integer.class);
            TPMAP.put("long", Long.class);
            TPMAP.put("double", Double.class);
            TPMAP.put("float", Float.class);
            TPMAP.put("str", String.class);
            TPMAP.put("string", String.class);
            TPMAP.put("boolean", Boolean.class);
            TPMAP.put("list", List.class);
            TPMAP.put("set", Set.class);
            TPMAP.put("object", Object.class);
        }
    }

    public String getType() {
        return type;
    }

    public Class<?> getClazz() {
        if (null == clazz) {
            type = null == type ? "string" : type;
            clazz = TPMAP.get(type.toLowerCase(Locale.getDefault()));
        }
        return clazz;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getLeafid() {
        return leafid;
    }

    @XmlAttribute(name = "leafid")
    public void setLeafid(String leafid) {
        this.leafid = leafid;
    }

    public Extract getExtract() {
        if (List.class.isAssignableFrom(getClazz())) {
            if (null != extract) {
                extract.setMulti(true);
            }
        }
        return extract;
    }

    @XmlElement(name = "extract")
    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public Format getFormat() {
        return format;
    }

    @XmlElement(name = "format")
    public void setFormat(Format format) {
        this.format = format;
    }

    public boolean isMulti() {
        return extract.isMulti() || List.class.isAssignableFrom(getClazz());
    }

    public boolean isFoundflag() {
        return foundflag;
    }

    @XmlAttribute(name = "foundflag")
    public void setFoundflag(boolean foundflag) {
        this.foundflag = foundflag;
    }

    public boolean isTransmitflag() {
        return transmitflag;
    }

    @XmlAttribute(name = "transmitflag")
    public void setTransmitflag(boolean transmitflag) {
        this.transmitflag = transmitflag;
    }

    public boolean isSaveflag() {
        return saveflag;
    }

    @XmlAttribute(name = "saveflag")
    public void setSaveflag(boolean saveflag) {
        this.saveflag = saveflag;
    }

    // public Object getValue() {
    // return value;
    // }
    //
    // public void setValue(Object value) {
    // this.value = value;
    // }

    public java.lang.reflect.Field getField() {
        return field;
    }

    public ObjectFormatter<?> getObjectFormatter() {
        // String\Object\List不需要转化类型
        if (null == objectFormatter && !isMulti() && !String.class.isAssignableFrom(getClazz())
                && !getClazz().getName().contains("Object")) {
            Class<?> fieldClazz = BasicTypeFormatter.detectBasicClass(getClazz());
            objectFormatter = initFormatter(ObjectFormatters.get(fieldClazz));
        }
        return objectFormatter;
    }

    @SuppressWarnings("rawtypes")
    private ObjectFormatter<?> initFormatter(Class<? extends ObjectFormatter> formatterClazz) {
        try {
            return formatterClazz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.warn("Create New Instance Error[InstantiationException]!");
        } catch (IllegalAccessException e) {
            LOGGER.warn("Create New Instance Error[IllegalAccessException]!");
        }
        return null;
    }

    @Override
    public String toString() {
        return "Field [name=" + name + ", type=" + type + ", clazz=" + clazz + ", leafid=" + leafid + ", foundflag="
                + foundflag + ", transmitflag=" + transmitflag + ", saveflag=" + saveflag + ", extract=" + extract
                + ", format=" + format + ", field=" + field + ", objectFormatter=" + objectFormatter + "]";
    }

}
