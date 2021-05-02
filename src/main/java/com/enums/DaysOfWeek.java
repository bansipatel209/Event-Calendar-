package com.enums;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

public enum DaysOfWeek {
	MONDAY(4), TUESDAY(8), WEDNESDAY(16), THURSDAY(32), FRIDAY(64), SATURDAY(128), SUNDAY(2);

	private int code;

	private static Map<String, Integer> valueMap;

	static {
		valueMap = stream(DaysOfWeek.values()).collect(toMap(  day -> day.toString() , DaysOfWeek ::getValue));
	}

	DaysOfWeek(int code) {
		this.code = code;
	}

	public int getValue() {
		return this.code;
	}

	public static Integer fromDayValue(String value) {
		return valueMap.get(value);
	}
}
