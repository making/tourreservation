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
package org.terasoluna.tourreservation.tour;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class TourInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tourCode;

	private Date plannedDay;

	private String planNo;

	private String tourName;

	private int tourDays;

	private Date depDay;

	private int avaRecMax;

	private int basePrice;

	private String conductor;

	private String tourAbs;

	private Departure departure;

	private Arrival arrival;

	private Accommodation accommodation;

	public TourInfo(String tourCode) {
		this.tourCode = tourCode;
	}

	public TourInfo() {
	}

	public LocalDate getPaymentLimit() {
		LocalDate paymentLimit = LocalDate.ofInstant(this.getDepDay().toInstant(), ZoneId.systemDefault());
		return paymentLimit.minusDays(7);
	}

	public String getTourCode() {
		return this.tourCode;
	}

	public Date getPlannedDay() {
		return this.plannedDay;
	}

	public String getPlanNo() {
		return this.planNo;
	}

	public String getTourName() {
		return this.tourName;
	}

	public int getTourDays() {
		return this.tourDays;
	}

	public Date getDepDay() {
		return this.depDay;
	}

	public int getAvaRecMax() {
		return this.avaRecMax;
	}

	public int getBasePrice() {
		return this.basePrice;
	}

	public String getConductor() {
		return this.conductor;
	}

	public String getTourAbs() {
		return this.tourAbs;
	}

	public Departure getDeparture() {
		return this.departure;
	}

	public Arrival getArrival() {
		return this.arrival;
	}

	public Accommodation getAccommodation() {
		return this.accommodation;
	}

	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}

	public void setPlannedDay(Date plannedDay) {
		this.plannedDay = plannedDay;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public void setTourDays(int tourDays) {
		this.tourDays = tourDays;
	}

	public void setDepDay(Date depDay) {
		this.depDay = depDay;
	}

	public void setAvaRecMax(int avaRecMax) {
		this.avaRecMax = avaRecMax;
	}

	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public void setTourAbs(String tourAbs) {
		this.tourAbs = tourAbs;
	}

	public void setDeparture(Departure departure) {
		this.departure = departure;
	}

	public void setArrival(Arrival arrival) {
		this.arrival = arrival;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TourInfo tourInfo))
			return false;
		return getTourDays() == tourInfo.getTourDays() && getAvaRecMax() == tourInfo.getAvaRecMax()
				&& getBasePrice() == tourInfo.getBasePrice() && Objects.equals(getTourCode(), tourInfo.getTourCode())
				&& Objects.equals(getPlannedDay(), tourInfo.getPlannedDay())
				&& Objects.equals(getPlanNo(), tourInfo.getPlanNo())
				&& Objects.equals(getTourName(), tourInfo.getTourName())
				&& Objects.equals(getDepDay(), tourInfo.getDepDay())
				&& Objects.equals(getConductor(), tourInfo.getConductor())
				&& Objects.equals(getTourAbs(), tourInfo.getTourAbs())
				&& Objects.equals(getDeparture(), tourInfo.getDeparture())
				&& Objects.equals(getArrival(), tourInfo.getArrival())
				&& Objects.equals(getAccommodation(), tourInfo.getAccommodation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTourCode(), getPlannedDay(), getPlanNo(), getTourName(), getTourDays(), getDepDay(),
				getAvaRecMax(), getBasePrice(), getConductor(), getTourAbs(), getDeparture(), getArrival(),
				getAccommodation());
	}

	@Override
	public String toString() {
		return "TourInfo{" + "tourCode='" + tourCode + '\'' + ", plannedDay=" + plannedDay + ", planNo='" + planNo
				+ '\'' + ", tourName='" + tourName + '\'' + ", tourDays=" + tourDays + ", depDay=" + depDay
				+ ", avaRecMax=" + avaRecMax + ", basePrice=" + basePrice + ", conductor='" + conductor + '\''
				+ ", tourAbs='" + tourAbs + '\'' + ", departure=" + departure + ", arrival=" + arrival
				+ ", accommodation=" + accommodation + '}';
	}

}
