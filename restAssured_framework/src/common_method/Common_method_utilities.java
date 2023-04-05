package common_method;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Common_method_utilities { 
	
	public static void evidenceFileCreator(String filename, String request, String response) throws IOException
	{
	File newTextFile = new File ("E:\\restAssured_framework\\"+ filename + ".txt");
	if(newTextFile.createNewFile())
	{
		FileWriter dataWriter = new FileWriter(newTextFile);
		dataWriter.write("requestbody is : \n" +request+ "\n\n");
		dataWriter.write("responsebody is : \n" +response);
		dataWriter.close();
		System.out.println("requestBody and responseBody data saved in :" +newTextFile.getName());;
	}
	else
	{
		System.out.println(newTextFile.getName() + "Already exist take a backup of it ! ");
	}
	}



}
