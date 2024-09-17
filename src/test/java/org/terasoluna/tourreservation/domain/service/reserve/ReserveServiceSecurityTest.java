/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.domain.service.reserve;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.TestcontainersConfiguration;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.domain.mapper.reserve.ReserveMapper;
import org.terasoluna.tourreservation.domain.model.Arrival;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Departure;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.mapper.tourinfo.TourInfoMapper;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Testcontainers(disabledWithoutDocker = true)
@Import(TestcontainersConfiguration.class)
class ReserveServiceSecurityTest {

	private static final String CUSTOMER_A = "C0000001";

	private static final String CUSTOMER_B = "C0000002";

	// Mock repository is define at the default bean definition file for this test case
	// class.
	@MockBean
	ReserveMapper mockReserveMapper;

	@MockBean
	TourInfoMapper tourInfoMapper;

	@Autowired
	ReserveService reserveService;

	@Autowired
	Clock clock;

	@BeforeEach
	void resetMocks() {
		reset(mockReserveMapper, tourInfoMapper);
	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void findOneForOwnerReservation() {

		// setup
		{
			setUpMockReserveRepository(CUSTOMER_A, "R000000001");
		}

		// test
		Reserve reserve;
		{
			reserve = reserveService.findOneWithTourInfo("R000000001");
		}

		// assert
		{
			assertThat(reserve.getReserveNo()).isEqualTo("R000000001");
			assertThat(reserve.getCustomer().getCustomerCode()).isEqualTo(CUSTOMER_A);
		}

	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void findOneForNotFound() {

		// setup
		{
			// Nothing
			// ReserveRepository#findOne return null.
		}

		// test
		Reserve reserve;
		{
			reserve = reserveService.findOneWithTourInfo("R000000001");
		}

		// assert
		{
			assertThat(reserve).isNull();
		}

	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void findOneForOtherOwnerReservation() {
		assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(() -> {

			// setup
			{
				setUpMockReserveRepository(CUSTOMER_B, "R000000001");
			}

			// test
			{
				reserveService.findOneWithTourInfo("R000000001");
			}

		});

	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void updateForOwnerReservation() {

		// setup
		{
			setUpMockReserveRepository(CUSTOMER_A, "R000000001");
		}

		// test
		ReservationUpdateOutput output;
		{
			ReservationUpdateInput input = new ReservationUpdateInput();
			input.setReserveNo("R000000001");
			input.setAdultCount(1);
			input.setChildCount(1);
			output = reserveService.update(input);
		}

		// assert
		{
			verify(mockReserveMapper, times(1)).update(any(Reserve.class));
			assertThat(output.getReserve().getReserveNo()).isEqualTo("R000000001");
			assertThat(output.getReserve().getCustomer().getCustomerCode()).isEqualTo(CUSTOMER_A);
		}

	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void updateForOtherOwnerReservation() {

		// setup
		{
			setUpMockReserveRepository(CUSTOMER_B, "R000000001");
		}

		// test
		{
			ReservationUpdateInput input = new ReservationUpdateInput();
			input.setAdultCount(1);
			input.setChildCount(1);
			input.setReserveNo("R000000001");
			try {
				reserveService.update(input);
				fail("");
			}
			catch (AccessDeniedException e) {
				// as expected
			}
		}

		// assert
		{
			verify(mockReserveMapper, never()).update(any(Reserve.class));
		}
	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void cancelForOwnerReservation() {

		// setup
		{
			setUpMockReserveRepository(CUSTOMER_A, "R000000001");
		}

		// test
		{
			reserveService.cancel("R000000001");
		}

		// assert
		{
			verify(mockReserveMapper, times(1)).deleteById("R000000001");
		}

	}

	@Test
	@WithMockCustomer(customerCode = CUSTOMER_A)
	void cancelForOtherOwnerReservation() {

		// setup
		{
			setUpMockReserveRepository(CUSTOMER_B, "R000000001");
		}

		// test
		{
			try {
				reserveService.cancel("R000000001");
				fail("");
			}
			catch (AccessDeniedException e) {
				// as expected
			}
		}

		// assert
		{
			verify(mockReserveMapper, never()).deleteById("R000000001");
		}

	}

	/**
	 * Set up return object of {@link ReserveMapper}'s method.
	 * <p>
	 * This method set up return object of following methods.
	 * <ul>
	 * <li>{@link ReserveMapper#findById}</li>
	 * <li>{@link ReserveMapper#findOneForUpdate}</li>
	 * </ul>
	 * @param customerCode customer code of reservation owner
	 * @param reserveNo reserve number of reservation
	 */
	private void setUpMockReserveRepository(String customerCode, String reserveNo) {
		Reserve reserve = new Reserve(reserveNo);
		reserve.setCustomer(new Customer(customerCode));
		reserve.setTourInfo(new TourInfo("01"));

		when(mockReserveMapper.findById(reserveNo)).thenReturn(Optional.of(reserve));
		when(mockReserveMapper.findOneForUpdate(reserveNo)).thenReturn(reserve);

		TourInfo tourInfo = new TourInfo("01");
		tourInfo.setDepDay(LegacyDate.fromLocalDate(LocalDate.now(clock).plusDays(8)));
		tourInfo.setDeparture(new Departure());
		tourInfo.setArrival(new Arrival());
		reserve.setTourInfo(tourInfo);

		when(tourInfoMapper.findOneWithDetails(tourInfo.getTourCode())).thenReturn(tourInfo);
	}

	@WithSecurityContext(factory = WithMockCustomerSecurityContextFactory.class)
	@Retention(RetentionPolicy.RUNTIME)
	@interface WithMockCustomer {

		String customerCode();

	}

	static class WithMockCustomerSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomer> {

		@Override
		public SecurityContext createSecurityContext(WithMockCustomer mockCustomer) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			ReservationUserDetails userDetails = new ReservationUserDetails(new Customer(mockCustomer.customerCode()));
			Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "dummyPassword",
					userDetails.getAuthorities());
			context.setAuthentication(auth);
			return context;
		}

	}

}
