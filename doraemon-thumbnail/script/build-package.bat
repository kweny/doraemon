set JAVA_HOME=%1
set MODULE_PATH=%2
set INPUT=%3
set OUTPUT=%4
set JAR=%5
set VERSION=%6
set APP_ICON=%7

call "%JAVA_HOME%\bin\java.exe" ^
    -Xmx512M ^
    --module-path "%JAVA_HOME%\jmods" ^
    --add-opens jdk.jlink/jdk.tools.jlink.internal.packager=jdk.packager ^
    -m jdk.packager/jdk.packager.Main ^
    create-image ^
    --module-path "%MODULE_PATH%" ^
    --verbose ^
    --echo-mode ^
    --add-modules "org.kweny.doraemon.thumbnail" ^
    --input "%INPUT%" ^
    --output "%OUTPUT%" ^
    --name "Thumbnail" ^
    --main-jar "%JAR%" ^
    --version "%VERSION%" ^
    --jvm-args "--add-opens javafx.base/com.sun.javafx.reflect=ALL-UNNAMED" ^
    --icon "%APP_ICON%" ^
    --class "org.kweny.doraemon.thumbnail.ThumbnailApplication"