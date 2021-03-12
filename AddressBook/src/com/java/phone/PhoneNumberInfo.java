package com.java.phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberInfo {

	static final String INPUT_NUMBER_ERROR = "숫자만 입력해주세요";
	static final String INPUT_FORM_ERROR = "[올바른 전화번호 형식이 아닙니다]";

	private String name;
	private String phoneNumber;
	private String houseNumber;

	public PhoneNumberInfo(String name, String phoneNumber, String houseNumber) {
		this.name = name.trim();
		this.phoneNumber = phoneNumber.trim();
		this.houseNumber = houseNumber.trim();
	}

	public void check() {
	}

	@Override
	public String toString() {
		return name + "," + phoneNumber + "," + houseNumber;
	}

	public String showInfo() {
		return String.format("%-6s %-15s %-15s", name, filter(phoneNumber), filter(houseNumber));
	}
	public String showInfo(int count) {
		return String.format("%d. %-6s %-15s %-15s", count, name, filter(phoneNumber), filter(houseNumber));
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
		Pattern phoneNumberPattern = Pattern.compile("^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$");
		Pattern houseNumberPattern = Pattern.compile("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$");

		Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phoneNumber);
		Matcher houseNumberMatcher = houseNumberPattern.matcher(houseNumber);

		if (phoneNumberMatcher.find()) {
			if (houseNumberMatcher.find()) {
				return true;
			}
		}

		System.out.println(INPUT_FORM_ERROR);
		return false;
	}
}
