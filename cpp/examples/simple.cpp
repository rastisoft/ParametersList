/*
BSD 2-Clause License

Copyright (c) 2017, Davood Rasti and Alireza Rasti - rastisoft
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/*
this example illustrates how the ParametersList can be used to retrive and used
parameters value.
*/

#include <iostream>
#include "../ParametersList/include/ParametersList.h"
#include "../ParametersList/include/Utility.h"

using namespace RS;
using namespace RS::Resources;

int main(int argc, char* argv[])
{
    try
    {
        //Creates a ParametersList and load parameters from config01.plf file.
        ParametersList pl("../../../data/config01.plf");

        //Retrives title parameter which is a string.
        std::cout << "\nApp title : " << pl.get<std::string>("title") << '\n';
        
        //Gets the screen size.
        const int width = pl.get<int>("Screen.Size.width");
        const int height = pl.get<int>("Screen.Size.height");

        //Check the screen size.
        if(width == 1280 && height == 720)
            std::cout << "Supported resolution!";
        else
            std::cout << "This resolution is not supported. ";
        
        std::cout << " width : " << width << ", height : " << height << '\n';
        
        std::cout << "Full screen mode : ";
        std::cout << ((pl.get<bool>("Screen.isFullScreen")) ? "On" : "Off") << '\n';
            
        //You can add new parameters or update 
        //existing one using set() function.
        pl.set("v-sync", true); //v-sync is not in the list so it will be added.

        //Screen.Size.width is already in the list so
        //it's value will be updated.
        pl.set("Screen.Size.width", 1920);

        //You can also load another .plf file. in this case, 
        //value of the existing parameters in the list are 
        //updated and new ones are added.
        pl.loadFromFile("../../../data/config02.plf");

        //Here are parameters value after loading new file.
        std::cout << "\nApp title now is : " << pl.get<std::string>("title") << '\n';
        std::cout << "background image : " << pl.get<std::string>("backgroundImage") << '\n';

    }
    catch(const ParametersListException& exception)
    {
        std::cout << exception.what() << '\n';
    }
}