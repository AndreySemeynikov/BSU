#include <iostream>
#include "windows.h"

int* sharedArray;
int arraySize;
HANDLE* stopEvents;
HANDLE* continueEvents;
int numThreads;
bool* threadWorking;
CRITICAL_SECTION criticalSection;

void resetEvents(HANDLE* events) {
    for (int i = 0; i < numThreads; i++) {
        ResetEvent(events[i]);
    }
}

void displayArray() {
    for (int i = 0; i < arraySize; i++) {
        std::cout << "Element " << i + 1 << ": " << sharedArray[i] << "\n";
    }
}

DWORD WINAPI markerThread(LPVOID param) {
    auto threadNumber = (int)param;
    int markedElements = 0;
    srand(threadNumber);
    bool* visitedElements = new bool[arraySize] {};

    while (true) {
        markedElements = 0;

        while (true) {
            int randomIndex = rand() % arraySize;

            if (sharedArray[randomIndex] != 0) {
                EnterCriticalSection(&criticalSection);
                std::cout << "Thread " << threadNumber + 1 << " is stopping. Marked elements: " << markedElements << ". It breaks on element " << randomIndex + 1 << "\n";
                SetEvent(stopEvents[threadNumber]);
                LeaveCriticalSection(&criticalSection);
                break;
            }

            EnterCriticalSection(&criticalSection);
            if (sharedArray[randomIndex] == 0) {
                Sleep(5);
                sharedArray[randomIndex] = threadNumber + 1;
                markedElements++;
                visitedElements[randomIndex] = true;
                Sleep(5);
            }
            LeaveCriticalSection(&criticalSection);
        }

        WaitForSingleObject(continueEvents[threadNumber], INFINITE);
        ResetEvent(continueEvents[threadNumber]);

        if (!threadWorking[threadNumber]) {
            EnterCriticalSection(&criticalSection);
            std::cout << "Thread " << threadNumber + 1 << " is closing\n";

            for (int i = 0; i < arraySize; i++) {
                if (visitedElements[i]) {
                    sharedArray[i] = 0;
                }
            }

            SetEvent(stopEvents[threadNumber]);
            LeaveCriticalSection(&criticalSection);
            return 0;
        }
    }
}

int main() {
    std::cout << "Enter the number of elements in the array: ";
    std::cin >> arraySize;

    sharedArray = new int[arraySize]();
    std::cout << "Enter the number of threads: ";
    std::cin >> numThreads;

    HANDLE* threads = new HANDLE[numThreads];
    stopEvents = new HANDLE[numThreads];
    continueEvents = new HANDLE[numThreads];
    threadWorking = new bool[numThreads];

    InitializeCriticalSection(&criticalSection);

    for (int i = 0; i < numThreads; i++) {
        stopEvents[i] = CreateEvent(NULL, TRUE, FALSE, NULL);
        continueEvents[i] = CreateEvent(NULL, TRUE, FALSE, NULL);
        threads[i] = CreateThread(NULL, 0, markerThread, (void*)i, 0, NULL);
        threadWorking[i] = true;
    }

    for (int i = 0; i < numThreads; i++) {
        for (int j = 0; j < numThreads; j++) {
            if (threadWorking[j]) {
                ResetEvent(stopEvents[j]);
            }
        }

        WaitForMultipleObjects(numThreads, stopEvents, TRUE, INFINITE);

        int threadToStop;
        std::cout << "Array:\n";
        displayArray();

        while (true) {
            std::cout << "Enter the thread number to stop (1-" << numThreads << "). Already stopped threads: ";
            for (int j = 0; j < numThreads; j++) {
                if (!threadWorking[j]) {
                    std::cout << j + 1 << " ";
                }
            }
            std::cout << "\n";
            std::cin >> threadToStop;
            threadToStop--;

            if (threadToStop >= 0 && threadToStop < numThreads && threadWorking[threadToStop]) {
                threadWorking[threadToStop] = false;
                ResetEvent(stopEvents[threadToStop]);
                SetEvent(continueEvents[threadToStop]);
                WaitForSingleObject(stopEvents[threadToStop], INFINITE);
                break;
            }
            else {
                std::cout << "Incorrect number, try again!\n";
            }
        }

        for (int j = 0; j < numThreads; j++) {
            if (threadWorking[j]) {
                SetEvent(continueEvents[j]);
            }
        }
    }

    // Clean up
    DeleteCriticalSection(&criticalSection);
    delete[] sharedArray;
    delete[] threads;
    delete[] stopEvents;
    delete[] continueEvents;
    delete[] threadWorking;

    return 0;
}
