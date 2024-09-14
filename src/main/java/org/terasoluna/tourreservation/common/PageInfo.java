package org.terasoluna.tourreservation.common;

import org.thymeleaf.util.NumberUtils;

import org.springframework.data.domain.Page;

public final class PageInfo {

	public static <T> Integer[] sequence(Page<T> page, int pageLinkMaxDispNum) {
		int begin = Math.max(1, page.getNumber() + 1 - pageLinkMaxDispNum / 2);
		int end = begin + (pageLinkMaxDispNum - 1);
		if (end > page.getTotalPages() - 1) {
			end = page.getTotalPages();
			begin = Math.max(1, end - (pageLinkMaxDispNum - 1));
		}
		return NumberUtils.sequence(begin, end);
	}

}
