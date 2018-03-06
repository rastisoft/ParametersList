# **ParametersList**
|        |          |
|---------|:---------:|
|**Version:**  | 0.5.0 |
|**Language:** | C++, Java |
|**License:**  | BSD 2-Clause |
|**Status:**   | under development |

## **What is ParametersList?**
Sometimes for our personal software projects, we prefer to keep the parameters and their value that are needed to configure an application,  in a text file. It allows us, without recompiling the source code, control behavior of the application by changing these parameters. Previously, we used XML and json to store and deal with these parameters. Later, we decided to create our own simpler tool, named ParametersList, which provides us minimum functionalities that we need. 

Currently, the ParametersList is in its earlier stage and evolving gradually so its not completely error free. So far we have implemented the first version in C++17 and Java. The C# version will be added later.

## **Rules**
The parameters and their values are stored in a C like syntax into a file with extension .plf like:

```c
// This is a simple ParametersList file.

title = simple app;
Screen =
{
    Size = 
    {
        width = 1280;
        height = 720;
    }

    isFullScreen = false;
}

Color = {red = 0.5; green = 1.0; blue = 0.0;}
```

To make parsing of ParametersList file easier and faster, some few rules should be followed.

 * Parameter and its vale are separated by '=' .
 * Semicolon ';' should be used at the end of a statement.
 * '//' can be used for single line comment.
 * '{' and '}' are used to create a group of related parameters.
### Some notes:
* Current version does not support unicode characters yet.
* Syntax checker is not completed yet so breaking of syntax rules may cause fatal error.

## **How to use**
During the runtime, the ParametersList can be used to extract the parameters and their values from a plf file:

### C++:
```c++
ParametersList pl("config01.plf");

const std::string title = pl.get<std::string>("title");
const int width = pl.get<int>("Screen.Size.width");
```

### Java:
```java
ParametersList pl = new ParametersList("config01.plf");
final int width = pl.getAsInt("Screen.Size.width");
```
We can add new parameters or updated existing ones using set() method: 

### C++, Java:
```C++
pl.set("v-sync", true); //v-sync is not in the list so it will be added.
pl.set("Screen.isFullScreen", true); //Updates existing parameter.
```
You can also load another .plf file. In this case, value of the existing parameters in the list are updated and new ones are added.

### C++, Java:
```C++
pl.loadFromFile("config02.plf");
```

## Examples:
C++: cpp/examples/simple.cpp

Java: java/Simple.java

## Build C++ version:
### Requirements:
To build C++ version you need:

* C++ 17 compiler.
* CMake 3.0 or above.

We have used *GCC-7.1.0* compiler on *Ubuntu 16.04* to develop and test ParametersList.

### Build:
In order to build C++ version, run following commands:
```
cd cpp
mkdir build
cd build
cmake ..
make
```
The executable file will be built in the *cpp/examples/bin* folder.

## Build Java version:
### Requirements:
 * Java JDK 7 or above.

 ### Build and Run:

 ```
 cd java
 javac Simple.java
 java Simple
 ```