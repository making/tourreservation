package org.terasoluna.tourreservation.reserve;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.common.ResultMessages;
import org.terasoluna.tourreservation.message.BusinessMessageId;
import org.terasoluna.tourreservation.tour.Accommodation;
import org.terasoluna.tourreservation.tour.Arrival;
import org.terasoluna.tourreservation.tour.Departure;
import org.terasoluna.tourreservation.tour.PriceCalculateOutput;
import org.terasoluna.tourreservation.tour.PriceCalculateSharedService;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.TourInfoSharedService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.terasoluna.tourreservation.reserve.ReserveServiceBuilder.reserveService;

class ReserveServiceTest {

	ReserveService reserveService;

	ReserveMapper reserveMapper;

	TourInfoSharedService tourInfoSharedService;

	PriceCalculateSharedService priceCalculateSerivce;

	Clock clock;

	Instant now;

	@BeforeEach
	void setUp() {
		reserveMapper = mock(ReserveMapper.class);
		tourInfoSharedService = mock(TourInfoSharedService.class);
		priceCalculateSerivce = mock(PriceCalculateSharedService.class);
		now = Instant.now();
		clock = Clock.fixed(now, ZoneId.systemDefault());
		reserveService = reserveService().reserveMapper(reserveMapper)
			.authorizedReserveSharedService(new AuthorizedReserveSharedService(reserveMapper))
			.tourInfoSharedService(tourInfoSharedService)
			.priceCalculateService(priceCalculateSerivce)
			.clock(clock)
			.build();
	}

	@Test
	void findOne01() {
		Reserve reserve = new Reserve();
		TourInfo tourInfo = new TourInfo();
		tourInfo.setTourCode("XXXXXXXXXX");
		reserve.setTourInfo(tourInfo);
		when(reserveMapper.findById("foo")).thenReturn(Optional.of(reserve));
		when(tourInfoSharedService.findOneWithDetails("XXXXXXXXXX")).thenReturn(tourInfo);

		Reserve result = reserveService.findOneWithTourInfo("foo");
		assertThat(result).isEqualTo(reserve);
	}

	@Test
	void findOne02() {
		when(reserveMapper.findById("foo")).thenReturn(Optional.empty());
		when(tourInfoSharedService.findOneWithDetails(null)).thenReturn(null);

		Reserve result = reserveService.findOneWithTourInfo("foo");
		assertThat(result).isNull();
	}

	@Test
	void findByCustomerCode01() {
		TourInfo tour1 = new TourInfo("01");
		TourInfo tour2 = new TourInfo("02");
		Reserve reserve1 = new Reserve("001");
		Reserve reserve2 = new Reserve("002");
		reserve1.setTourInfo(tour1);
		reserve2.setTourInfo(tour2);
		tour1.setTourDays(2);
		tour2.setTourDays(4);
		List<Reserve> reserves = Arrays.asList(reserve1, reserve2);

		when(reserveMapper.findAllWithTourInfoByCustomer("xxxx")).thenReturn(reserves);
		when(tourInfoSharedService.isOverPaymentLimit(tour1)).thenReturn(false);
		when(tourInfoSharedService.isOverPaymentLimit(tour2)).thenReturn(true);

		List<Reserve> result = reserveService.findAllWithTourInfoByCustomer("xxxx");
		assertThat(result).isNotNull().hasSize(2);
		assertThat(result.getFirst()).isEqualTo(reserve1);
		assertThat(result.get(1)).isEqualTo(reserve2);
	}

	@Test
	void findByCustomerCode03() {
		when(reserveMapper.findAllWithTourInfoByCustomer("xxxx")).thenReturn(new ArrayList<Reserve>());
		List<Reserve> result = reserveService.findAllWithTourInfoByCustomer("xxxx");
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
	}

	@Test
	void reserve01() {
		// normal case

		TourInfo tour = new TourInfo("01");
		// limit
		tour.setDepDay(LegacyDate.fromLocalDate(LocalDate.now(clock).plusDays(7)));
		tour.setTourDays(2);
		tour.setAvaRecMax(10);
		tour.setBasePrice(10000);
		tour.setAccommodation(new Accommodation());
		tour.setDeparture(new Departure());
		tour.setArrival(new Arrival());
		when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
		when(tourInfoSharedService.findOneWithDetailsForUpdate("01")).thenReturn(tour);
		when(reserveMapper.countReservedPersonSumByTourInfo(tour.getTourCode())).thenReturn(7L); // 1+2+7
		// <=
		// 10

		ReserveTourInput input = new ReserveTourInput();
		input.setAdultCount(1);
		input.setChildCount(2);
		input.setTourCode("01");
		input.setRemarks("aa");

		PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
		priceCalculateOutput.setSumPrice(100000);
		priceCalculateOutput.setAdultCount(1);
		priceCalculateOutput.setChildCount(2);
		when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(priceCalculateOutput);

		// normal
		ReserveTourOutput output = reserveService.reserve(input);

		ArgumentCaptor<Reserve> capture = ArgumentCaptor.forClass(Reserve.class);
		verify(reserveMapper, atLeast(1)).insert(capture.capture());

		Reserve r = capture.getValue();
		assertThat(output.getReserve()).isEqualTo(r);
		assertThat(r.getAdultCount()).isEqualTo(1);
		assertThat(r.getChildCount()).isEqualTo(2);
		assertThat(r.getSumPrice()).isEqualTo(100000);
		assertThat(r.getTourInfo().getTourCode()).isEqualTo("01");
		assertThat(r.getRemarks()).isEqualTo("aa");
		assertThat(r.getTransfer()).isEqualTo(Reserve.NOT_TRANSFERED);
		assertThat(r.getReservedDay())
			.isEqualTo(Date.from(now.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toInstant()));
	}

