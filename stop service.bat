@echo off
setlocal enabledelayedexpansion

set PORT=80

for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr :%PORT%') do (
    set PID=%%a
)

if defined PID (
    echo Terminating process with PID %PID%...
    taskkill /F /PID %PID%
) else (
    echo No process found listening on port %PORT%.
)

pause
