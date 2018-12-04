![logo](http://webmagic.io/images/logo.jpeg)


[![Build Status](https://travis-ci.org/code4craft/webmagic.png?branch=master)](https://travis-ci.org/code4craft/webmagic)


官方网站[http://webmagic.io/](http://webmagic.io/)

>基于webmagic 0.7.3，增强选择器，支持选择器链式执行：
<br\>
  混合选择器 MixeSelector，就是一个表达式执行器，支持xpath|css|json|regex|replace|filter|split各种选择器表达式化。
```java
    // MixeSelector为默认选择器
    @ExtractBy("xpath('//a/text()').filter('\\S+').replace('^[\\s\\d、]+(.*?)[\\sABCD]+$','$1')")
    private String title;

    @ExtractBy("xpath('//div/text()').split('\\s+')")
    private List<String> answer;	
```
或者
```java
    new MixeSelector("xpath('//a/text()').filter('\\S+').replace('^[\\s\\d、]+(.*?)[\\sABCD]+$','$1')").select(dom);
    new MixeSelector("xpath('//a/text()').filter('\\S+').replace('^[\\s\\d、]+(.*?)[\\sABCD]+$','$1')").selectList(dom);
```
  拆分选择器 SplitSelector，将字符串拆成数组。
```java
    @ExtractBy(value="\\s+",type=Type.split)
    private List<String> answer;
```
或者
```java
   new SplitSelector("\\s+").select(dom);
   new SplitSelector("\\s+").selectList(dom);
```
过滤选择器 FilterSelector，value为正则表达式，前后自动补齐^和$，支持正向和反向过滤。
···java
    @ExtractBy(value="\\S+",type=Type.filter)
    private String answer;
···
或者
```java
    new FilterSelector("\\S+").select(dom);
```
替换选择器 ReplaceSelector，支持替换和提取。提取和RegexSelector效果差不多
```java
    @ExtractBy(value="'^[\\s\\d、]+(.*?)[\\sABCD]+$','$1'",type=Type.replace)
    private String title;
```
或者
```java
   new ReplaceSelector("^[\\s\\d、]+(.*?)[\\sABCD]+$", "$1").select(dom);
   new ReplaceSelector("'^[\\s\\d、]+(.*?)[\\sABCD]+$','$1'").select(dom);
```
