#include <iostream>
#include <vector>
#include <thread>
#include <chrono>

void Average(const std::vector<double>& arr, double& aver)
{
    long long sum = 0;
    for (int i = 0; i < arr.size(); i++)
    {
        sum += arr[i];
        std::this_thread::sleep_for(std::chrono::milliseconds(12));
    }
    double doubleSum = static_cast<double>(sum);
    aver = doubleSum / arr.size();
    std::cout << "Average of this array is: " << aver << std::endl;
}

void Min_Max(const std::vector<double>& arr, double& max, double& min)
{
    for (int i = 0; i < arr.size(); i++)
    {
        if (arr[i] > max)
        {
            max = arr[i];
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(7));
        if (arr[i] < min)
        {
            min = arr[i];
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(7));
    }
    std::cout << "Max element = " << max << std::endl;
    std::cout << "Min element = " << min << std::endl;
}

int main()
{
    long long n;
    double max = INT_MIN;
    double min = INT_MAX;
    double aver = 0;
    std::cout << "Enter number of elements: ";
    std::cin >> n;
    if (n <= 0) {
        std::cout << "Incorrect input data" << std::endl;
        return -1;
    }
    std::vector<double> arr(n);
    std::cout << "Enter the array elements: ";
    for (int i = 0; i < n; i++)
    {
        std::cin >> arr[i];
    }

    std::thread min_max(Min_Max, std::cref(arr), std::ref(max), std::ref(min));
    std::thread average(Average, std::cref(arr), std::ref(aver));
    min_max.join();
    average.join();
    for (long long i = 0; i < n; i++)
    {
        if (arr[i] == max || arr[i] == min)
        {
            arr[i] = aver;
        }
    }
    std::cout << "Your array after change: ";
    for (long long i = 0; i < n; i++)
    {
        std::cout << arr[i] << " ";
    }

    return 0;
}
