@ECHO OFF
@setlocal enableextensions
@cd /d "%~dp0"
 
set PGPASSWORD=123456
SET PGPATH="c:\Program Files\PostgreSQL\9.6\bin\"
SET SVPATH="F:\bak\"
SET PRJDB=yjk
SET DBUSR=postgres
FOR /F "TOKENS=1,2,3 DELIMS=/ " %%i IN ('DATE /T') DO SET d=%%i-%%j-%%k
 
SET DBDUMP=%PRJDB%_%d%.bak
@ECHO OFF
%PGPATH%pg_dump -h localhost -U postgres %PRJDB% > %SVPATH%%DBDUMP%

echo Backup Taken Complete %SVPATH%%DBDUMP%

forfiles /p %SVPATH% /d -5 /c "cmd /c echo deleting @file ... && del /f @path"
