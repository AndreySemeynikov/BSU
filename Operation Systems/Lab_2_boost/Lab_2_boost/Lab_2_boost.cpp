#include <iostream>
#include <chrono>
#include <vector>
#include <boost/thread.hpp>

void Average(const std::vector<double>& arr, double& aver)
{
    double sum = 0;
    for (const auto& element : arr)
    {
        sum += element;
        boost::this_thread::sleep_for(boost::chrono::milliseconds(12));
    }
    aver = sum / arr.size();
    std::cout << "Average of this array is: " << aver << std::endl;
}

void Min_Max(const std::vector<double>& arr, double& max, double& min)
{
    for (const auto& element : arr)
    {
        if (element > max)
        {
            max = element;
        }
        boost::this_thread::sleep_for(boost::chrono::milliseconds(7));
        if (element < min)
        {
            min = element;
        }
        boost::this_thread::sleep_for(boost::chrono::milliseconds(7));
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
    for (auto& element : arr)
    {
        std::cin >> element;
    }

    boost::thread min_max(Min_Max, std::cref(arr), std::ref(max), std::ref(min));
    boost::thread average(Average, std::cref(arr), std::ref(aver));
    min_max.join();
    average.join();

    for (auto& element : arr)
    {
        if (element == max || element == min)
        {
            element = aver;
        }
    }

    std::cout << "Your array after change: ";
    for (const auto& element : arr)
    {
        std::cout << element << " ";
    }

    return 0;
}
