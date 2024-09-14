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
import java.util.Objects;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private String staffCode;

	private String staffName;

	private String staffKana;

	private String staffPass;

	public Employee() {
	}

	public String getStaffCode() {
		return this.staffCode;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public String getStaffKana() {
		return this.staffKana;
	}

	public String getStaffPass() {
		return this.staffPass;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setStaffKana(String staffKana) {
		this.staffKana = staffKana;
	}

	public void setStaffPass(String staffPass) {
		this.staffPass = staffPass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Employee employee))
			return false;
		return Objects.equals(getStaffCode(), employee.getStaffCode())
				&& Objects.equals(getStaffName(), employee.getStaffName())
				&& Objects.equals(getStaffKana(), employee.getStaffKana())
				&& Objects.equals(getStaffPass(), employee.getStaffPass());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStaffCode(), getStaffName(), getStaffKana(), getStaffPass());
	}

	@Override
	public String toString() {
		return "Employee{" + "staffCode='" + staffCode + '\'' + ", staffName='" + staffName + '\'' + ", staffKana='"
				+ staffKana + '\'' + '}';
	}

}
