package org.terasoluna.tourreservation.tour.web;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasoluna.tourreservation.tour.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.tour.TourInfoService;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.terasoluna.tourreservation.tour.web.SearchTourControllerBuilder.searchTourController;

class SearchTourControllerTest {

	MockMvc mockMvc;

	TourInfoService tourInfoService;

	SearchTourFormDateValidator validator;

	Instant now = Instant.now();

	@BeforeEach
	void setUp() {
		// other members instantiation and assignment
		tourInfoService = mock(TourInfoService.class);
		validator = new SearchTourFormDateValidator();
		// instantiate the controller to test
		SearchTourController searchTourController = searchTourController().tourInfoService(tourInfoService)
			.validator(validator)
			.clock(Clock.fixed(now, ZoneId.systemDefault()))
			.build();

		// Assign custom method argument resolver and build
		// This is needed to resolve Pageable method argument
		mockMvc = MockMvcBuilders.standaloneSetup(searchTourController)
			.setCustomArgumentResolvers(new HandlerMethodArgumentResolver() {
				@Override
				public boolean supportsParameter(MethodParameter parameter) {
					return parameter.getParameterType().equals(Pageable.class);
				}

				@Override
				public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
					return PageRequest.of(0, 50);
				}
			})
			.build();
	}

	/**
	 * This method tests the working of method annotated by @ModelAttribute. <br>
	 * </p>
	 */
	@Test
	void searchForm() throws Exception {

		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours").param("form", "");

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("searchtour/searchForm"));

		// Check the default data set in setupForm method
		// this will test the @ModelAttribute annotation

		LocalDate nextWeekDate = now.atZone(ZoneId.systemDefault()).toLocalDate().plusWeeks(1);

		results.andExpect(model().attribute("searchTourForm", hasProperty("depYear", is(nextWeekDate.getYear()))));
		results
			.andExpect(model().attribute("searchTourForm", hasProperty("depMonth", is(nextWeekDate.getMonthValue()))));
		results.andExpect(model().attribute("searchTourForm", hasProperty("depDay", is(nextWeekDate.getDayOfMonth()))));

	}

	/**
	 * This method tests the success case of Search operation. <br>
	 * </p>
	 */
	@Test
	void searchSuccess() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours");

		// Set mock behavior for service method
		when(tourInfoService.searchTour(any(TourInfoSearchCriteria.class), any(Pageable.class)))
			.thenReturn(new PageImpl<>(new ArrayList<>()));

		LocalDate nextWeekDate = now.atZone(ZoneId.systemDefault()).toLocalDate().plusWeeks(1);

		getRequest.param("depYear", String.valueOf(nextWeekDate.getYear()));
		getRequest.param("depMonth", String.valueOf(nextWeekDate.getMonthValue()));
		getRequest.param("depDay", String.valueOf(nextWeekDate.getDayOfMonth()));
		getRequest.param("tourDays", "2");
		getRequest.param("adultCount", "2");
		getRequest.param("childCount", "2");
		getRequest.param("basePrice", "2");
		getRequest.param("depCode", "01");
		getRequest.param("arrCode", "02");

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("searchtour/searchForm"));
		results.andExpect(model().hasNoErrors());
	}

	/**
	 * This method tests the failure case of Search operation due to validation error.
	 * This test case will confirm working of @InitiBinder and @Validated annotations <br>
	 * </p>
	 */
	@Test
	void searchFail() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/tours");

		// Set mock behavior for service method
		when(tourInfoService.searchTour(any(TourInfoSearchCriteria.class), any(Pageable.class)))
			.thenReturn(new PageImpl<>(new ArrayList<>()));

		// Set invalid date such that custom date validator will fail
		getRequest.param("depYear", "2000");
		getRequest.param("depMonth", "2");
		getRequest.param("depDay", "30");

		ResultActions results = mockMvc.perform(getRequest);
		results.andExpect(status().isOk());
		results.andExpect(view().name("searchtour/searchForm"));
		results.andExpect(model().hasErrors());
		results.andExpect(model().attributeErrorCount("searchTourForm", 7));
	}

}