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
package org.terasoluna.tourreservation.tour.web;

import java.time.Clock;
import java.time.LocalDate;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.tourreservation.common.LegacyDate;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.tour.TourInfoSearchCriteriaBuilder;
import org.terasoluna.tourreservation.tour.TourInfoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Handle request of tour searching.
 */
@Controller
@RequestMapping(value = "tours")
@SessionAttributes(types = SearchTourForm.class)
@Builder(style = BuilderStyle.STAGED)
public class SearchTourController {

	private static final Logger log = LoggerFactory.getLogger(SearchTourController.class);

	private final TourInfoService tourInfoService;

	private final SearchTourFormDateValidator validator;

	private final Clock clock;

	public SearchTourController(TourInfoService tourInfoService, SearchTourFormDateValidator validator, Clock clock) {
		this.tourInfoService = tourInfoService;
		this.validator = validator;
		this.clock = clock;
	}

	@InitBinder("searchTourForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(this.validator);
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public SearchTourForm setUpSearchTourForm() {
		SearchTourForm searchTourForm = new SearchTourForm();
		LocalDate today = LocalDate.now(this.clock);
		LocalDate nextWeek = today.plusWeeks(1);
		searchTourForm.setDepYear(nextWeek.getYear());
		searchTourForm.setDepMonth(nextWeek.getMonthValue());
		searchTourForm.setDepDay(nextWeek.getDayOfMonth());
		log.debug("populate form {}", searchTourForm);
		return searchTourForm;
	}

	/**
	 * clear session attributes and redirect to the search input view
	 * @return redirect view for show the search input
	 */
	@GetMapping(params = "initForm")
	public String searchInitForm(SessionStatus status) {
		status.setComplete();
		return "redirect:/tours?form";
	}

	/**
	 * shows the search input view
	 * @return search input view
	 */
	@GetMapping(params = "form")
	public String searchForm() {
		return "searchtour/searchForm";
	}

	/**
	 * Searches the tours based on user input. Search result is shown in the form of a
	 * List of TourInfo domain objects User input is mapped to form backed bean
	 * SearchTourForm
	 * @param searchTourForm
	 * @param result
	 * @param model
	 * @param pageable
	 * @return
	 */
	@GetMapping
	public String search(@Validated SearchTourForm searchTourForm, BindingResult result, Model model,
			@PageableDefault Pageable pageable) {
		if (result.hasErrors()) {
			return "searchtour/searchForm";
		}

		if (log.isDebugEnabled()) {
			log.debug("pageable={}", pageable);
		}

		TourInfoSearchCriteria criteria = TourInfoSearchCriteriaBuilder.tourInfoSearchCriteria()
			.depDate(LegacyDate.fromLocalDate(LocalDate.of(searchTourForm.getDepYear(), searchTourForm.getDepMonth(),
					searchTourForm.getDepDay())))
			.tourDays(searchTourForm.getTourDays())
			.depCode(searchTourForm.getDepCode())
			.arrCode(searchTourForm.getArrCode())
			.adultCount(searchTourForm.getAdultCount())
			.childCount(searchTourForm.getChildCount())
			.basePrice(searchTourForm.getBasePrice())
			.build();

		LocalDate depDate = LocalDate.of(searchTourForm.getDepYear(), searchTourForm.getDepMonth(),
				searchTourForm.getDepDay());
		criteria.setDepDate(LegacyDate.fromLocalDate(depDate));

		Page<TourInfo> page = this.tourInfoService.searchTour(criteria, pageable);
		model.addAttribute("page", page);
		return "searchtour/searchForm";
	}

}
