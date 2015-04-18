package cvParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;

public class cvParser
{

	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: cvParser pathNameOfDir");
			return;
		}

		File directory = new File( args[0]);
		List<File> tempList = Arrays.asList(directory.listFiles());
		for (File file : tempList)
		{
			if (!file.isFile())
			{
				continue;
			}
			String fullPathName = file.getPath();
			String fileName = file.getName();
			String fileNameNoExt = getFileNameNoEx(fileName);
			
			if (isWordFile(fileName))
			{
				try
				{
					POITextExtractor extractor = ExtractorFactory
					        .createExtractor(new File(fullPathName));
					CV cv = new CV(fileNameNoExt, extractor.getText());
					System.out.println(cv.toString());
				} catch (Exception e)
				{
					System.out.println("Error in: " + fullPathName);
					e.printStackTrace();
				} 
			} else if (isPdfFile(fileName))
			{
				System.out.println("PDF To be supported in a while");
			} else
			{
				System.out.println("Do not support that format!");
			}
		}
		
	}

	/**
	 * check if the file is PDF
	 * 
	 * @param name
	 * @return
	 */
	private static boolean isPdfFile(String name)
	{
		String ext = getExtensionName(name);
		if (StringUtils.isNotBlank(ext))
		{
			return ext.equalsIgnoreCase("pdf");
		}
		return false;
	}

	/**
	 * check if the file is doc or docx
	 * 
	 * @param name
	 * @return
	 */
	private static boolean isWordFile(String name)
	{
		String ext = getExtensionName(name);
		if (StringUtils.isNotBlank(ext))
		{
			return ext.equalsIgnoreCase("doc")
			        || ext.equalsIgnoreCase("docx");
		}
		return false;
	}

	/**
	 * Get the ext from a filename
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename)
	{
		if ((filename != null) && (filename.length() > 0))
		{
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1)))
			{
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * get rid of ext from the filename
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename)
	{
		if ((filename != null) && (filename.length() > 0))
		{
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length())))
			{
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

}
