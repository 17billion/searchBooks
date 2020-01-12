package com.searchbooks.enums;

public enum EnumSearchTarget {
	전체("전체", "all"), 제목("제목", "title"), ISBN("ISBN", "isbn"), 목차("목차", "contents"), 출판사("출판사", "publisher");

	private String desc;
	private String code;

	private EnumSearchTarget(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}

	public static EnumSearchTarget getByCode(String code) {
		EnumSearchTarget returnValue = null;

		for (EnumSearchTarget temp : EnumSearchTarget.values()) {
			if (temp.getCode().equals(code)) {
				returnValue = temp;
				break;
			}
		}

		return returnValue;
	}
}
