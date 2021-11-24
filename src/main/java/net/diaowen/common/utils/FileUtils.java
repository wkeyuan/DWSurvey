package net.diaowen.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static File transferFile(String path, MultipartFile file)
			throws IllegalStateException, IOException {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}

		if (file.getOriginalFilename().lastIndexOf(".") > 0) {
			File aFile = new File(path + file.getOriginalFilename());

			String strLast = path
					.substring(0, path.lastIndexOf(File.separator));

			int nameLength = strLast.substring(
					strLast.lastIndexOf(File.separator)).length()
					+ 1 + aFile.getName().length();
			// 如果上传的文件的名字中含有中文字符或其他非单词字符，那么就进行重命名，并将其改为英文名字
			// 这里所说的单词字符为：[a-zA-Z_0-9]
			Boolean rename = true;
			// String pattern="[\u4e00-\u9fa5]+";
			// Pattern p=Pattern.compile(pattern);
			// Matcher result=p.matcher(file.getOriginalFilename());
			// rename=result.find();
			if (aFile != null && aFile.exists() || nameLength > 30 || rename) {
				char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
						'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
						'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
						'5', '6', '7', '8', '9' };

				StringBuffer fileName = new StringBuffer("");
				Random r = new Random();
				int pos = -1;
				for (int i = 0; i < 15; i++) {
					pos = Math.abs(r.nextInt(36));
					fileName.append(str[pos]);
				}

				String newName = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1);

				aFile = new File(path + fileName.toString().trim() + "."
						+ newName);

			}
			file.transferTo(aFile);
			return aFile;
		} else {
			return null;
		}
	}

	public static File transferFile1(String path, MultipartFile file)
			throws IllegalStateException, IOException {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}

		if (file.getOriginalFilename().lastIndexOf(".") > 0) {
			File aFile = new File(path + file.getOriginalFilename());

			String strLast = path
					.substring(0, path.lastIndexOf(File.separator));

			int nameLength = strLast.substring(
					strLast.lastIndexOf(File.separator)).length()
					+ 1 + aFile.getName().length();
			// 如果上传的文件的名字中含有中文字符或其他非单词字符，那么就进行重命名，并将其改为英文名字
			// 这里所说的单词字符为：[a-zA-Z_0-9]
			Boolean rename = true;
			// String pattern="[\u4e00-\u9fa5]+";
			// Pattern p=Pattern.compile(pattern);
			// Matcher result=p.matcher(file.getOriginalFilename());
			// rename=result.find();
			if (aFile != null && aFile.exists() || nameLength > 30 || rename) {
				char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
						'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
						'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
						'5', '6', '7', '8', '9' };

				StringBuffer fileName = new StringBuffer("");
				Random r = new Random();
				int pos = -1;
				for (int i = 0; i < 15; i++) {
					pos = Math.abs(r.nextInt(36));
					fileName.append(str[pos]);
				}

				String newName = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1);

				aFile = new File(path + fileName.toString().trim() + "."
						+ newName);
			}
			file.transferTo(aFile);
			return aFile;
		} else {
			return null;
		}
	}

	public static String[] transferFile2(String path, File[] file,String[] filenames)
			throws IllegalStateException, IOException {
		File file2=new File(path);
		if (!file2.exists() || !file2.isDirectory()) {
			file2.mkdirs();
		}
		if (file != null && file.length > 0) {
			String[] temp=new String[file.length];
			char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9' };
			for (int i = 0; i < file.length; i++) {
				try {
					FileInputStream fis = new FileInputStream(file[i]);
					StringBuffer fileName = new StringBuffer("");
					Random r = new Random();
					int pos = -1;
					for (int j = 0; j < 15; j++) {
						pos = Math.abs(r.nextInt(36));
						fileName.append(str[pos]);
					}
					fileName.append(filenames[i].substring(filenames[i].lastIndexOf(".")));

					FileOutputStream fos = new FileOutputStream(path+ fileName.toString());
					byte[] buf = new byte[1024];
					int j = 0;
					while ((j = fis.read(buf)) != -1) {
						fos.write(buf, 0, j);
					}
					fis.close();
					fos.close();
					temp[i]=fileName.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return temp;
		}
		return null;
	}

	/**
	 * 删除文件或者文件夹，对于文件夹遍历其子文件夹进行递归删除
	 *
	 * @param f -
	 *            File对象
	 * @return 删除是否成功
	 */
	public static boolean deleteFile(File f) {
		if (f.exists()) {
			if (f.isFile())
				return f.delete();
			else if (f.isDirectory()) {
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (!deleteFile(files[i]))
						return false;
				}
				return f.delete();
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 读文件
	 * @param path
	 * @param encoding
	 * @return
	 */
	public static String readfile(String path, String encoding) {
		StringBuffer stringBuffer=new StringBuffer();
		BufferedReader reader = null;
		File inputPath = new File(path);
		if (inputPath.exists()) {
			try {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(inputPath), encoding));
				String line;
				while ((line = reader.readLine()) != null) {
					if (StringUtils.isNotBlank(line)) {
//						sl.add(line);
						stringBuffer.append(line+"\r\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	return stringBuffer.toString();
	}
}
