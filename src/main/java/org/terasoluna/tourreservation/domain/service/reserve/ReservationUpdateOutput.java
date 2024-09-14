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

import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;

public class ReservationUpdateOutput {

	private PriceCalculateOutput priceCalculateOutput;

	private Reserve reserve;

	private LocalDate paymentTimeLimit;

	public ReservationUpdateOutput() {
	}

	public PriceCalculateOutput getPriceCalculateOutput() {
		return this.priceCalculateOutput;
	}

	public Reserve getReserve() {
		return this.reserve;
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

	public void setPaymentTimeLimit(LocalDate paymentTimeLimit) {
		this.paymentTimeLimit = paymentTimeLimit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReservationUpdateOutput that))
			return false;
		return Objects.equals(getPriceCalculateOutput(), that.getPriceCalculateOutput())
				&& Objects.equals(getReserve(), that.getReserve())
				&& Objects.equals(getPaymentTimeLimit(), that.getPaymentTimeLimit());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPriceCalculateOutput(), getReserve(), getPaymentTimeLimit());
	}

}
