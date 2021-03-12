package com.java.phone;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneController {
	static final String MEMU_INFO = "*****************************************\n"
			+ "*\t       전화번호 관리 프로그램        \t*\n" + "*****************************************";
	static final String MENU_SELECT = "1.리스트\t2.등록\t3.삭제\t4.검색\t5.종료\n" + "--------------------------------------\n"
			+ ">메뉴번호: ";
	static final String MENU_SHOWINFO_MESSAGE = "<1.리스트>";
	static final String MENU_ADDINFO_MESSAGE = "<2.이름>";
	static final String MENU_DELETE_MESSAGE = "<3.삭제>";
	static final String MENU_SEARCH_MESSAGE = "<4.검색>";

	static final String INPUT_ERROR = "[다시입력해주세요]";
	static final String INPUT_NUMBERFORMAT_ERROR = "[숫자를 입력해 주세요]";
	static final String INPUT_DBSEARCH_ERROR = "[리스트에 없는 숫자입니다.]";
	static final String INPUT_NAME = ">이름: ";
	static final String INPUT_NUMBER = ">번호: ";

	static final String INPUT_PHONENUMBER = ">휴대전화: ";
	static final String INPUT_HOUSENUMBER = ">집전화: ";

	static final String ADD_MESSAGE = "[등록되었습니다.]";
	static final String DELETE_MESSAGE = "[삭제되었습니다.]";
	static final String SEARCH_EMPTY_MESSAGE = "[아무것도 못찾았습니다.]";
	static final String END_MESSAGE = "*****************************************\n" + "*\t\t감사합니다\t\t\t*\n"
			+ "*****************************************";

	public static void start(Scanner input) {

		ArrayList<PhoneNumberInfo> phoneNumberDB = PhoneNumberDBReset.reset();

		controllProgram(input, phoneNumberDB);
		System.out.println(END_MESSAGE);
		input.close();
	}

	private static void controllProgram(Scanner input, ArrayList<PhoneNumberInfo> phoneNumberDB) {
		System.out.println(MEMU_INFO);
		boolean end = false;
		while (!end) {
			System.out.print(MENU_SELECT);
			try {
				int menu = Integer.parseInt(input.nextLine());
				System.out.println();

				switch (menu) {
				case 1:
					showNumberInfo(phoneNumberDB);
					break;
				case 2:
					addNumberInfo(input, phoneNumberDB);
					break;
				case 3:
					deleteNumberInfo(input, phoneNumberDB);
					break;
				case 4:
					searchNumberInfo(input, phoneNumberDB);
					break;
				case 5:
					end = true;
					break;
				default:
					System.err.println(INPUT_ERROR);
				}

				System.out.println();
			} catch (NumberFormatException e) {
				System.err.println(INPUT_NUMBERFORMAT_ERROR);
			}
		}
	}

	private static void showNumberInfo(ArrayList<PhoneNumberInfo> phoneNumberDB) {
		System.out.println(MENU_SHOWINFO_MESSAGE);
		int count = 1;
		for (PhoneNumberInfo numberInfo : phoneNumberDB) {
			System.out.println(numberInfo.showInfo(count++));
		}
	}

	private static void addNumberInfo(Scanner input, ArrayList<PhoneNumberInfo> phoneNumberDB) {
		System.out.println(MENU_ADDINFO_MESSAGE);
		System.out.print(INPUT_NAME);
		String name = input.nextLine();
		System.out.print(INPUT_PHONENUMBER);
		String phoneNumber = input.nextLine();
		System.out.print(INPUT_HOUSENUMBER);
		String houseNumber = input.nextLine();

		PhoneNumberInfo newNumberInfo = new PhoneNumberInfo(name, phoneNumber, houseNumber);

		if (PhoneNumberDBManeger.infoSameCheck(phoneNumberDB, newNumberInfo) && newNumberInfo.checkNumber()) {
			phoneNumberDB.add(newNumberInfo);
			PhoneNumberDBReset.updateNumberInfo(phoneNumberDB);
			System.out.println(ADD_MESSAGE);
		}
	}

	private static void deleteNumberInfo(Scanner input, ArrayList<PhoneNumberInfo> phoneNumberDB) {
		System.out.println(MENU_DELETE_MESSAGE);
		System.out.printf(INPUT_NUMBER);
		try {
			int target = Integer.parseInt(input.nextLine()) -1;
			phoneNumberDB.remove(phoneNumberDB.get(target));
			System.out.println(DELETE_MESSAGE);
			PhoneNumberDBReset.updateNumberInfo(phoneNumberDB);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INPUT_DBSEARCH_ERROR);
		} catch (NumberFormatException e) {
			System.err.println(INPUT_NUMBERFORMAT_ERROR);
		}
	}

	private static void searchNumberInfo(Scanner input, ArrayList<PhoneNumberInfo> phoneNumberDB) {
		System.out.println(MENU_SEARCH_MESSAGE);
		System.out.printf(INPUT_NAME);
		String target = input.nextLine();
		int count = 1;
		for (PhoneNumberInfo numberInfo : phoneNumberDB) {
			if (numberInfo.findName(target)) {
				System.out.println(numberInfo.showInfo(count++));
			}
		}
		if (count == 1) {
			System.out.println(SEARCH_EMPTY_MESSAGE);
		}
	}
}

