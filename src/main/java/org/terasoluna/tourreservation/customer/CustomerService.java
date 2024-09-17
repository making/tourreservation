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
package org.terasoluna.tourreservation.customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomerService {

	private final CustomerMapper customerMapper;

	private final PasswordEncoder passwordEncoder;

	public CustomerService(CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
		this.customerMapper = customerMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public Customer findOne(String customerCode) {
		return customerMapper.findById(customerCode).orElse(null);
	}

	public Customer register(Customer customer, String rawPassword) {

		String password = passwordEncoder.encode(rawPassword);

		customer.setCustomerPass(password);
		customerMapper.insert(customer);
		return customer;
	}

}
