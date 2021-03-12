package com.java.phone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;

public class PhoneNumberDBReset {
	static final String rootPath = System.getProperty("user.dir");
	static final String DB = rootPath + "\\phoneNumberDB.txt";
	static final String FILE_MAKE_ERR = "파일을 만들지 못했습니다!";
	static final String FILE_FIND_ERR = "파일을 찾지 못했습니다.";
	static final String DB_SPLIT_POINT = ",";

	public static ArrayList<PhoneNumberInfo> reset() {
		checkPhoneNumberDB();
		return readPhoneNumberDB();
	}

	private static void checkPhoneNumberDB() {
		File file = new File(DB);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println(FILE_MAKE_ERR);
			}
		}
	}

	private static ArrayList<PhoneNumberInfo> readPhoneNumberDB() {
		ArrayList<PhoneNumberInfo> phoneNumberDB = new ArrayList<PhoneNumberInfo>();
		Reader reader = null;
		BufferedReader br = null;
 
		try {
			reader = new FileReader(DB);
			br = new BufferedReader(reader);

			String line = null;

			while ((line = br.readLine()) != null) {
				String[] lineInfo = line.split(DB_SPLIT_POINT);
				PhoneNumberInfo numberInfo = new PhoneNumberInfo(lineInfo[0], lineInfo[1], lineInfo[2]);
				phoneNumberDB.add(numberInfo);
			}
		} catch (FileNotFoundException e) {
			System.err.println(FILE_FIND_ERR);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {

			}
		}
		return phoneNumberDB;
	}

	public static void updateNumberInfo(ArrayList<PhoneNumberInfo> phoneNumberDB) {
		OutputStream os = null;
		OutputStreamWriter dos = null;
		BufferedWriter br = null;

		try {
			os = new FileOutputStream(DB);
			dos = new OutputStreamWriter(os, "UTF-8");
			br = new BufferedWriter(dos);

			for (PhoneNumberInfo numberInfo : phoneNumberDB) {
				br.write(numberInfo.toString());
				br.write("\n");
			}
		} catch (FileNotFoundException e) {
			System.err.println("파일을 찾을수없습니다.");
		} catch (IOException e) {

		} finally {
			try {
				br.close();
			} catch (Exception e) {
			}
		}
	}

}
