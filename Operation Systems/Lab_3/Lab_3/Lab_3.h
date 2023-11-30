#pragma once
#include <iostream>
#include <Windows.h>

struct thread_info {
    int array_size;
    int* arr;
    int thread_index;
    HANDLE stop_work;  // Event
    HANDLE start_work; // Event
    HANDLE* terminate_or_continue; // Events [0] - terminate, [1] - continue
};

CRITICAL_SECTION cs;

void set_zeros(int* arr, int size, int thread_index);
bool need_to_terminate(HANDLE* terminate_or_continue);
void continue_thread(HANDLE* terminate_or_continue);
DWORD WINAPI thread_func(LPVOID params);
void print_array(int* arr, int size);
bool all_threads_terminated(bool* terminated_threads, int size);

