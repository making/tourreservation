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
package org.terasoluna.tourreservation.app.reservetour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handle request of tour searching.
 */
@Controller
@RequestMapping(value = "tours")
public class ReserveTourController {

	private static final Logger log = LoggerFactory.getLogger(ReserveTourController.class);

	private final ReserveTourHelper reserveTourHelper;

	public ReserveTourController(ReserveTourHelper reserveTourHelper) {
		this.reserveTourHelper = reserveTourHelper;
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public ReserveTourForm setUpReserveTourForm() {
		return new ReserveTourForm();
	}

	/**
	 * Shows the detail view of a tour
	 * @param form
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "{tourCode}", "{tourCode}/reserve" }, params = "form")
	public String reserveForm(@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("tourCode") String tourCode, ReserveTourForm form, Model model) {
		log.debug("retrieve tour {}", tourCode);

		TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails, tourCode, form);

		model.addAttribute("output", output);

		return "reservetour/reserveForm";
	}

	/**
	 * Shows the confirmation page before reserving a tour
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping(value = "{tourCode}/reserve", params = "confirm")
	public String confirm(@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("tourCode") String tourCode, @Validated ReserveTourForm form, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return reserveForm(userDetails, tourCode, form, model);
		}
		log.debug("confirm the reservation details for the following tour {}", tourCode);

		TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails, tourCode, form);
		model.addAttribute("output", output);

		return "reservetour/reserveConfirm";
	}

	/**
	 * reserves a tour
	 * @param form
	 * @param bindingResult
	 * @param redirectAttr
	 * @return
	 */
	@PostMapping("{tourCode}/reserve")
	public String reserve(@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("tourCode") String tourCode, @Validated ReserveTourForm form, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttr) {
		log.debug("reserve tour {}", tourCode);

		if (bindingResult.hasErrors()) {
			return reserveForm(userDetails, tourCode, form, model);
		}

		try {
			ReserveTourOutput output = reserveTourHelper.reserve(userDetails, tourCode, form);
			redirectAttr.addFlashAttribute("output", output);
		}
		catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return reserveForm(userDetails, tourCode, form, model);
		}

		return "redirect:/tours/{tourCode}/reserve?complete";
	}

	/**
	 * redirects to the reservation completion page
	 * @return
	 */
	@GetMapping(value = "{tourCode}/reserve", params = "complete")
	public String reserveComplete() {
		return "reservetour/reserveComplete";
	}

	@PostMapping(value = "{tourCode}/reserve", params = "redo")
	public String reserveRedo(@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("tourCode") String tourCode, ReserveTourForm form, Model model) {
		return reserveForm(userDetails, tourCode, form, model);
	}

}
