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
package org.terasoluna.tourreservation.reserve;

import java.util.Objects;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

@Builder(style = BuilderStyle.STAGED)
public class ReservationUpdateInput {

	private String reserveNo;

	private Integer adultCount;

	private Integer childCount;

	ReservationUpdateInput(String reserveNo, Integer adultCount, Integer childCount) {
		this.reserveNo = reserveNo;
		this.adultCount = adultCount;
		this.childCount = childCount;
	}

	public ReservationUpdateInput() {
	}

	public String getReserveNo() {
		return this.reserveNo;
	}

	public Integer getAdultCount() {
		return this.adultCount;
	}

	public Integer getChildCount() {
		return this.childCount;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReservationUpdateInput that))
			return false;
		return Objects.equals(getReserveNo(), that.getReserveNo())
				&& Objects.equals(getAdultCount(), that.getAdultCount())
				&& Objects.equals(getChildCount(), that.getChildCount());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReserveNo(), getAdultCount(), getChildCount());
	}

}
