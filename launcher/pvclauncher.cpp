#include <windows.h>
#include <shellapi.h>
#include <string>
#include <iostream>

std::string argsToString(int argc, char* argv[]) {
    std::string result = "";

    for (int i = 1; i < argc; i++) {
        result += argv[i];
        result += " ";
    }

    return result;
}

int main(int argc, char* argv[]) {

    char path[MAX_PATH];
    GetModuleFileNameA(NULL, path, MAX_PATH);
    std::string pathToPVCJar = std::string(path);
    pathToPVCJar = pathToPVCJar.substr(0, pathToPVCJar.size() - 8);

    std::string pvcArgsString = argsToString(argc, argv);
    std::wstring pvcJarPath = std::wstring(pathToPVCJar.begin(), pathToPVCJar.end()) + L"\\pvc.jar ";
    std::wstring pvcJarExecute = L"java -jar " + pvcJarPath;
    std::wstring pvcArgsWstring = std::wstring(pvcArgsString.begin(), pvcArgsString.end());
    std::wstring executeCommand = pvcJarExecute + pvcArgsWstring;

    //Deprecated
    //ShellExecute(NULL, L"open", L"cmd.exe", executeCommand.c_str() , NULL, SW_HIDE);
    
    PROCESS_INFORMATION ProcessInfo;
    STARTUPINFO StartUpInfo;

    ZeroMemory(&StartUpInfo, sizeof(StartUpInfo));
    StartUpInfo.cb = sizeof StartUpInfo; 

    if (CreateProcess(NULL, LPWSTR(executeCommand.c_str()), NULL, NULL, FALSE, 0, NULL, NULL, &StartUpInfo, &ProcessInfo)) {
        WaitForSingleObject(ProcessInfo.hProcess, INFINITE);
        CloseHandle(ProcessInfo.hThread);
        CloseHandle(ProcessInfo.hProcess);
    }
    else {
        std::cout << "pvc cannot be executed" << std::endl;
    }
    
    return 0;
}