//package com.java.phone;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Reader;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class PhoneController {
//	static final String rootPath = System.getProperty("user.dir");
//	static final String source = rootPath + "\\phoneNumberDB.txt";
//	static final String MEMU_INFO = "*****************************************\n"
//			+ "*\t       전화번호 관리 프로그램        \t*\n" + "*****************************************";
//	static final String MENU_SELECT = "1.리스트\t2.등록\t3.삭제\t4.검색\t5.종료\n" + "--------------------------------------\n"
//			+ ">메뉴번호: ";
//	static final String INPUT_ERROR = "[다시입력해주세요]";
//	static final String END_MESSAGE = "*****************************************\n" + "*\t\t감사합니다\t\t\t*\n"
//			+ "*****************************************";
//
//	private static ArrayList<PhoneNumberInfo> phoneNumberDB = new ArrayList<PhoneNumberInfo>();
//
//	public static void start(Scanner input) {
//		checkPhoneNumberDB();
//		readPhoneNumberDB();
//		controllProgram(input);
//		System.out.println(END_MESSAGE);
//		input.close();
//	}
//
//	private static void checkPhoneNumberDB() {
//		File file = new File(source);
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				System.err.println("파일을 만들지 못했습니다!");
//			}
//
//		}
//	}
//
//	private static void readPhoneNumberDB() {
//		Reader reader = null;
//		BufferedReader br = null;
//
//		try {
//			reader = new FileReader(source);
//
//			br = new BufferedReader(reader);
//
//			String line = null;
//
//			while ((line = br.readLine()) != null) {
//				String[] lineInfo = line.split(",");
//				PhoneNumberInfo numberInfo = new PhoneNumberInfo(lineInfo[0], lineInfo[1], lineInfo[2]);
//				phoneNumberDB.add(numberInfo);
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
//
//	private static void controllProgram(Scanner input) {
//		System.out.println(MEMU_INFO);
//		boolean end = false;
//		while (!end) {
//			System.out.print(MENU_SELECT);
//			try {
//				int menu = Integer.parseInt(input.nextLine());
//				System.out.println();
//
//				switch (menu) {
//				case 1:
//					showNumberInfo();
//					break;
//				case 2:
//					addNumberInfo(input);
//					break;
//				case 3:
//					deleteNumberInfo(input);
//					break;
//				case 4:
//					searchNumberInfo(input);
//					break;
//				case 5:
//					end = true;
//					break;
//				default:
//					System.out.println(INPUT_ERROR);
//				}
//
//				System.out.println();
//			} catch (NumberFormatException e) {
//				System.err.println("숫자를 입력해주세요");
//			}
//		}
//	}
//
//	private static void showNumberInfo() {
//		int count = 1;
//		for (PhoneNumberInfo numberInfo : phoneNumberDB) {
//			showNumber(count++, numberInfo);
//		}
//	}
//
////		Reader reader = null;
////		BufferedReader br = null;
////
////		try {
////			reader = new FileReader(source);
////			br = new BufferedReader(reader);
////
////			String line = null;
////			int count = 0;
////			while ((line = br.readLine()) != null) {
////				StringTokenizer st = new StringTokenizer(line, "\n");
////				while (st.hasMoreElements()) {
////					String token = st.nextToken();
////					String numberinfo[] = token.split(",");
////					System.out.print(
////							++count + ". " + numberinfo[0] + "\t" + numberinfo[1] + "\t" + numberinfo[2] + "\t" + "\n");
////				}
////			}
////		} catch (FileNotFoundException e) {
////		} catch (Exception e) {
////		} finally {
////			try {
////				br.close();
////			} catch (Exception e) {
////			}
////		}
////
////	}
//
//	private static void addNumberInfo(Scanner input) {
//		System.out.println();
//		System.out.println("<2.등록>");
//		System.out.print(">이름: ");
//		String name = input.nextLine();
//		System.out.print(">휴대전화: ");
//		String phoneNumber = input.nextLine();
//		System.out.print(">집전화: ");
//		String houseNumber = input.nextLine();
//
//		PhoneNumberInfo newNumberInfo = new PhoneNumberInfo(name, phoneNumber, houseNumber);
//		phoneNumberDB.add(newNumberInfo);
//
//		updateNumberInfo();
//		System.out.println("[등록되었습니다.]");
//
//		int count = 0;
//		
//		
////		for (PhoneNumberInfo p :  phoneNumberDB) {
////			count++;
////			if(p.equals(newNumberInfo))
////			{
////				System.out.println(count + "같은거있음");
////			}
////		}
//	}
////		Writer writer = null;
////
////		try {
////			writer = new FileWriter(source);
////			for (PhoneNumberInfo numberInfo : phoneNumberDB) {
////				writer.write(numberInfo.toString());
////				writer.write("\n");
////				writer.flush();
////				System.out.println(numberInfo.toString());
////			}
////			writer.write(newNumberInfo.toString());
////			phoneNumberDB.add(newNumberInfo);
////			System.out.println();
////			writer.flush();
////		} catch (IOException e) {
////			e.printStackTrace();
////		} finally {
////			try {
////				writer.close();
////			} catch (Exception e) {
////			}
////		}
////	}
//
//	private static void deleteNumberInfo(Scanner input) {
//		try {
//			System.out.println("<3.삭제>");
//			System.out.print(">번호: ");
//
//			int target = Integer.parseInt(input.nextLine());
//			int count = 1;
//
//			if (phoneNumberDB.size() != 0 && target <= phoneNumberDB.size()) {
//				for (PhoneNumberInfo numberInfo : phoneNumberDB) {
//					if (count++ == target) {
//						phoneNumberDB.remove(numberInfo);
//						System.out.println("[삭제되었습니다.]");
//						break;
//					}
//				}
//				updateNumberInfo();
//			} else if (target >= phoneNumberDB.size())
//				System.out.println("없는 번호입니다.");
//			else
//				System.out.println("저장된 값이 없습니다.");
//		} catch (NullPointerException e) {
//			System.err.println("없는 번호입니다");
//		} catch (NumberFormatException e) {
//			System.err.println("숫자를 입력해 주세요");
//		}
//	}
//
//	private static void updateNumberInfo() {
//		OutputStream os = null;
//		OutputStreamWriter dos = null;
//		BufferedWriter br = null;
//
//		try {
//			os = new FileOutputStream(source);
//			dos = new OutputStreamWriter(os, "UTF-8");
//			br = new BufferedWriter(dos);
//
//			for (PhoneNumberInfo numberInfo : phoneNumberDB) {
//				br.write(numberInfo.toString());
//				br.write("\n");
//			}
//		} catch (FileNotFoundException e) {
//			System.err.println("파일을 찾을수없습니다.");
//		} catch (IOException e) {
//
//		} finally {
//			try {
//				br.close();
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	private static void searchNumberInfo(Scanner input) {
//		System.out.println("<4.검색>");
//		System.out.print(">이름: ");
//		String target = input.nextLine();
//		int count = 1;
//		for (PhoneNumberInfo numberInfo : phoneNumberDB) {
//			if (numberInfo.getName().indexOf(target) != -1) {
//				showNumber(count++, numberInfo);
//			}
//		}
//	}
//
//	private static void showNumber(int count, PhoneNumberInfo numberInfo) {
//		
//		System.out.println(count + ". " + numberInfo.showInfo());
//	}
////		Reader reader = null;
////		BufferedReader br = null;
////
////		try {
////
////			reader = new FileReader(source);
////
////			br = new BufferedReader(reader);
////
////			String line = null;
////
////			while ((line = br.readLine()) != null) {
////				int count = 0;
////				if (line.contains(target)) {
////					String numberinfo[] = line.split(",");
////					System.out.print(
////							++count + ". " + numberinfo[0] + "\t" + numberinfo[1] + "\t" + numberinfo[2] + "\t" + "\n");
////				}
////			}
////		} catch (FileNotFoundException e) {
////			System.err.println("파일을 찾지 못했습니다");
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			try {
////				br.close();
////			} catch (Exception e) {
////
////			}
////		}
////	}
//
//}
