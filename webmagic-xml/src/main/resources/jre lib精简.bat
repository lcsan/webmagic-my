@echo off
set /p input=爬虫配置xml文件：
java -jar -verbose:class webmagic-xml.jar --run %input% >> class.txt
javac -cp .;commons-io-2.4.jar CutJar.java
set /p jreform=jre的lib目录：
set /p jreto=精简后的jre的lib目录：
java -cp .;commons-io-2.4.jar CutJar class.txt %jreform% %jreto%
pause