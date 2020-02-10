// Патч изменяет версию и номер сборки приложения
// © Maximoff, 2020

package ru.maximoff.patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoUpdates
{
	private String vName;
	private String vCode;
	private String vCodeHex;
	private String newName;
	private String newCode;
	private String newCodeHex;
	private FileFilter dirFilter;
	private FilenameFilter smaliFilter;
	private FilenameFilter xmlFilter;
	private FilenameFilter otherFilter;

    public NoUpdates()
	{}

    public void patch(String apkPath, String patchPath, String decodePath, String param)
	{
        File rootDir = new File(decodePath);
		newName = "999999999";
		newCode = "999999999";
		if (param != null)
		{
			String[] params = param.split(",");
			if (params.length == 2)
			{
				newName = params[0].trim();
				newCode = params[1].trim();
			}
		}
		if (parseParam(rootDir))
		{
			vCodeHex = "0x" + Integer.toHexString(Integer.parseInt(vCode));
			newCodeHex = "0x" + Integer.toHexString(Integer.parseInt(newCode));
			dirFilter = new FileFilter()
			{
				@Override
				public boolean accept(File p1)
				{
					return p1.isDirectory();
				}
			};
			smaliFilter = new FilenameFilter()
			{
				@Override
				public boolean accept(File p1, String p2)
				{
					return p2.toLowerCase().endsWith(".smali");
				}
			};
			xmlFilter = new FilenameFilter()
			{
				@Override
				public boolean accept(File p1, String p2)
				{
					return p2.toLowerCase().endsWith(".xml");
				}
			};
			otherFilter = new FilenameFilter()
			{
				@Override
				public boolean accept(File p1, String p2)
				{
					return (p2.toLowerCase().endsWith(".js") || p2.toLowerCase().endsWith(".txt") || !p2.contains("."));
				}
			};
			startReplacing(rootDir);
		}
		else
		{
			throw new NullPointerException("Error \"AndroidManifest.xml\" parse, exit!");
		}
    }

	private void startReplacing(File rootDir)
	{
		replacer(rootDir, otherFilter);
		File[] subDirs = rootDir.listFiles(dirFilter);
		if (subDirs != null)
		{
			for (File subDir : subDirs)
			{
				if (subDir.getName().startsWith("smali"))
				{
					replacer(subDir, smaliFilter);
				}
				else if (subDir.getName().equals("res"))
				{
					replacer(subDir, xmlFilter);
				}
			}
		}
	}

	private void replacer(File dir, FilenameFilter filter)
	{
		File[] files = dir.listFiles(filter);
		File[] subDirs = dir.listFiles(dirFilter);
		if (files != null)
		{
			for (File file : files)
			{
				String content = readToString(file, false);
				if (content == null)
					continue;
				content = content.replace("\"" + vName + "\"", "\"" + newName + "\"").replace("\"" + vCode + "\"", "\"" + newCode + "\"").replaceAll(vName + "((\\s+)?(\\w+|\\()(\\s+)?)" + vCode, newName + "$1" + newCode);
				if (filter == xmlFilter)
				{
					content = content.replace(">" + vName + "<", ">" + newName + "<").replace(">" + vCode + "<", ">" + newCode + "<");
				}
				if (filter == smaliFilter)
				{
					content = content.replaceAll("((const|const/16) ((v|p)\\d+), " + vCodeHex + ")\\s", "$1 #Maximoff. Возможный номер сборки! Заменить на: const $3, " + newCodeHex + "\n");
				}
				writeToFile(file, content);
			}
		}
		if (subDirs != null)
		{
			for (File subDir : subDirs)
			{
				replacer(subDir, filter);
			}
		}
	}

	private boolean parseParam(File rootDir)
	{
		File manifest = new File(rootDir, "AndroidManifest.xml");
		if (!manifest.exists())
			return false;
		String content = readToString(manifest, true);
		if (content == null)
			return false;
		Pattern pName = Pattern.compile("\\sandroid:versionName=\"([^\"]+)\"");
		Pattern pCode = Pattern.compile("\\sandroid:versionCode=\"([^\"]+)\"");
		Matcher mName = pName.matcher(content);
		if (mName.find())
		{
			String parseName = mName.group(1);
			if (parseName.startsWith("@string/"))
			{
				parseFromStrings(rootDir, parseName.split("/")[1]);
			}
			else
			{
				vName = mName.group(1);
				content = mName.replaceAll(" android:versionName=\"" + newName + "\"");
			}
		}
		Matcher mCode = pCode.matcher(content);
		if (mCode.find())
		{
			vCode = mCode.group(1);
			content = mCode.replaceAll(" android:versionCode=\"" + newCode + "\"");
		}
		if (vName != null && vCode != null)
		{
			writeToFile(manifest, content);
			return true;
		}
		else
		{
			return false;
		}
	}

	private void parseFromStrings(File rootDir, String name)
	{
		File strings = new File(rootDir.getAbsolutePath() + "/res/values/strings.xml");
		if (strings.exists())
		{
			String content = readToString(strings, true);
			if (content == null)
				return;
			Pattern pName = Pattern.compile("<string name=\"" + name + "\">([^<]+)</string>");
			Matcher mName = pName.matcher(content);
			if (mName.find())
			{
				vName = mName.group(1);
				writeToFile(strings, mName.replaceAll("<string name=\"" + name + "\">" + newName + "</string>"));
			}
		}
	}

    private String readToString(File file, boolean forceRead)
	{
		try
		{
			String  line;
			boolean contains = forceRead;
			StringBuilder response = new StringBuilder();
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			while ((line = reader.readLine()) != null)
			{
				response.append(line).append("\n");
				if (!contains)
				{
					contains = (line.indexOf(vCode) >= 0 || line.indexOf(vCode) >= 0 || line.indexOf(vCodeHex) >= 0);
				}
			}
			return (contains ? response.toString() : null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void writeToFile(File file, String string)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes("UTF-8"));
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

