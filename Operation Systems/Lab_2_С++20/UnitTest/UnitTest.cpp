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
            // Arrange
            std::vector<double> arr = { 1, 2, 3, 4, 5 };
            double expectedAverage = 3;

            // Act
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
            // Arrange
            std::vector<double> arr = { 5, 4, 3, 2, 1 };
            double expectedAverage = 3;

            // Act
            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 1)
                {
                    arr[i] = aver;
                }
            }

            // Check if values of max and min are changed to average
            Assert::AreEqual(expectedAverage, arr[0]);
            Assert::AreEqual(expectedAverage, arr[4]);
        }

        TEST_METHOD(ChangeValuesWhenMaxAndMinNotFound)
        {
            // Arrange
            std::vector<double> arr = { 5, 4, 3, 4, 2 };
            double expectedAverage = 3.8;

            // Act
            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 2)
                {
                    arr[i] = aver;
                }
            }

            // Check if values of max and min are not changed since not found
            Assert::AreEqual(aver, arr[0]);
            Assert::AreEqual(aver, arr[4]);
        }

        TEST_METHOD(AverageReturnsCorrectValueForNegativeArray)
        {
            // Arrange
            std::vector<double> arr = { -1, -2, -3, -4, -5 };
            double expectedAverage = -3;

            // Act
            double average;
            Average(arr, average);

            // Assert
            Assert::AreEqual(expectedAverage, average);
        }

       
        TEST_METHOD(ChangeValuesDoesNotChangeArrayIfOneMaxOrMin)
        {
            // Arrange
            std::vector<double> arr = { 5, 5, 5 };

            // Act
            double aver;
            Average(arr, aver);

            for (long long i = 0; i < arr.size(); i++)
            {
                if (arr[i] == 5 || arr[i] == 3)
                {
                    arr[i] = aver;
                }
            }

            // Assert
            for (long long i = 0; i < arr.size(); i++)
            {
                Assert::AreEqual(5.0, arr[i]);
            }
        }
    };
}