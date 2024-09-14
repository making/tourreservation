package org.terasoluna.tourreservation.common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class LegacyDate {

	public static Date fromLocalDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
