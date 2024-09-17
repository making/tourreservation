package org.terasoluna.tourreservation.reserve;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.terasoluna.tourreservation.TestcontainersConfiguration;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@Import(TestcontainersConfiguration.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReserveMapperTest {

	@Autowired
	ReserveMapper reserveMapper;

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	void save() {
		String tourCode = "0000000001";
		Instant now = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		TourInfo tourInfo = new TourInfo();
		tourInfo.setTourCode(tourCode);
		String customerCode = "00000001";
		String reserveNo = "10000000";
		String q = """
				INSERT INTO reserve (
				    reserve_no,
				    tour_code,
				    reserved_day,
				    adult_count,
				    child_count,
				    customer_code,
				    transfer,
				    sum_price,
				    remarks
				)
				VALUES (
				    :reserveNo,
				    :tourCode,
				    :reservedDay,
				    :adultCount,
				    :childCount,
				    :customerCode,
				    :transfer,
				    :sumPrice,
				    :remarks
				)
				""";
		{
			Map<String, Object> param = new HashMap<>();
			param.put("reserveNo", reserveNo);
			param.put("tourCode", tourCode);
			param.put("reservedDay", Date.from(now));
			param.put("adultCount", 1);
			param.put("childCount", 2);
			param.put("customerCode", customerCode);
			param.put("transfer", "0");
			param.put("sumPrice", 1000);
			param.put("remarks", "TEST");
			jdbcTemplate.update(q, param);
		}

		Reserve reserve = reserveMapper.findOneWithDetail(reserveNo);
		assertThat(reserve).isNotNull();
		assertThat(reserve.getReserveNo()).isEqualTo(reserveNo);
		assertThat(reserve.getReservedDay()).isEqualTo(Date.from(now));
		assertThat(reserve.getAdultCount()).isEqualTo(1);
		assertThat(reserve.getChildCount()).isEqualTo(2);
		assertThat(reserve.getTransfer()).isEqualTo("0");
		assertThat(reserve.getSumPrice()).isEqualTo(1000);
		assertThat(reserve.getRemarks()).isEqualTo("TEST");
		assertThat(reserve.getCustomer()).isNotNull();
		assertThat(reserve.getCustomer().getCustomerCode()).isEqualTo(customerCode);
		assertThat(reserve.getTourInfo()).isNotNull();
		assertThat(reserve.getTourInfo().getTourCode()).isEqualTo(tourCode);
	}

	@Test
	void findWithDetail02() {
		String reserveNo = "10000000";
		Reserve reserve = reserveMapper.findOneWithDetail(reserveNo);
		assertThat(reserve).isNull();
	}

	@Test
	void findReservedSumByTourInfo01() {
		String tourCode = "0000000001";
		Instant now = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		TourInfo tourInfo = new TourInfo();
		tourInfo.setTourCode(tourCode);
		String customerCode = "00000001";

		String q = """
				INSERT INTO reserve (
				    reserve_no,
				    tour_code,
				    reserved_day,
				    adult_count,
				    child_count,
				    customer_code,
				    transfer,
				    sum_price,
				    remarks
				)
				VALUES(
				    :reserveNo,
				    :tourCode,
				    :reservedDay,
				    :adultCount,
				    :childCount,
				    :customerCode,
				    :transfer,
				    :sumPrice,
				    :remarks
				)
				""";
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("reserveNo", "10000000");
			param.put("tourCode", tourCode);
			param.put("reservedDay", Date.from(now));
			param.put("adultCount", 1);
			param.put("childCount", 1);
			param.put("customerCode", customerCode);
			param.put("transfer", "0");
			param.put("sumPrice", 1000);
			param.put("remarks", "TEST");
			jdbcTemplate.update(q, param);
		}
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("reserveNo", "10000001");
			param.put("tourCode", tourCode);
			param.put("reservedDay", Date.from(now));
			param.put("adultCount", 2);
			param.put("childCount", 3);
			param.put("customerCode", customerCode);
			param.put("transfer", "0");
			param.put("sumPrice", 1000);
			param.put("remarks", "TEST");
			jdbcTemplate.update(q, param);
		}

		// run
		Long retValue = reserveMapper.countReservedPersonSumByTourInfo(tourInfo.getTourCode());

		// assert
		assertThat(retValue).isEqualTo(7L);
	}

	@Test
	void findReservedSumByTourInfo02() {
		TourInfo tourInfo = new TourInfo();
		tourInfo.setTourCode("xxxxx");

		// run
		Long retValue = reserveMapper.countReservedPersonSumByTourInfo(tourInfo.getTourCode());

		// assert
		assertThat(retValue).isNull();
	}

	@Test
	void findByCustomer01() {
		String tourCode = "8888888888";
		LocalDate today = LocalDate.now();
		LocalDate reservedDay = today;
		LocalDate depDay = today.plusDays(32);
		LocalDate plannedDay = today.minusDays(1);
		String tourAbs = "wonderful travel !";
		String tourName = "test tour";
		String customerCode = "00000001";

		{
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
					values(
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
			param.put("basePrice", 20000);
			param.put("conductor", 1);
			param.put("tourAbs", tourAbs);
			jdbcTemplate.update(q, param);
		}

		{
			String q = """
					INSERT INTO reserve(
					    reserve_no,
					    tour_code,
					    reserved_day,
					    adult_count,
					    child_count,
					    customer_code,
					    transfer,
					    sum_price,
					    remarks
					)
					VALUES(
					    :reserveNo,
					    :tourCode,
					    :reservedDay,
					    :adultCount,
					    :childCount,
					    :customerCode,
					    :transfer,
					    :sumPrice,
					    :remarks
					)
					""";
			Map<String, Object> param = new HashMap<>();
			param.put("reserveNo", "00000000");
			param.put("tourCode", tourCode);
			param.put("reservedDay", LegacyDate.fromLocalDate(reservedDay));
			param.put("adultCount", 1);
			param.put("childCount", 1);
			param.put("customerCode", customerCode);
			param.put("transfer", "0");

			param.put("sumPrice", 1000);
			param.put("remarks", "TEST");
			jdbcTemplate.update(q, param);
		}

		// run
		List<Reserve> reservationList = reserveMapper.findAllWithTourInfoByCustomer(customerCode);

		// assert
		assertThat(reservationList).isNotNull();
		assertThat(reservationList.size() > 0).isEqualTo(true);
		Reserve r = reservationList.getLast(); // last
		// inserted
		assertThat(r.getAdultCount()).isEqualTo(1);
		assertThat(r.getChildCount()).isEqualTo(1);
		assertThat(r.getReservedDay()).isEqualTo(LegacyDate.fromLocalDate(reservedDay));
		assertThat(r.getRemarks()).isEqualTo("TEST");
		assertThat(r.getReserveNo()).isEqualTo("00000000");
		assertThat(r.getSumPrice()).isEqualTo(1000);
		assertThat(r.getTransfer()).isEqualTo("0");

		assertThat(r.getCustomer().getCustomerAdd()).isEqualTo("千葉県八千代市上高野");
		assertThat(r.getCustomer().getCustomerBirth()).isEqualTo(LegacyDate.fromLocalDate(LocalDate.of(1975, 1, 5)));
		assertThat(r.getCustomer().getCustomerCode()).isEqualTo("00000001");
		assertThat(r.getCustomer().getCustomerJob()).isEqualTo("プログラマ");
		assertThat(r.getCustomer().getCustomerKana()).isEqualTo("キムラ　タロウ");
		assertThat(r.getCustomer().getCustomerMail()).isEqualTo("tarou@example.com");
		assertThat(r.getCustomer().getCustomerName()).isEqualTo("木村　太郎");
		assertThat(r.getCustomer().getCustomerPass())
			.isEqualTo("{pbkdf2}34036dc513d126f31515bc6401347a33bc495df1b27b9b39c5770798caa96594616716943f2f33e4");
		assertThat(r.getCustomer().getCustomerPost()).isEqualTo("276-0022");
		assertThat(r.getCustomer().getCustomerTel()).isEqualTo("111-1111-1111");

		assertThat(r.getTourInfo().getAvaRecMax()).isEqualTo(2147483647);
		assertThat(r.getTourInfo().getBasePrice()).isEqualTo(20000);
		assertThat(r.getTourInfo().getConductor()).isEqualTo("1");
		assertThat(r.getTourInfo().getDepDay()).isEqualTo(LegacyDate.fromLocalDate(depDay));
		assertThat(r.getTourInfo().getPlannedDay()).isEqualTo(LegacyDate.fromLocalDate(plannedDay));
		assertThat(r.getTourInfo().getTourAbs()).isEqualTo(tourAbs);
		assertThat(r.getTourInfo().getTourCode()).isEqualTo(tourCode);
		assertThat(r.getTourInfo().getTourDays()).isEqualTo(1);
		assertThat(r.getTourInfo().getTourName()).isEqualTo(tourName);
		assertThat(r.getTourInfo().getAccommodation().getAccomCode()).isEqualTo("0001");
		assertThat(r.getTourInfo().getAccommodation().getAccomName()).isEqualTo("TERASOLUNAホテル第一荘");
		assertThat(r.getTourInfo().getAccommodation().getAccomTel()).isEqualTo("018-123-4567");

		assertThat(r.getTourInfo().getDeparture().getDepCode()).isEqualTo("01");
		assertThat(r.getTourInfo().getDeparture().getDepName()).isEqualTo("北海道");
	}

	@Test
	void findByCustomer02() {
		String customerCode = "xxxxxxxx";

		// run
		List<Reserve> reservationList = reserveMapper.findAllWithTourInfoByCustomer(customerCode);

		assertThat(reservationList).isNotNull();
		assertThat(reservationList).isEmpty();
	}

}