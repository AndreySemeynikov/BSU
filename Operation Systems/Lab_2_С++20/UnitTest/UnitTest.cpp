#include "pch.h"
#include "CppUnitTest.h"
#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
#include <limits.h>
#include "../Lab_2/Lab_2.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace Unittests
{
    TEST_CLASS(AverageTest)
    {
    public:
        TEST_METHOD(AverageCalculatedCorrectly)
        {
            std::vector<double> arr = { 1, 2, 3, 4, 5 };
            double expectedAverage = 3;

            double average;
            Average(arr, average);

            // Assert
            Assert::AreEqual(expectedAverage, average);
        }
    };

    TEST_CLASS(ChangeValuesTest)
    {
    public:
        TEST_METHOD(ChangeValuesWhenMaxAndMinFound)
        {
            std::vector<double> arr = { 5, 4, 3, 2, 1 };
            double expectedAverage = 3;

            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 1)
                {
                    arr[i] = aver;
                }
            }
            Assert::AreEqual(expectedAverage, arr[0]);
            Assert::AreEqual(expectedAverage, arr[4]);
        }

        TEST_METHOD(ChangeValuesWhenMaxAndMinNotFound)
        {
            std::vector<double> arr = { 5, 4, 3, 4, 2 };
            double expectedAverage = 3.8;

            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 2)
                {
                    arr[i] = aver;
                }
            }

            Assert::AreEqual(aver, arr[0]);
            Assert::AreEqual(aver, arr[4]);
        }

        TEST_METHOD(AverageReturnsCorrectValueForNegativeArray)
        {
            std::vector<double> arr = { -1, -2, -3, -4, -5 };
            double expectedAverage = -3;

            double average;
            Average(arr, average);

            Assert::AreEqual(expectedAverage, average);
        }

       
        TEST_METHOD(ChangeValuesDoesNotChangeArrayIfOneMaxOrMin)
        {
            std::vector<double> arr = { 5, 5, 5 };

            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 3)
                {
                    arr[i] = aver;
                }
            }

            for (long long i = 0; i < arr.size(); i++)
            {
                Assert::AreEqual(5.0, arr[i]);
            }
        }
    };

    TEST_CLASS(MinMaxTest)
    {
    public:
        TEST_METHOD(IdentifyMaxMinCorrectly)
        {
            std::vector<double> arr = { 1, 2, 3, 5, 4 };
            double expectedMax = 5;
            double expectedMin = 1;

            double max, min;
            std::thread min_max(Min_Max, std::cref(arr), std::ref(max), std::ref(min));
            min_max.join();
            Assert::AreEqual(expectedMax, max);

        }

    };
}