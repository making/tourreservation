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
package org.terasoluna.tourreservation.tour;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PriceCalculateSharedService {

	private final AgeMapper ageMapper;

	private final PlatformTransactionManager transactionManager;

	private Age adultAge;

	private Age childAge;

	public PriceCalculateSharedService(AgeMapper ageMapper, PlatformTransactionManager transactionManager) {
		this.ageMapper = ageMapper;
		this.transactionManager = transactionManager;
	}

	public PriceCalculateOutput calculatePrice(Integer basePrice, Integer adultCount, Integer childCount) {
		PriceCalculateOutput result = new PriceCalculateOutput();

		int adultUnitPrice = basePrice * adultAge.getAgeRate() / 100;

		result.setAdultUnitPrice(adultUnitPrice);

		int adultPrice = adultCount * adultUnitPrice;
		result.setAdultPrice(adultPrice);
		int childUnitPrice = basePrice * childAge.getAgeRate() / 100;

		result.setChildUnitPrice(childUnitPrice);

		int childPrice = childCount * childUnitPrice;

		result.setChildPrice(childPrice);
		result.setAdultCount(adultCount);
		result.setChildCount(childCount);
		result.setSumPrice(adultPrice + childPrice);

		return result;
	}

	@PostConstruct
	public void loadAges() {
		// use TransactionTemplate to avoid SQLException which tells set-readonly is not
		// allowed
		// see https://github.com/terasolunaorg/terasoluna-tourreservation/issues/163
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				childAge = ageMapper.findById("1").orElseThrow(IllegalStateException::new);
				adultAge = ageMapper.findById("0").orElseThrow(IllegalStateException::new);
			}
		});
	}

}
