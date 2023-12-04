#include<iostream>
#include<fstream>
#include<string>
#include<Windows.h>
#include"message.h"
#include<vector>


HANDLE launchProcess(std::wstring commandLine) {
    STARTUPINFO startupInfo;
    PROCESS_INFORMATION processInfo;
    ZeroMemory(&startupInfo, sizeof(startupInfo));
    startupInfo.cb = sizeof(startupInfo);

    if (!CreateProcess(NULL, (LPWSTR)commandLine.c_str(), NULL, NULL,
        FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &startupInfo, &processInfo)) {
        return NULL;
    }

    CloseHandle(processInfo.hThread);
    return processInfo.hProcess;
}

int main() {
    std::wstring fileName;
    std::cout << "Enter file name:";
    std::wcin >> fileName;

    std::fstream file(fileName, std::ios::binary | std::ios::out);
    int numRecords;
    std::cout << "Enter number of records:";
    std::cin >> numRecords;

    if (!file.is_open()) {
        std::cout << "Error while creating file!";
        return 0;
    }
    file.close();

    int numSenders;
    std::cout << "Enter number of Senders:";
    std::cin >> numSenders;

    HANDLE* senderHandles = new HANDLE[numSenders];
    HANDLE* eventHandles = new HANDLE[numSenders];

    HANDLE mutex = CreateMutex(NULL, FALSE, L"mutex");
    HANDLE writeSemaphore = CreateSemaphore(NULL, numRecords, numRecords, L"write_sem");
    HANDLE readSemaphore = CreateSemaphore(NULL, 0, numRecords, L"read_sem");

    if (!mutex || !writeSemaphore || !readSemaphore) {
        std::cout << "Error";
        return -1;
    }

    for (int i = 0; i < numSenders; ++i) {
        std::wstring commandLine = L"Sender.exe " + fileName + L" " + std::to_wstring(numRecords) + L" " + std::to_wstring(i);

        HANDLE eventHandle = CreateEvent(NULL, FALSE, FALSE, (std::to_wstring(i) + L"ready").c_str());
        eventHandles[i] = eventHandle;
        senderHandles[i] = launchProcess(commandLine);

        if (senderHandles[i] == NULL) {
            std::cout << "Error while creating process";
            return -1;
        }
    }

    WaitForMultipleObjects(numSenders, eventHandles, TRUE, INFINITE);

    int action = 1;

    while (true) {
        std::cout << "1 to read message" << "\n";
        std::cout << "0 to exit" << "\n";
        std::cin >> action;

        if (action != 0 && action != 1) {
            std::cout << "Unknown command";
            continue;
        }

        if (action == 0) {
            break;
        }

        WaitForSingleObject(readSemaphore, INFINITE);
        WaitForSingleObject(mutex, INFINITE);

        file.open(fileName, std::ios::binary | std::ios::in);
        message mes;
        file >> mes;
        std::cout << "new message:" << mes.get_text() << "\n";
        std::vector<message> fileText;

        while (file >> mes) {
            fileText.push_back(mes);
        }

        file.close();
        file.open(fileName, std::ios::binary | std::ios::out);

        for (int i = 0; i < fileText.size(); ++i) {
            file << fileText[i];
        }

        file.close();
        ReleaseMutex(mutex);
        ReleaseSemaphore(writeSemaphore, 1, NULL);
    }

    for (int i = 0; i < numSenders; ++i) {
        CloseHandle(eventHandles[i]);
        CloseHandle(senderHandles[i]);
    }

    delete[] eventHandles;
    delete[] senderHandles;

    CloseHandle(mutex);
    CloseHandle(readSemaphore);
    CloseHandle(writeSemaphore);

    return 0;
}


