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
import org.terasoluna.tourreservation.customer.Customer;

@Builder(style = BuilderStyle.STAGED)
public class ReserveTourInput {

	private String tourCode;

	private Integer adultCount;

	private Integer childCount;

	private String remarks;

	private Customer customer;

	public ReserveTourInput() {
	}

	ReserveTourInput(String tourCode, Integer adultCount, Integer childCount, String remarks, Customer customer) {
		this.tourCode = tourCode;
		this.adultCount = adultCount;
		this.childCount = childCount;
		this.remarks = remarks;
		this.customer = customer;
	}

	public String getTourCode() {
		return this.tourCode;
	}

	public Integer getAdultCount() {
		return this.adultCount;
	}

	public Integer getChildCount() {
		return this.childCount;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReserveTourInput that))
			return false;
		return Objects.equals(getTourCode(), that.getTourCode())
				&& Objects.equals(getAdultCount(), that.getAdultCount())
				&& Objects.equals(getChildCount(), that.getChildCount())
				&& Objects.equals(getRemarks(), that.getRemarks()) && Objects.equals(getCustomer(), that.getCustomer());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTourCode(), getAdultCount(), getChildCount(), getRemarks(), getCustomer());
	}

}
