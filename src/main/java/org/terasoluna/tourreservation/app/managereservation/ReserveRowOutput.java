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
package org.terasoluna.tourreservation.app.managereservation;

import java.util.Objects;

import org.terasoluna.tourreservation.domain.model.Reserve;

public class ReserveRowOutput {

	private Reserve reserve;

	private Boolean limitExceeding;

	private String tourDays;

	public ReserveRowOutput(Reserve reserve, Boolean limitExceeding, String tourDays) {
		this.reserve = reserve;
		this.limitExceeding = limitExceeding;
		this.tourDays = tourDays;
	}

	public ReserveRowOutput() {
	}

	public Reserve getReserve() {
		return this.reserve;
	}

	public Boolean getLimitExceeding() {
		return this.limitExceeding;
	}

	public String getTourDays() {
		return this.tourDays;
	}

	public void setReserve(Reserve reserve) {
		this.reserve = reserve;
	}

	public void setLimitExceeding(Boolean limitExceeding) {
		this.limitExceeding = limitExceeding;
	}

	public void setTourDays(String tourDays) {
		this.tourDays = tourDays;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReserveRowOutput that))
			return false;
		return Objects.equals(getReserve(), that.getReserve())
				&& Objects.equals(getLimitExceeding(), that.getLimitExceeding())
				&& Objects.equals(getTourDays(), that.getTourDays());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReserve(), getLimitExceeding(), getTourDays());
	}

	@Override
	public String toString() {
		return "ReserveRowOutput{" + "reserve=" + reserve + ", limitExceeding=" + limitExceeding + ", tourDays='"
				+ tourDays + '\'' + '}';
	}

}
