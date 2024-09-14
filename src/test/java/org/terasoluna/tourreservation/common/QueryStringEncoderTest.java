package org.terasoluna.tourreservation.common;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.app.reservetour.ReserveTourForm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.terasoluna.tourreservation.app.reservetour.ReserveTourFormBuilder.reserveTourForm;

class QueryStringEncoderTest {

	@Test
	void encode() {
		ReserveTourForm form = reserveTourForm().adultCount(1).childCount(2).remarks("テスト").build();
		String queryString = QueryStringEncoder.encode(form);
		assertThat(queryString).isEqualTo("adultCount=1&childCount=2&remarks=%E3%83%86%E3%82%B9%E3%83%88");
	}

	@Test
	void encodeList() {
		String queryString = QueryStringEncoder.encode(new Foo("test", List.of("a", "b", "あ")));
		assertThat(queryString).isEqualTo("foo=test&bar=a&bar=b&bar=%E3%81%82");

	}

	record Foo(String foo, List<String> bar) {
	}

}