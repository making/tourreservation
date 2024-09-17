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
package org.terasoluna.tourreservation.reserve.web;

import java.util.Date;
import java.util.Objects;

import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.reserve.Reserve;
import org.terasoluna.tourreservation.tour.PriceCalculateOutput;

public class ReservationDetailOutput {

	private PriceCalculateOutput priceCalculateOutput;

	private Reserve reserve;

	private Customer customer;

	private Date paymentTimeLimit;

	private Boolean limitExceeding;

	public ReservationDetailOutput(PriceCalculateOutput priceCalculateOutput, Reserve reserve, Customer customer,
			Date paymentTimeLimit, Boolean limitExceeding) {
		this.priceCalculateOutput = priceCalculateOutput;
		this.reserve = reserve;
		this.customer = customer;
		this.paymentTimeLimit = paymentTimeLimit;
		this.limitExceeding = limitExceeding;
	}

	public ReservationDetailOutput() {
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

	public Date getPaymentTimeLimit() {
		return this.paymentTimeLimit;
	}

	public Boolean getLimitExceeding() {
		return this.limitExceeding;
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

	public void setPaymentTimeLimit(Date paymentTimeLimit) {
		this.paymentTimeLimit = paymentTimeLimit;
	}

	public void setLimitExceeding(Boolean limitExceeding) {
		this.limitExceeding = limitExceeding;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReservationDetailOutput that))
			return false;
		return Objects.equals(getPriceCalculateOutput(), that.getPriceCalculateOutput())
				&& Objects.equals(getReserve(), that.getReserve()) && Objects.equals(getCustomer(), that.getCustomer())
				&& Objects.equals(getPaymentTimeLimit(), that.getPaymentTimeLimit())
				&& Objects.equals(getLimitExceeding(), that.getLimitExceeding());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPriceCalculateOutput(), getReserve(), getCustomer(), getPaymentTimeLimit(),
				getLimitExceeding());
	}

	@Override
	public String toString() {
		return "ReservationDetailOutput{" + "priceCalculateOutput=" + priceCalculateOutput + ", reserve=" + reserve
				+ ", customer=" + customer + ", paymentTimeLimit=" + paymentTimeLimit + ", limitExceeding="
				+ limitExceeding + '}';
	}

}
