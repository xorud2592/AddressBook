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
		return name + "," + phoneNumber + "," + houseNumber;
	}

	public String showInfo() {
		return String.format("%-6s %-15s %-15s", name, phoneNumber, houseNumber);
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

}
