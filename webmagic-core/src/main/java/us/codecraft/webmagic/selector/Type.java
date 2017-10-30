package us.codecraft.webmagic.selector;

public enum Type {
    /**
     * xpath
     */
    xpath("xpath", 1),
    /**
     * css
     */
    css("css", 2),
    /**
     * json
     */
    json("json", 3),
    /**
     * regex
     */
    regex("regex", 4),
    /**
     * replace
     */
    replace("replace", 5),
    /**
     * filter
     */
    filter("filter", 6),
    /**
     * mixe
     */
    mixe("mixe", 7);

    private int code;

    private String name;

    Type(String name, int code) {
        this.code = code;
        this.name = name;
    }

    public static Type getType(int code) {
        for (Type m : Type.values()) {
            if (code == m.code) {
                return m;
            }
        }
        return Type.xpath;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