	@Test
	void reserve02() {
		assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> {
			// in case of over payment limit
			TourInfo tour = new TourInfo("01");
			tour.setTourDays(2);
			tour.setAvaRecMax(10);
			tour.setBasePrice(10000);
			tour.setAccommodation(new Accommodation());
			tour.setDeparture(new Departure());
			tour.setArrival(new Arrival());
			when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(true); // !!today
			// is
			// over
			// limit
			tour.setTourDays(2);
			tour.setAvaRecMax(10);
			when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
			when(tourInfoSharedService.findOneWithDetailsForUpdate("01")).thenReturn(tour);
			when(reserveMapper.countReservedPersonSumByTourInfo(tour.getTourCode())).thenReturn(7L); // 1+2+7
			// <=
			// 10

			ReserveTourInput input = new ReserveTourInput();
			input.setAdultCount(1);
			input.setChildCount(2);
			input.setTourCode("01");
			input.setRemarks("aa");

			PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
			priceCalculateOutput.setSumPrice(100000);
			priceCalculateOutput.setAdultCount(1);
			priceCalculateOutput.setChildCount(2);
			when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(priceCalculateOutput);

			try {
				reserveService.reserve(input);
				fail("error route");
			}
			catch (BusinessException e) {
				ResultMessages messages = e.getResultMessages();
				assertThat(messages.isNotEmpty()).isEqualTo(true);
				assertThat(messages.type()).isEqualTo(ResultMessages.Type.ERROR);
				assertThat(messages.messageCodes()).hasSize(1);
				assertThat(messages.messageCodes().getFirst()).isEqualTo(BusinessMessageId.E_TR_0004);
				throw e;
			}
		});
	}

	@Test
	void reserve03() {
		assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> {
			// in case of no vacancy
			TourInfo tour = new TourInfo("01");
			tour.setTourDays(2);
			tour.setAvaRecMax(10);
			tour.setBasePrice(10000);
			tour.setAccommodation(new Accommodation());
			tour.setDeparture(new Departure());
			tour.setArrival(new Arrival());
			tour.setTourDays(2);
			tour.setAvaRecMax(10);
			when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
			when(tourInfoSharedService.findOneWithDetailsForUpdate("01")).thenReturn(tour);
			when(reserveMapper.countReservedPersonSumByTourInfo(tour.getTourCode())).thenReturn(8L); // !!1+2+8
			// >
			// 10

			ReserveTourInput input = new ReserveTourInput();
			input.setAdultCount(1);
			input.setChildCount(2);
			input.setTourCode("01");
			input.setRemarks("aa");

			PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
			priceCalculateOutput.setSumPrice(100000);
			priceCalculateOutput.setAdultCount(1);
			priceCalculateOutput.setChildCount(2);
			when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(priceCalculateOutput);

			try {
				reserveService.reserve(input);
				fail("error route");
			}
			catch (BusinessException e) {
				ResultMessages messages = e.getResultMessages();
				assertThat(messages.isNotEmpty()).isEqualTo(true);
				assertThat(messages.type()).isEqualTo(ResultMessages.Type.ERROR);
				assertThat(messages.messageCodes()).hasSize(1);
				assertThat(messages.messageCodes().getFirst()).isEqualTo(BusinessMessageId.E_TR_0005);
				throw e;
			}
		});
	}

	@Test
	void cancel01() {
		Reserve reserve = new Reserve("001");
		reserve.setAdultCount(1);
		reserve.setChildCount(2);

		TourInfo tour = new TourInfo("01");
		reserve.setTourInfo(tour);
		reserve.setTransfer(Reserve.NOT_TRANSFERED);

		when(reserveMapper.findById("001")).thenReturn(Optional.of(reserve));
		when(reserveMapper.findOneForUpdate("001")).thenReturn(reserve);
		when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
		when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within
		// limit

		reserveService.cancel("001");

		ArgumentCaptor<String> argOfDelete = ArgumentCaptor.forClass(String.class);
		verify(reserveMapper, times(1)).deleteById(argOfDelete.capture());

		assertThat(argOfDelete.getValue()).isEqualTo("001");
	}

	@Test
	void cancel02() {
		assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> {
			Reserve reserve = new Reserve("001");
			reserve.setAdultCount(1);
			reserve.setChildCount(2);

			TourInfo tour = new TourInfo("01");
			reserve.setTourInfo(tour);
			reserve.setTransfer(Reserve.TRANSFERED); // !!!TRANSFERED

			when(reserveMapper.findById("001")).thenReturn(Optional.of(reserve));
			when(reserveMapper.findOneForUpdate("001")).thenReturn(reserve);
			when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
			when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within
			// limit

			try {
				reserveService.cancel("001");
				fail("error route");
			}
			catch (BusinessException e) {
				ResultMessages messages = e.getResultMessages();
				assertThat(messages.isNotEmpty()).isEqualTo(true);
				assertThat(messages.type()).isEqualTo(ResultMessages.Type.ERROR);
				assertThat(messages.messageCodes()).hasSize(1);
				assertThat(messages.messageCodes().getFirst()).isEqualTo(BusinessMessageId.E_TR_0001);
				throw e;
			}
		});
	}

	@Test
	void cancel03() {
		assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> {
			Reserve reserve = new Reserve("001");
			reserve.setAdultCount(1);
			reserve.setChildCount(2);

			TourInfo tour = new TourInfo("01");
			reserve.setTourInfo(tour);
			reserve.setTransfer(Reserve.NOT_TRANSFERED);

			when(reserveMapper.findById("001")).thenReturn(Optional.of(reserve));
			when(reserveMapper.findOneForUpdate("001")).thenReturn(reserve);
			when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
			when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(true); // !!!over
			// limit

			try {
				reserveService.cancel("001");
				fail("error route");
			}
			catch (BusinessException e) {
				ResultMessages messages = e.getResultMessages();
				assertThat(messages.isNotEmpty()).isEqualTo(true);
				assertThat(messages.type()).isEqualTo(ResultMessages.Type.ERROR);
				assertThat(messages.messageCodes()).hasSize(1);
				assertThat(messages.messageCodes().getFirst()).isEqualTo(BusinessMessageId.E_TR_0002);
				throw e;
			}
		});
	}

	@Test
	void cancel04() {
		assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> {
			Reserve reserve = new Reserve("001");
			reserve.setAdultCount(1);
			reserve.setChildCount(2);

			TourInfo tour = new TourInfo("01");
			reserve.setTourInfo(tour);
			reserve.setTransfer(Reserve.NOT_TRANSFERED);

			when(reserveMapper.findById("001")).thenReturn(Optional.of(reserve)).thenReturn(Optional.empty()); // !!!return
			// null
			// for
			// second
			// time
			when(reserveMapper.findOneForUpdate("001")).thenReturn(null); // return
			// null
			when(tourInfoSharedService.findOneWithDetails("01")).thenReturn(tour);
			when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within
			// limit

			try {
				reserveService.cancel("001");
				fail("error route");
			}
			catch (BusinessException e) {
				ResultMessages messages = e.getResultMessages();
				assertThat(messages.isNotEmpty()).isEqualTo(true);
				assertThat(messages.type()).isEqualTo(ResultMessages.Type.ERROR);
				assertThat(messages.messageCodes()).hasSize(1);
				assertThat(messages.messageCodes().getFirst()).isEqualTo(BusinessMessageId.E_TR_0003);
				throw e;
			}
		});
	}

	@Test
	void update01() {
		PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
		priceCalculateOutput.setSumPrice(100000);
		priceCalculateOutput.setAdultCount(1);
		priceCalculateOutput.setChildCount(2);
		when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(priceCalculateOutput);

		ReservationUpdateInput input = new ReservationUpdateInput();
		input.setReserveNo("foo");
		input.setAdultCount(1);
		input.setChildCount(2);

		Reserve reserve = new Reserve();
		TourInfo tour = new TourInfo("aaa");
		tour.setArrival(new Arrival());
		tour.setDeparture(new Departure());
		tour.setBasePrice(10000);
		tour.setDepDay(LegacyDate.fromLocalDate(LocalDate.now(clock).plusDays(7)));
		reserve.setTourInfo(tour);

		when(reserveMapper.findById("foo")).thenReturn(Optional.of(reserve));
		when(tourInfoSharedService.findOneWithDetails("aaa")).thenReturn(tour);
		// run
		ReservationUpdateOutput output = reserveService.update(input);
		assertThat(output).isEqualTo(output);
	}

}