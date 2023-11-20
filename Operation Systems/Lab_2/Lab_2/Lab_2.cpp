#include <iostream>
#include <chrono>
#include <thread>
#include <vector>


void Average(double arr[], long long n, double& aver)
{
	long long sum = 0;
	for (int i = 0; i < n; i++)
	{
		sum += arr[i];
		std::this_thread::sleep_for(std::chrono::milliseconds(12));
	}
	double doubleSum = static_cast<double>(sum);
	aver = doubleSum / n;
	std::cout << "Average of this array is: " << aver << std::endl;
}

void Min_Max(double arr[], long long n, double& max, double& min)
{
	for (int i = 0; i < n; i++)
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
	double* arr = new double[n];
	std::cout << "Enter the array elements: ";
	for (int i = 0; i < n; i++)
	{
		std::cin >> arr[i];
	}

	std::thread min_max(Min_Max, arr, n, std::ref(max), std::ref(min));
	std::thread average(Average, arr, n, std::ref(aver));
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

}