//#include <iostream>
//#include <fstream>
//#include <string>
//#include <Windows.h>
//#include "message.h"
//#include <vector>
//#include <string>
//
//
//HANDLE launchProcess(std::wstring commandLine) {
//    STARTUPINFO startupInfo;
//    PROCESS_INFORMATION processInfo;
//    ZeroMemory(&startupInfo, sizeof(startupInfo));
//    startupInfo.cb = sizeof(startupInfo);
//
//    if (!CreateProcess(NULL, (LPWSTR)commandLine.c_str(), NULL, NULL,
//        FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &startupInfo, &processInfo)) {
//        return NULL;
//    }
//
//    CloseHandle(processInfo.hThread);
//    return processInfo.hProcess;
//}
//
//int main() {
//    std::wstring fileName;
//    std::wcout << L"Enter file name:";
//    std::wcin >> fileName;
//
//    std::wfstream file(fileName, std::ios::binary | std::ios::out);
//    int numRecords;
//    std::wcout << L"Enter number of records:";
//    std::wcin >> numRecords;
//
//    if (!file.is_open()) {
//        std::wcout << L"Error while creating file!";
//        return 0;
//    }
//    file.close();
//
//    int numSenders;
//    std::wcout << L"Enter number of Senders:";
//    std::wcin >> numSenders;
//
//    HANDLE* senderHandles = new HANDLE[numSenders];
//    HANDLE* eventHandles = new HANDLE[numSenders];
//
//    HANDLE mutex = CreateMutex(NULL, FALSE, L"mutex");
//    HANDLE writeSemaphore = CreateSemaphore(NULL, numRecords, numRecords, L"write_sem");
//    HANDLE readSemaphore = CreateSemaphore(NULL, 0, numRecords, L"read_sem");
//
//    if (!mutex || !writeSemaphore || !readSemaphore) {
//        std::wcout << L"Error";
//        return -1;
//    }
//
//    for (int i = 0; i < numSenders; ++i) {
//        std::wstring commandLine = L"Sender.exe " + fileName + L" " + std::to_wstring(numRecords) + L" " + std::to_wstring(i);
//
//        HANDLE eventHandle = CreateEvent(NULL, FALSE, FALSE, (std::to_wstring(i) + L"ready").c_str());
//        eventHandles[i] = eventHandle;
//        senderHandles[i] = launchProcess(commandLine);
//
//        if (senderHandles[i] == NULL) {
//            std::wcout << L"Error while creating process";
//            return -1;
//        }
//    }
//
//    WaitForMultipleObjects(numSenders, eventHandles, TRUE, INFINITE);
//
//    int action = 1;
//
//    while (true) {
//        std::wcout << L"1 to read message" << L"\n";
//        std::wcout << L"0 to exit" << L"\n";
//        std::wcin >> action;
//
//        if (action != 0 && action != 1) {
//            std::wcout << L"Unknown command";
//            continue;
//        }
//
//        if (action == 0) {
//            break;
//        }
//
//        WaitForSingleObject(readSemaphore, INFINITE);
//        WaitForSingleObject(mutex, INFINITE);
//
//        file.open(fileName, std::ios::binary | std::ios::in);
//        message mes;
//        file >> mes;
//        std::wcout << L"new message:" << mes.get_text() << L"\n";
//        std::vector<message> fileText;
//
//        while (file >> mes) {
//            fileText.push_back(mes);
//        }
//
//        file.close();
//        file.open(fileName, std::ios::binary | std::ios::out);
//
//        for (int i = 0; i < fileText.size(); ++i) {
//            file << fileText[i];
//        }
//
//        file.close();
//        ReleaseMutex(mutex);
//        ReleaseSemaphore(writeSemaphore, 1, NULL);
//    }
//
//    for (int i = 0; i < numSenders; ++i) {
//        CloseHandle(eventHandles[i]);
//        CloseHandle(senderHandles[i]);
//    }
//
//    delete[] eventHandles;
//    delete[] senderHandles;
//
//    CloseHandle(mutex);
//    CloseHandle(readSemaphore);
//    CloseHandle(writeSemaphore);
//
//    return 0;
//}

