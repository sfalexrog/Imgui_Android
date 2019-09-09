# Cmake file for a sample cross-platform project with dear imgui.
# The aim here is to have most of the stuff (including cmake files)
# be the same throughout platforms.

cmake_minimum_required(VERSION 3.1)

project(imgui_demo)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_MODULE_PATH ${PROJECT_SOURCE_DIR}/cmake)

find_package(SDL2 REQUIRED)

# This is an adaptation of the ImGui demo, with some geometry (a teapot)
# rendered in order to check the ImGui implementation. Teapot is taken
# from Google NDK samples.

set(IMGUI_PATH deps/imgui)
set(IMGUI_IMPL_PATH src/imgui_impl)

# Since we're drawing something that's not just a triangle, we're gonna
# want a library that deals with vectors and matrices. glm is the obvious
# choice.

add_subdirectory(deps/glm)

# Use GLES2/GLES3 implementations for Android, GL3 for everything else

if (ANDROID)
    set(GL_PROFILES "GL_PROFILE_GLES2" "GL_PROFILE_GLES3")
else()
    set(GL_PROFILES "GL_PROFILE_GL3")
endif()

file(GLOB IMGUI_FILES
    ${IMGUI_PATH}/*.cpp
    ${IMGUI_IMPL_PATH}/*.cpp
)

file(GLOB DEMO_FILES
    src/*.cpp
)

if (NOT ANDROID)
    set(GLLOAD_PATH src/glload)
    file(GLOB GLLOAD_FILES
            ${GLLOAD_PATH}/*.c)
endif()


set(DEMO_SOURCES ${IMGUI_FILES} ${DEMO_FILES} ${GLLOAD_FILES})

# Build as a library for Android, as an executable otherwise

if (ANDROID)
    add_library(demo SHARED
        ${DEMO_SOURCES}
    )
else()
    add_executable(demo
        ${DEMO_SOURCES}
    )
endif()

target_link_libraries(demo ${SDL2_LIBRARY} glm GLESv2)
target_include_directories(demo PRIVATE ${SDL2_INCLUDE_DIR})
target_include_directories(demo PRIVATE ${IMGUI_PATH})
target_include_directories(demo PRIVATE ${IMGUI_IMPL_PATH})
target_include_directories(demo PRIVATE ${GLLOAD_PATH})
target_compile_definitions(demo PRIVATE ${GL_PROFILES})