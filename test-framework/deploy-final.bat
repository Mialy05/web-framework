@REM peth vers le répertoire tmp de l'app web
set path=C:\Users\Mialisoa\ITU\S4\prog-sys\test-framework-tmp

@REM  supprimer s'il existe déjà
if exist %path% rmdir /S /Q %path%

mkdir %path% %path%\WEB-INF %path%\WEB-INF\classes %path%\WEB-INF\lib %path%\views

@REM copier les dossiers
copy .\views %path%\views
copy test.jsp %path%
copy  .\WEB-INF\lib %path%\WEB-INF\lib
copy  .\WEB-INF\web.xml %path%\WEB-INF\web.xml
C:\Windows\System32\xcopy.exe /s  .\WEB-INF\classes C:\Users\Mialisoa\ITU\S4\prog-sys\test-framework-tmp\WEB-INF\classes 

@REM créer le fichier .war et le déploier dans tomcat
cd %path%
"C:\Program Files\Java\jdk-14.0.1\bin\jar.exe" cvf test-framework2.war -C . . 
copy test-framework2.war "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\"