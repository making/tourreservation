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
package org.terasoluna.tourreservation.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.jilt.Opt;

@Builder(style = BuilderStyle.STAGED)
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Opt
	private String customerCode;

	private String customerName;

	private String customerKana;

	private String customerPass;

	private Date customerBirth;

	private String customerJob;

	private String customerMail;

	private String customerTel;

	private String customerPost;

	private String customerAdd;

	public Customer(String customerCode) {
		this.customerCode = customerCode;
	}

	public Customer() {
	}

	Customer(String customerCode, String customerName, String customerKana, String customerPass, Date customerBirth,
			String customerJob, String customerMail, String customerTel, String customerPost, String customerAdd) {
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerKana = customerKana;
		this.customerPass = customerPass;
		this.customerBirth = customerBirth;
		this.customerJob = customerJob;
		this.customerMail = customerMail;
		this.customerTel = customerTel;
		this.customerPost = customerPost;
		this.customerAdd = customerAdd;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getCustomerKana() {
		return this.customerKana;
	}

	public String getCustomerPass() {
		return this.customerPass;
	}

	public Date getCustomerBirth() {
		return this.customerBirth;
	}

	public String getCustomerJob() {
		return this.customerJob;
	}

	public String getCustomerMail() {
		return this.customerMail;
	}

	public String getCustomerTel() {
		return this.customerTel;
	}

	public String getCustomerPost() {
		return this.customerPost;
	}

	public String getCustomerAdd() {
		return this.customerAdd;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerKana(String customerKana) {
		this.customerKana = customerKana;
	}

	public void setCustomerPass(String customerPass) {
		this.customerPass = customerPass;
	}

	public void setCustomerBirth(Date customerBirth) {
		this.customerBirth = customerBirth;
	}

	public void setCustomerJob(String customerJob) {
		this.customerJob = customerJob;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public void setCustomerPost(String customerPost) {
		this.customerPost = customerPost;
	}

	public void setCustomerAdd(String customerAdd) {
		this.customerAdd = customerAdd;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Customer customer))
			return false;
		return Objects.equals(getCustomerCode(), customer.getCustomerCode())
				&& Objects.equals(getCustomerName(), customer.getCustomerName())
				&& Objects.equals(getCustomerKana(), customer.getCustomerKana())
				&& Objects.equals(getCustomerPass(), customer.getCustomerPass())
				&& Objects.equals(getCustomerBirth(), customer.getCustomerBirth())
				&& Objects.equals(getCustomerJob(), customer.getCustomerJob())
				&& Objects.equals(getCustomerMail(), customer.getCustomerMail())
				&& Objects.equals(getCustomerTel(), customer.getCustomerTel())
				&& Objects.equals(getCustomerPost(), customer.getCustomerPost())
				&& Objects.equals(getCustomerAdd(), customer.getCustomerAdd());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerCode(), getCustomerName(), getCustomerKana(), getCustomerPass(),
				getCustomerBirth(), getCustomerJob(), getCustomerMail(), getCustomerTel(), getCustomerPost(),
				getCustomerAdd());
	}

	@Override
	public String toString() {
		return "Customer{" + "customerCode='" + customerCode + '\'' + ", customerName='" + customerName + '\''
				+ ", customerKana='" + customerKana + '\'' + ", customerBirth=" + customerBirth + ", customerJob='"
				+ customerJob + '\'' + ", customerMail='" + customerMail + '\'' + ", customerTel='" + customerTel + '\''
				+ ", customerPost='" + customerPost + '\'' + ", customerAdd='" + customerAdd + '\'' + '}';
	}

}
