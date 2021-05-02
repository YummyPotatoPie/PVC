#include <windows.h>
#include <shellapi.h>
#include <string>

std::string argsToString(int argc, char* argv[]) {
    std::string result = "";

    for (int i = 1; i < argc; i++) {
        result += argv[i];
        result += " ";
    }

    return result;
}

int main(int argc, char* argv[]) {

    std::string pvcArgsString = argsToString(argc, argv);
    std::wstring pvcJarExecute = L"/K java -jar pvc.jar ";
    std::wstring pvcArgsWstring = std::wstring(pvcArgsString.begin(), pvcArgsString.end());
    std::wstring executeCommand = pvcJarExecute + pvcArgsWstring;

    ShellExecute(NULL, L"open", L"cmd.exe", executeCommand.c_str() , NULL, SW_HIDE);
    return 0;
}
