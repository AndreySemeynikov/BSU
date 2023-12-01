#include "pch.h"
#include "CppUnitTest.h"
#include "CppUnitTest.h"
#include <Windows.h>
#include "../Lab_3/Lab_3.cpp"


using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace YourTestsNamespace
{
    TEST_CLASS(YourTestsClass)
    {
    public:

        TEST_METHOD(TestSetZeros)
        {
            int arr[] = { 1, 2, 3, 4, 5 };
            int size = sizeof(arr) / sizeof(arr[0]);
            int thread_index = 3;

            set_zeros(arr, size, thread_index);

            for (int i = 0; i < size; ++i)
            {
                if (i == thread_index - 1)
                    Assert::AreEqual(0, arr[i]);
                else
                    Assert::AreNotEqual(0, arr[i]);
            }
        }

        TEST_METHOD(TestNeedToTerminate)
        {
            HANDLE terminate_or_continue[2];
            terminate_or_continue[0] = CreateEvent(NULL, FALSE, FALSE, NULL);
            terminate_or_continue[1] = CreateEvent(NULL, FALSE, FALSE, NULL);

            Assert::IsFalse(need_to_terminate(terminate_or_continue));

            SetEvent(terminate_or_continue[0]);
            Assert::IsTrue(need_to_terminate(terminate_or_continue));

            CloseHandle(terminate_or_continue[0]);
            CloseHandle(terminate_or_continue[1]);
        }

        TEST_METHOD(TestPrintArray)
        {
            int arr[] = { 1, 2, 3, 4, 5 };
            int size = sizeof(arr) / sizeof(arr[0]);

            std::streambuf* oldCoutStreamBuf = std::cout.rdbuf();
            std::stringstream newCoutBuffer;
            std::cout.rdbuf(newCoutBuffer.rdbuf());

            print_array(arr, size);

            std::cout.rdbuf(oldCoutStreamBuf);


            std::string expectedOutput = "Array: 1 2 3 4 5 \n";
            Assert::AreEqual(expectedOutput, newCoutBuffer.str());
        }

        TEST_METHOD(TestAllThreadsTerminated)
        {
            int number_of_threads = 3;
            bool terminated_threads[] = { true, true, true };

            Assert::IsTrue(all_threads_terminated(terminated_threads, number_of_threads));

            terminated_threads[1] = false;

            Assert::IsFalse(all_threads_terminated(terminated_threads, number_of_threads));
        }
    };
}
