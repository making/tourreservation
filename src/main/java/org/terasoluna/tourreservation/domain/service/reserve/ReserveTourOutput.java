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

import java.time.LocalDate;
import java.util.Objects;

import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;

public class ReserveTourOutput {

	private PriceCalculateOutput priceCalculateOutput;

	private Reserve reserve;

	private Customer customer;

	private TourInfo tourInfo;

	private LocalDate paymentTimeLimit;

	public ReserveTourOutput() {
	}

	public PriceCalculateOutput getPriceCalculateOutput() {
		return this.priceCalculateOutput;
	}

	public Reserve getReserve() {
		return this.reserve;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public TourInfo getTourInfo() {
		return this.tourInfo;
	}

	public LocalDate getPaymentTimeLimit() {
		return this.paymentTimeLimit;
	}

	public void setPriceCalculateOutput(PriceCalculateOutput priceCalculateOutput) {
		this.priceCalculateOutput = priceCalculateOutput;
	}

	public void setReserve(Reserve reserve) {
		this.reserve = reserve;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setTourInfo(TourInfo tourInfo) {
		this.tourInfo = tourInfo;
	}

	public void setPaymentTimeLimit(LocalDate paymentTimeLimit) {
		this.paymentTimeLimit = paymentTimeLimit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReserveTourOutput that))
			return false;
		return Objects.equals(getPriceCalculateOutput(), that.getPriceCalculateOutput())
				&& Objects.equals(getReserve(), that.getReserve()) && Objects.equals(getCustomer(), that.getCustomer())
				&& Objects.equals(getTourInfo(), that.getTourInfo())
				&& Objects.equals(getPaymentTimeLimit(), that.getPaymentTimeLimit());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPriceCalculateOutput(), getReserve(), getCustomer(), getTourInfo(),
				getPaymentTimeLimit());
	}

}
