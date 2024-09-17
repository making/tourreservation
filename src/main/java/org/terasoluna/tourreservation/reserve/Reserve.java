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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terasoluna.tourreservation.reserve;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.jilt.Builder;
import org.terasoluna.tourreservation.customer.Customer;
import org.terasoluna.tourreservation.tour.TourInfo;

@Builder(toBuilder = "from")
public class Reserve implements Serializable {

	public static final String TRANSFERED = "1";

	public static final String NOT_TRANSFERED = "0";

	private static final long serialVersionUID = 1L;

	private String reserveNo;

	private Date reservedDay;

	private int adultCount;

	private int childCount;

	private String transfer;

	private int sumPrice;

	private String remarks;

	private TourInfo tourInfo;

	private Customer customer;

	public Reserve(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public Reserve() {
	}

	Reserve(String reserveNo, Date reservedDay, int adultCount, int childCount, String transfer, int sumPrice,
			String remarks, TourInfo tourInfo, Customer customer) {
		this.reserveNo = reserveNo;
		this.reservedDay = reservedDay;
		this.adultCount = adultCount;
		this.childCount = childCount;
		this.transfer = transfer;
		this.sumPrice = sumPrice;
		this.remarks = remarks;
		this.tourInfo = tourInfo;
		this.customer = customer;
	}

	public String getReserveNo() {
		return this.reserveNo;
	}

	public Date getReservedDay() {
		return this.reservedDay;
	}

	public int getAdultCount() {
		return this.adultCount;
	}

	public int getChildCount() {
		return this.childCount;
	}

	public String getTransfer() {
		return this.transfer;
	}

	public int getSumPrice() {
		return this.sumPrice;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public TourInfo getTourInfo() {
		return this.tourInfo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public void setReservedDay(Date reservedDay) {
		this.reservedDay = reservedDay;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setTourInfo(TourInfo tourInfo) {
		this.tourInfo = tourInfo;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Reserve reserve))
			return false;
		return getAdultCount() == reserve.getAdultCount() && getChildCount() == reserve.getChildCount()
				&& getSumPrice() == reserve.getSumPrice() && Objects.equals(getReserveNo(), reserve.getReserveNo())
				&& Objects.equals(getReservedDay(), reserve.getReservedDay())
				&& Objects.equals(getTransfer(), reserve.getTransfer())
				&& Objects.equals(getRemarks(), reserve.getRemarks())
				&& Objects.equals(getTourInfo(), reserve.getTourInfo())
				&& Objects.equals(getCustomer(), reserve.getCustomer());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReserveNo(), getReservedDay(), getAdultCount(), getChildCount(), getTransfer(),
				getSumPrice(), getRemarks(), getTourInfo(), getCustomer());
	}

	@Override
	public String toString() {
		return "Reserve{" + "reserveNo='" + reserveNo + '\'' + ", reservedDay=" + reservedDay + ", adultCount="
				+ adultCount + ", childCount=" + childCount + ", transfer='" + transfer + '\'' + ", sumPrice="
				+ sumPrice + ", remarks='" + remarks + '\'' + ", tourInfo=" + tourInfo + ", customer=" + customer + '}';
	}

}
