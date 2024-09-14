package org.terasoluna.tourreservation.domain.service.tourinfo;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourInfoSharedServiceTest {

	TourInfoSharedService tourInfoSharedService;

	TourInfoRepository tourInfoRepository;

	Instant now = Instant.now();

	@BeforeEach
	void setUp() {
		this.tourInfoRepository = mock(TourInfoRepository.class);
		this.tourInfoSharedService = new TourInfoSharedService(this.tourInfoRepository,
				Clock.fixed(now, ZoneId.of("Asia/Tokyo")));
	}

	@Test
	void findOne01() {
		TourInfo info = new TourInfo();

		when(tourInfoRepository.findOneWithDetails("foo")).thenReturn(info);

		// run
		TourInfo result = tourInfoSharedService.findOneWithDetails("foo");

		// assert
		assertThat(result).isEqualTo(info);
	}

	@Test
	void isOverPaymentLimitTour01() {
		// normal case
		TourInfo tour = new TourInfo();
		Instant depDay = now.plus(7, ChronoUnit.DAYS); // within limit
		tour.setDepDay(Date.from(depDay));

		boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
		assertThat(result).isEqualTo(false);
	}

	@Test
	void isOverPaymentLimitTour02() {
		// invalid case
		TourInfo tour = new TourInfo();
		Instant depDay = now.plus(6, ChronoUnit.DAYS); // over limit
		tour.setDepDay(Date.from(depDay));

		boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
		assertThat(result).isEqualTo(true);
	}

	@Test
	void isOverPaymentLimitTour03() {
		// normal case
		TourInfo tour = new TourInfo();
		Instant depDay = now.plus(7, ChronoUnit.DAYS).minusMillis(1); // within limit
		tour.setDepDay(Date.from(depDay));

		boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
		assertThat(result).isEqualTo(false);
	}

}