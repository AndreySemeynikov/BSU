# CMakeList.txt : CMake project for Lab_2_CMake, include source and define
# project specific logic here.
#

# Add source to this project's executable.
add_executable (Lab_2_CMake "Lab_2_CMake.cpp" "Lab_2_CMake.h")

if (CMAKE_VERSION VERSION_GREATER 3.12)
  set_property(TARGET Lab_2_CMake PROPERTY CXX_STANDARD 20)
endif()

# TODO: Add tests and install targets if needed.
set(CMAKE_CXX_STANDARD 11)

find_package(Threads REQUIRED)
target_link_libraries(Lab_2_CMake PRIVATE Threads::Threads)