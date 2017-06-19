package com.key.common.plugs.itextpdf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WordTest {
	/**
	 * 读取html文件到word
	 * 
	 * @param filepath
	 *            html文件的路径
	 * @return
	 * @throws Exception
	 */
	public boolean writeWordFile(String filepath) throws Exception {
		boolean flag = false;
		ByteArrayInputStream bais = null;
		FileOutputStream fos = null;
		String path = "D:/"; // 根据实际情况写路径
		try {
			if (!"".equals(path)) {
				File fileDir = new File(path);
				if (fileDir.exists()) {
					String content = readFile(filepath);
					byte b[] = content.getBytes();
					bais = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					DirectoryEntry directory = poifs.getRoot();
					DocumentEntry documentEntry = directory.createDocument(
							"WordDocument", bais);
					fos = new FileOutputStream(path + "temp1.doc");
					poifs.writeFilesystem(fos);
					bais.close();
					fos.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				fos.close();
			if (bais != null)
				bais.close();
		}
		return flag;
	}

	/**
	 * 读取html文件到字符串
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		StringBuffer buffer = new StringBuffer("");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			buffer = new StringBuffer();
			while (br.ready())
				buffer.append((char) br.read());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}
		return buffer.toString(); 
	}

	public static void main(String[] args) throws Exception {
		new WordTest().writeWordFile("D:/ttpp.htm");
	}
}
