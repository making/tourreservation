package org.terasoluna.tourreservation.tour;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourInfoServiceTest {

	TourInfoService tourInfoService;

	TourInfoMapper tourInfoMapper;

	@BeforeEach
	void setUp() {
		this.tourInfoMapper = mock(TourInfoMapper.class);
		this.tourInfoService = new TourInfoService(tourInfoMapper);
	}

	@Test
	void searchTourInfo01() {

		TourInfoSearchCriteria criteria = new TourInfoSearchCriteria();
		Pageable pageable = PageRequest.of(0, 10);

		List<TourInfo> mockedList = new ArrayList<>();

		TourInfo info = new TourInfo();
		Arrival a = new Arrival();
		a.setArrCode("1234");
		info.setArrival(a);

		Departure departure = new Departure();
		departure.setDepCode("5678");
		info.setDeparture(departure);
		info.setTourCode("12345678");
		mockedList.add(info);

		Page<TourInfo> page = new PageImpl<>(mockedList, pageable, 1L);

		when(this.tourInfoMapper.findPageBySearchCriteria(criteria, pageable)).thenReturn(mockedList);

		when(this.tourInfoMapper.countBySearchCriteria(criteria)).thenReturn(1L);

		// run
		Page<TourInfo> result = this.tourInfoService.searchTour(criteria, pageable);

		// assert
		assertThat(result).isEqualTo(page);
	}

}