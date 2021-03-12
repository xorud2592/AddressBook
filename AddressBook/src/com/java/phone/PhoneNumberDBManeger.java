package com.java.phone;

import java.util.ArrayList;

public class PhoneNumberDBManeger {
	static final String DB_ERROR_SAMEINFO = "[중복된 번호가 있습니다.]";

	public static boolean infoSameCheck(ArrayList<PhoneNumberInfo> phoneNumberDB, PhoneNumberInfo newNumberInfo) {
		for (PhoneNumberInfo searchDB : phoneNumberDB) {
			if (searchDB.equals(newNumberInfo)) {
				System.out.println(DB_ERROR_SAMEINFO);
				return false;
			}
		}
		return true;
	}
}
