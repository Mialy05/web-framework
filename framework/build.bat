jar cfv framework.jar -C bin/ .
copy ".\framework.jar" "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\web-framework\test-framework\WEB-INF\lib"
del ".\framework.jar"
@REM cd "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\web-framework\test-framework\"
@REM .\deploy.bat