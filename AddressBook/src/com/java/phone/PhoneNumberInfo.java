package com.java.phone;

public class PhoneNumberInfo {

	static final String INPUT_NUMBER_ERROR = "숫자만 입력해주세요";
	static final String INPUT_FORM_ERROR = "[올바른 전화번호 형식이 아닙니다]";

	private String name;
	private String phoneNumber;
	private String houseNumber;

	public PhoneNumberInfo(String name, String phoneNumber, String houseNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.houseNumber = houseNumber;
	}

	@Override
	public String toString() {
		return String.format("%-6s %-15s %-15s", name, filter(phoneNumber), filter(houseNumber));
	}

	private String filter(String number) {
		String[] numbers = number.split("-");
		return numbers[0] + "-" + (numbers[1].substring(0, numbers[1].length() - 2) + "**") + "-"
				+ (numbers[2].substring(0, numbers[2].length() - 2) + "**");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhoneNumberInfo) {
			PhoneNumberInfo temp = (PhoneNumberInfo) obj;
			if (this.name.equals(temp.name) && this.phoneNumber.equals(temp.phoneNumber)
					&& this.houseNumber.equals(temp.houseNumber))
				return true;
		}
		return false;
	}

	public boolean findName(String target) {
		if (this.name.indexOf(target) == -1) {
			return false;
		}
		return true;
	}

	public boolean checkNumber() {
		String[] phoneNumberInts = phoneNumber.trim().split("-");
		String[] houseNumberInts = houseNumber.trim().split("-");

		if (phoneNumberInts.length != 3 || houseNumberInts.length != 3) {
			System.out.println(INPUT_FORM_ERROR);
			return false;
		}

		try {
			if (houseNumberInts[0].length() == 0 || phoneNumberInts[0].length() == 0)
				throw new Exception();
			for (int count = 0; count < 3; count++) {
				if (count != 0 && Integer.parseInt(phoneNumberInts[count]) / 100 <= 0
						&& Integer.parseInt(phoneNumberInts[count]) / 10000 == 0)
					throw new Exception();
			}
			for (int count = 0; count < 3; count++) {
				if (count != 0 && Integer.parseInt(houseNumberInts[count]) / 100 <= 0
						&& Integer.parseInt(houseNumberInts[count]) / 10000 == 0)
					throw new Exception();
			}
		} catch (NumberFormatException e) {
			System.err.println(INPUT_NUMBER_ERROR);
			return false;
		} catch (Exception e) {
			System.out.println(INPUT_FORM_ERROR);
			return false;
		}
		return true;
	}
}
