// Патч заменяет переносы строк Unix (\n) на переносы строк MS-DOS (\r\n) в произвольных файлах
// © Maximoff, 2021

package ru.maximoff.patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class n2rn {
    public n2rn() {}

    public void patch(String apkPath, String patchPath, String decodePath, String param) {
		if (param.indexOf(':') >= 0) {
			String[] splitParam = param.split(":");
			for (String p : splitParam) {
				File target = new File(decodePath + "/" + p);
				if (!target.exists() || target.isDirectory()) {
					continue;
				}
				readFile(target);
			}
			return;
		}
        File target = new File(decodePath + "/" + param);
		if (!target.exists() || target.isDirectory()) {
			return;
		}
		readFile(target);
    }

    private boolean readFile(File file) {
		FileInputStream fis = null;
		BufferedReader reader = null;
		try {
			String line;
			String res = "";
			fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis));
			while ((line = reader.readLine()) != null) {
				res += line + "\r\n";
			}
			return writeToFile(file, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null)
					fis.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {}
		}
		return false;
	}

	private boolean writeToFile(File file, String string) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes());
			fos.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
