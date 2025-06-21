@echo off
echo ========================================
echo Ejecutando Tests con Cobertura JaCoCo
echo ========================================
echo.

echo Limpiando proyecto anterior...
call mvn clean

echo.
echo Ejecutando tests con cobertura...
call mvn test jacoco:report

echo.
echo Verificando umbral de cobertura...
call mvn jacoco:check

echo.
echo ========================================
echo Proceso completado!
echo ========================================
echo.
echo Reporte HTML disponible en: target/site/jacoco/index.html
echo Reporte XML disponible en: target/site/jacoco/jacoco.xml
echo.
pause 