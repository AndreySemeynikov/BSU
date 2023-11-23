#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <mutex>
#include <filesystem>

double processFile(const std::string& filePath) {
    std::ifstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Error opening file: " << filePath << std::endl;
        return 0;
    }

    int action;
    file >> action;

    double num1, num2;
    file >> num1 >> num2;

    double result = 0;

    switch (action) {
    case 1:
        result = num1 + num2;
        break;
    case 2:
        result = num1 * num2;
        break;
    case 3:
        result = num1 * num1 + num2 * num2;
        break;
    default:
        std::cerr << "Invalid action code in file: " << filePath << std::endl;
    }

    file.close();
    return result;
}

void worker(const std::vector<std::string>& files, int threadId, std::vector<double>& results, std::mutex& mutex) {
    double partialSum = 0;

    for (const auto& file : files) {
        partialSum += processFile(file);
    }

    std::lock_guard<std::mutex> lock(mutex);
    results[threadId] = partialSum;
}

int main() {
    std::cout << "Enter the absolute path of the directory: ";
    std::string directory;
    std::getline(std::cin, directory);

    if (!std::filesystem::is_directory(directory)) {
        std::cerr << "Error: The specified directory does not exist." << std::endl;
        return 1;
    }

    int numThreads;
    std::cout << "Enter the number of threads: ";
    std::cin >> numThreads;

    std::vector<std::string> files;
    for (const auto& entry : std::filesystem::directory_iterator(directory)) {
        if (entry.is_regular_file() && entry.path().filename().string().find("in_") == 0) {
            files.push_back(entry.path().string());
        }
    }

    if (files.empty()) {
        std::cerr << "Error: No matching files found in the specified directory." << std::endl;
        return 1;
    }

    int filesPerThread = files.size() / numThreads;
    int extraFiles = files.size() % numThreads;

    std::vector<std::thread> threads;
    std::vector<double> results(numThreads, 0);
    std::mutex mutex;

    for (int i = 0; i < numThreads; ++i) {
        int startIdx = i * filesPerThread;
        int endIdx = (i + 1) * filesPerThread + (i == numThreads - 1 ? extraFiles : 0);

        std::vector<std::string> threadFiles(files.begin() + startIdx, files.begin() + endIdx);
        threads.emplace_back(worker, threadFiles, i, std::ref(results), std::ref(mutex));
    }

    for (auto& thread : threads) {
        thread.join();
    }

    double totalResult = 0;
    for (double result : results) {
        totalResult += result;
    }

    std::ofstream outputFile(directory + "/out.dat");
    if (outputFile.is_open()) {
        outputFile << totalResult << std::endl;
        outputFile.close();
        std::cout << "Result written to out.dat" << std::endl;
    }
    else {
        std::cerr << "Error opening output file." << std::endl;
        return 1;
    }

    return 0;
}
