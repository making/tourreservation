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

import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;

public class TourDetailOutput {

	private TourInfo tourInfo;

	private PriceCalculateOutput priceCalculateOutput;

	private Customer customer;

	public TourDetailOutput(TourInfo tourInfo, PriceCalculateOutput priceCalculateOutput, Customer customer) {
		this.tourInfo = tourInfo;
		this.priceCalculateOutput = priceCalculateOutput;
		this.customer = customer;
	}

	public TourDetailOutput() {
	}

	public TourInfo getTourInfo() {
		return this.tourInfo;
	}

	public PriceCalculateOutput getPriceCalculateOutput() {
		return this.priceCalculateOutput;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setTourInfo(TourInfo tourInfo) {
		this.tourInfo = tourInfo;
	}

	public void setPriceCalculateOutput(PriceCalculateOutput priceCalculateOutput) {
		this.priceCalculateOutput = priceCalculateOutput;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
