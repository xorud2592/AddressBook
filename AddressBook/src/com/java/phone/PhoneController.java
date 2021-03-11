package com.java.phone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PhoneController {
	static final String rootPath = System.getProperty("user.dir");
	static final String source = rootPath + "\\phoneNumberDB.txt";
	static final String MEMU_INFO = "*****************************************\n"
			+ "*\t       전화번호 관리 프로그램        \t*\n" + "*****************************************";
	static final String MENU_SELECT = "1.리스트\t2.등록\t3.삭제\t4.검색\t5.종료\n" + "--------------------------------------\n"
			+ ">메뉴번호: ";
	static final String INPUT_ERROR = "[다시입력해주세요]";
	static final String END_MESSAGE = "*****************************************\n" + "*\t\t감사합니다\t\t\t*\n"
			+ "*****************************************";

	private static ArrayList<PhoneNumberInfo> numberInfos = new ArrayList<PhoneNumberInfo>();

	public static void start(Scanner input) {
		checkPhoneNumberDB();
		readPhoneNumberDB();
		controllProgram(input);
		System.out.println(END_MESSAGE);
		input.close();
	}

	private static void checkPhoneNumberDB() {
		File file = new File(source);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("파일을 만들지 못했습니다!");
			}

		}
	}

	private static void readPhoneNumberDB() {
		Reader reader = null;
		BufferedReader br = null;

		try {
			reader = new FileReader(source);

			br = new BufferedReader(reader);

			String line = null;

			while ((line = br.readLine()) != null) {
				String[] lineInfo = line.split(",");
				PhoneNumberInfo numberInfo = new PhoneNumberInfo(lineInfo[0], lineInfo[1], lineInfo[2]);
				numberInfos.add(numberInfo);
			}
		} catch (FileNotFoundException e) {
			System.err.println("파일을 찾지 못했습니다");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {

			}
		}
	}

	private static void controllProgram(Scanner input) {
		System.out.println(MEMU_INFO);
		boolean end = false;
		while (!end) {
			System.out.print(MENU_SELECT);
			try {
				int menu = Integer.parseInt(input.nextLine());
				System.out.println();

				switch (menu) {
				case 1:
					showNumberInfo();
					break;
				case 2:
					addNumberInfo(input);
					break;
				case 3:
					deleteNumberInfo(input);
					break;
				case 4:
					searchNumberInfo(input);
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println(INPUT_ERROR);
				}

				System.out.println();
			} catch (NumberFormatException e) {
				System.err.println("숫자를 입력해주세요");
			}
		}
	}

	private static void showNumberInfo() {
		int count = 1;
		for (PhoneNumberInfo numberInfo : numberInfos) {
			showNumber(count++, numberInfo);
		}
	}

//		Reader reader = null;
//		BufferedReader br = null;
//
//		try {
//			reader = new FileReader(source);
//			br = new BufferedReader(reader);
//
//			String line = null;
//			int count = 0;
//			while ((line = br.readLine()) != null) {
//				StringTokenizer st = new StringTokenizer(line, "\n");
//				while (st.hasMoreElements()) {
//					String token = st.nextToken();
//					String numberinfo[] = token.split(",");
//					System.out.print(
//							++count + ". " + numberinfo[0] + "\t" + numberinfo[1] + "\t" + numberinfo[2] + "\t" + "\n");
//				}
//			}
//		} catch (FileNotFoundException e) {
//		} catch (Exception e) {
//		} finally {
//			try {
//				br.close();
//			} catch (Exception e) {
//			}
//		}
//
//	}

	private static void addNumberInfo(Scanner input) {
		System.out.println();
		System.out.println("<2.등록>");
		System.out.print(">이름: ");
		String name = input.nextLine();
		System.out.print(">휴대전화: ");
		String phoneNumber = input.nextLine();
		System.out.print(">집전화: ");
		String houseNumber = input.nextLine();

		PhoneNumberInfo newNumberInfo = new PhoneNumberInfo(name, phoneNumber, houseNumber);
		numberInfos.add(newNumberInfo);

		updateNumberInfo();
		System.out.println("[등록되었습니다.]");

	}
//		Writer writer = null;
//
//		try {
//			writer = new FileWriter(source);
//			for (PhoneNumberInfo numberInfo : numberInfos) {
//				writer.write(numberInfo.toString());
//				writer.write("\n");
//				writer.flush();
//				System.out.println(numberInfo.toString());
//			}
//			writer.write(newNumberInfo.toString());
//			numberInfos.add(newNumberInfo);
//			System.out.println();
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				writer.close();
//			} catch (Exception e) {
//			}
//		}
//	}

	private static void deleteNumberInfo(Scanner input) {
		try {
			System.out.println("<3.삭제>");
			System.out.print(">번호: ");

			int target = Integer.parseInt(input.nextLine());
			int count = 1;

			if (numberInfos.size() != 0 && target < numberInfos.size()) {
				for (PhoneNumberInfo numberInfo : numberInfos) {
					if (count++ == target) {
						numberInfos.remove(numberInfo);
						System.out.println("[삭제되었습니다.]");
						break;
					}
				}
				updateNumberInfo();
			} else if (target >= numberInfos.size())
				System.out.println("없는 번호입니다.");
			else
				System.out.println("저장된 값이 없습니다.");
		} catch (NullPointerException e) {
			System.err.println("없는 번호입니다");
		} catch (NumberFormatException e) {
			System.err.println("숫자를 입력해 주세요");
		}
	}

	private static void updateNumberInfo() {
		OutputStream os = null;
		OutputStreamWriter dos = null;
		BufferedWriter br = null;

		try {
			os = new FileOutputStream(source);
			dos = new OutputStreamWriter(os, "UTF-8");
			br = new BufferedWriter(dos);

			for (PhoneNumberInfo numberInfo : numberInfos) {
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

	private static void searchNumberInfo(Scanner input) {
		System.out.println("<4.검색>");
		System.out.print(">이름: ");
		String target = input.nextLine();
		int count = 1;
		for (PhoneNumberInfo numberInfo : numberInfos) {
			if (numberInfo.getName().indexOf(target) != -1) {
				showNumber(count++, numberInfo);
			}
		}
	}

	private static void showNumber(int count, PhoneNumberInfo numberInfo) {
		System.out.println(count + ". " + numberInfo.getName() + "\t" + numberInfo.getPhoneNumber() + "\t"
				+ numberInfo.getHouseNumber());
	}
//		Reader reader = null;
//		BufferedReader br = null;
//
//		try {
//
//			reader = new FileReader(source);
//
//			br = new BufferedReader(reader);
//
//			String line = null;
//
//			while ((line = br.readLine()) != null) {
//				int count = 0;
//				if (line.contains(target)) {
//					String numberinfo[] = line.split(",");
//					System.out.print(
//							++count + ". " + numberinfo[0] + "\t" + numberinfo[1] + "\t" + numberinfo[2] + "\t" + "\n");
//				}
//			}
//		} catch (FileNotFoundException e) {
//			System.err.println("파일을 찾지 못했습니다");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				br.close();
//			} catch (Exception e) {
//
//			}
//		}
//	}

}
