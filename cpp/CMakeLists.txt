cmake_minimum_required (VERSION 3.0)

project(parameterslist)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17 -O3")
set(CMAKE_CXX_COMPILER g++)

include_directories(${CMAKE_SOURCE_DIR}/include)
file(GLOB_RECURSE SRC_FILES ${CMAKE_SOURCE_DIR}/src/RS/*.cpp)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/examples/bin)
add_library(parameterslist STATIC ${SRC_FILES})

add_executable(simple ${CMAKE_SOURCE_DIR}/examples/simple.cpp)
target_link_libraries(simple parameterslist)
