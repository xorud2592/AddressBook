package com.java.phone;

public class PhoneNumberInfo {
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
		return  name + "," + phoneNumber + "," + houseNumber;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getHouseNumber() {
		return houseNumber;
	}
}
