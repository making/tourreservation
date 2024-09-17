package org.terasoluna.tourreservation.tour;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.terasoluna.tourreservation.TestcontainersConfiguration;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@Import(TestcontainersConfiguration.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TourInfoMapperTest {

	TourInfoService tourInfoService;

	@Autowired
	TourInfoMapper tourInfoMapper;

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	String tourCode;

	LocalDate depDay;

	LocalDate plannedDay;

	String tourAbs;

	String tourName;

	int basePrice;

	TourInfoSearchCriteria criteria;

	@BeforeEach
	void setUp() {
		tourInfoService = new TourInfoService(tourInfoMapper);
		criteria = new TourInfoSearchCriteria();
		tourCode = "8888888888";
		depDay = LocalDate.of(2014, 2, 2);
		plannedDay = LocalDate.of(2013, 12, 31);
		tourAbs = "wonderful travel !";
		tourName = "test tour";
		basePrice = 20000;

		String q = """
				INSERT INTO tourinfo(
				    tour_code,
				    planned_day,
				    plan_no,
				    tour_name,
				    tour_days,
				    dep_day,
				    ava_rec_max,
				    dep_code,
				    arr_code,
				    accom_code,
				    base_price,
				    conductor,
				    tour_abs
				)
				VALUES(
				    :tourCode,
				    :plannedDay,
				    :planNo,
				    :tourName,
				    :tourDays,
				    :depDay,
				    :avaRecMax,
				    :depCode,
				    :arrCode,
				    :accomCode,
				    :basePrice,
				    :conductor,
				    :tourAbs
				)
				""";
		Map<String, Object> param = new HashMap<>();
		param.put("tourCode", tourCode);
		param.put("plannedDay", LegacyDate.fromLocalDate(plannedDay));
		param.put("planNo", "0101");
		param.put("tourName", tourName);
		param.put("tourDays", 1);
		param.put("depDay", LegacyDate.fromLocalDate(depDay));
		param.put("avaRecMax", 2147483647);
		param.put("depCode", "01");
		param.put("arrCode", "01");
		param.put("accomCode", "0001");
		param.put("basePrice", basePrice);
		param.put("conductor", 1);
		param.put("tourAbs", tourAbs);
		jdbcTemplate.update(q, param);
	}

	/**
	 * BasePrice and TourDays dont set value Case
	 */
	@Test
	void searchTourInfo01() {
		// search data
		criteria.setDepDate(LegacyDate.fromLocalDate(depDay));
		criteria.setAdultCount(1);
		criteria.setArrCode("01");
		criteria.setBasePrice(0);
		criteria.setChildCount(1);
		criteria.setDepCode("01");
		criteria.setTourDays(0);

		Pageable pageable = PageRequest.of(0, 10);

		// run
		Page<TourInfo> page = tourInfoService.searchTour(criteria, pageable);

		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getNumber()).isEqualTo(0);

		TourInfo tour = page.getContent().getFirst();

		// assert
		assertThat(tour.getAvaRecMax()).isEqualTo(2147483647);
		assertThat(tour.getBasePrice()).isEqualTo(basePrice);
		assertThat(tour.getConductor()).isEqualTo("1");
		assertThat(tour.getDepDay()).isEqualTo(LegacyDate.fromLocalDate(depDay));
		assertThat(tour.getPlannedDay()).isEqualTo(LegacyDate.fromLocalDate(plannedDay));
		assertThat(tour.getTourAbs()).isEqualTo(tourAbs);
		assertThat(tour.getTourCode()).isEqualTo(tourCode);
		assertThat(tour.getTourDays()).isEqualTo(1);
		assertThat(tour.getTourName()).isEqualTo(tourName);

		assertThat(tour.getAccommodation().getAccomCode()).isEqualTo("0001");
		assertThat(tour.getAccommodation().getAccomName()).isEqualTo("TERASOLUNAホテル第一荘");
		assertThat(tour.getAccommodation().getAccomTel()).isEqualTo("018-123-4567");

		assertThat(tour.getDeparture().getDepCode()).isEqualTo("01");
		assertThat(tour.getDeparture().getDepName()).isEqualTo("北海道");

	}

	/**
	 * BasePrice and TourDays set value Case
	 */
	@Test
	void searchTourInfo02() {
		// search data
		criteria
			.setDepDate(Date.from(LocalDate.of(2012, 7, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		criteria.setAdultCount(1);
		criteria.setArrCode("01");
		criteria.setBasePrice(10);
		criteria.setChildCount(1);
		criteria.setDepCode("01");
		criteria.setTourDays(2);

		Pageable pageable = PageRequest.of(0, 10);
		// run
		Page<TourInfo> page = tourInfoService.searchTour(criteria, pageable);

		// assert
		assertThat(page.getTotalPages()).isEqualTo(0);
		assertThat(page.getNumber()).isEqualTo(0);
	}

}