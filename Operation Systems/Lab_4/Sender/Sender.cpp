#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
#include "../Lab_4/message.h"

int main(int argc, char* argv[]) {
    HANDLE readyEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, (std::to_wstring(atoi(argv[3])) + L"ready").c_str());
    HANDLE mutex = OpenMutex(MUTEX_ALL_ACCESS, FALSE, L"mutex");
    HANDLE writeSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"write_sem");
    HANDLE readSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"read_sem");

    if (!readyEvent || !readSemaphore || !writeSemaphore || !mutex) {
        std::cout << "Error" << "\n";
        return -1;
    }

    SetEvent(readyEvent);

    std::fstream file;
    int action;

    while (true) {
        std::cout << "1 to write message" << "\n";
        std::cout << "0 to exit" << "\n";
        std::cin >> action;

        if (action != 0 && action != 1) {
            std::cout << "Unknown command" << "\n";
            continue;
        }

        if (action == 0) {
            break;
        }

        std::cin.ignore();
        std::string text;
        std::cout << "Enter message text:";
        std::getline(std::cin, text);

        WaitForSingleObject(writeSemaphore, INFINITE);
        WaitForSingleObject(mutex, INFINITE);

        message mes(text);
        file.open(argv[1], std::ios::binary | std::ios::app);
        file << mes;
        file.close();

        ReleaseMutex(mutex);
        ReleaseSemaphore(readSemaphore, 1, NULL);

        std::cout << "Written successfully" << "\n";
    }

    CloseHandle(mutex);
    CloseHandle(readyEvent);
    CloseHandle(writeSemaphore);
    CloseHandle(readSemaphore);

    return 0;
}
