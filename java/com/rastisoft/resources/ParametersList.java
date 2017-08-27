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

package com.rastisoft.resources;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

public class ParametersList {

	//Keeps the parameters and their values
	private final HashMap<String, String> parameters;

	/**
		@description: ParametersList class default constructor.
	*/
    public ParametersList(){
        parameters = new HashMap<String, String>();
    }

	/**
		@description: ParametersList class constructor.
		@param plfFile: plf file that the parameters should be extracted from.
	*/
    public ParametersList(final String plfFile) throws Exception{
	   this();
	   loadFromFile(plfFile);
    }

	/**
		@description: Extracts the parameters from a plf file.
		@param plfFile: PLF file that the parameters should be extracted from.
		@return: void.
	*/
    public void loadFromFile(final String plfFile) throws Exception {
		FileInputStream inFile = null;

		try{
			
			inFile = new FileInputStream(plfFile);

			String key = "";
			String value = "";    
			boolean isKeyValueSeparatorFound = false;
			boolean commentCharacterFound = false;
			final Stack<String> keysList = new Stack<String>();  
			char lastCharacter = 0;			

			int characterCode;
			while ((characterCode = inFile.read()) != -1) {
				
				final char character = (char)characterCode;
				
				if(commentCharacterFound)
				{
					commentCharacterFound = !(character == '\n' || character == '\r');
					continue;
				}
	
				if(character == '\n' || character == '\r' || character == '\t')
					continue;

				if(character == '/')
				{
					commentCharacterFound = (lastCharacter == '/');
					lastCharacter = '/';
					continue;
				}
				else if(isKeyValueSeparatorFound)
				{
					if(character == ';')
					{
						key = key.trim();
						value = value.trim();
						if(!key.isEmpty() && !value.isEmpty())
						{
							String parentKey = "";
							for(String keyItem : keysList)
								parentKey += keyItem + '.';
								
							parameters.put(parentKey + key, value);
							key = "";
							value = "";
							isKeyValueSeparatorFound = false;                        
						}
					}
					else if(character == '{')
					{
						keysList.push(key.trim());
						key = "";
						isKeyValueSeparatorFound = false;
					}
					else if(character == '}')
						keysList.pop();
					else
						value += character;
				}
				else
				{
					if(character == '=')
						isKeyValueSeparatorFound = true;
					else if(character == '}')
						keysList.pop();
					else
						key += character;
				}
				
				lastCharacter = character;
			}
		}
		finally	{
			if(inFile != null)
				inFile.close();
		}
	}

	/**
		@description: Adds new parameter/Updates existing ones.
		@param parameter: the parameter name.
		@param value: value of the parameter.
		@return: void.
	*/
	public void set(final String parameter, Object value) throws Exception {
		if(value != null)
			parameters.put(parameter, String.valueOf(value));
		else
			throw new Exception("set() : Invalid value.");
	}

	/**
		@description: Returns the value of a parameter in string.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public String getAsString(final String parameter) throws Exception {
		
		final String value = parameters.get(parameter);

		if(value != null)
			return value;
		
		throw new Exception("Parameter '" + parameter + "' does not exist.");
	}

	/**
		@description: Returns the value of a parameter in int.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public int getAsInt(final String parameter) throws Exception {
		try{
			return Integer.parseInt(getAsString(parameter));
		}
		catch(Exception exception){
			throw new Exception("getAsInt() : Invalid value.");
		}
	}

	/**
		@description: Returns the value of a parameter in float.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public float getAsFloat(final String parameter) throws Exception {
		try{
			return Float.parseFloat(getAsString(parameter));
		}
		catch(Exception exception){
			throw new Exception("getAsFloat() : Invalid value.");
		}
	}

	/**
		@description: Returns the value of a parameter in double.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public double getAsDouble(final String parameter) throws Exception {
		try{
			return Double.parseDouble(getAsString(parameter));
		}
		catch(Exception exception){
			throw new Exception("getAsDouble() : Invalid value.");
		}
	}

	/**
		@description: Returns the value of a parameter in long.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public long getAsLong(final String parameter) throws Exception {
		try{
			return Long.parseLong(getAsString(parameter));
		}
		catch(Exception exception){
			throw new Exception("getAsLong() : Invalid value.");
		}
	}

	/**
		@description: Returns the value of a parameter in boolean.
		@param parameter: the parameter name.
		@return: value of a parameter.
	*/
	public boolean getAsBoolean(final String parameter) throws Exception {
		final String value = getAsString(parameter);
		
		if(value.equals("true") || value.equals("false"))
			return (value.equals("true"));
		
		throw new Exception("getAsBoolean() : Invalid value.");
	}

	/**
		@description: Returns the number of parameters in the list.
		@return: the number of parameters in the list.
	*/
    public int getSize() {
        return parameters.size();
	}
	
	/**
		@description: Clears all parameters.
		@return: void.
	*/
	public void clear(){
		parameters.clear();
	}
}