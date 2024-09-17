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

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.slf4j.Logger;
import org.terasoluna.tourreservation.common.BusinessException;
import org.terasoluna.tourreservation.common.ResultMessages;
import org.terasoluna.tourreservation.message.BusinessMessageId;
import org.terasoluna.tourreservation.tour.TourInfo;
import org.terasoluna.tourreservation.tour.PriceCalculateOutput;
import org.terasoluna.tourreservation.tour.PriceCalculateSharedService;
import org.terasoluna.tourreservation.tour.TourInfoSharedService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Transactional
@Service
@Builder(style = BuilderStyle.STAGED)
public class ReserveService {

	private static final Logger log = getLogger(ReserveService.class);

	private final ReserveMapper reserveMapper;

	private final AuthorizedReserveSharedService authorizedReserveSharedService;

	private final TourInfoSharedService tourInfoSharedService;

	private final PriceCalculateSharedService priceCalculateService;

	private final Clock clock;

	public ReserveService(ReserveMapper reserveMapper, AuthorizedReserveSharedService authorizedReserveSharedService,
			TourInfoSharedService tourInfoSharedService, PriceCalculateSharedService priceCalculateService,
			Clock clock) {
		this.reserveMapper = reserveMapper;
		this.authorizedReserveSharedService = authorizedReserveSharedService;
		this.tourInfoSharedService = tourInfoSharedService;
		this.priceCalculateService = priceCalculateService;
		this.clock = clock;
	}

	public Reserve findOneWithTourInfo(String reserveNo) {
		Reserve reserve = authorizedReserveSharedService.findOne(reserveNo);

		if (reserve != null) {
			TourInfo tourInfo = tourInfoSharedService.findOneWithDetails(reserve.getTourInfo().getTourCode());
			reserve.setTourInfo(tourInfo);
		}

		return reserve;
	}

	public List<Reserve> findAllWithTourInfoByCustomer(String customerCode) {
		List<Reserve> reserves = reserveMapper.findAllWithTourInfoByCustomer(customerCode);
		return reserves;
	}

	public ReserveTourOutput reserve(ReserveTourInput input) throws BusinessException {
		TourInfo tourInfo = tourInfoSharedService.findOneWithDetailsForUpdate(input.getTourCode());
		LocalDate today = LocalDate.now(this.clock);

		// * check date
		// error if today is after payment limit
		if (tourInfoSharedService.isOverPaymentLimit(tourInfo)) {
			ResultMessages message = ResultMessages.error().add(BusinessMessageId.E_TR_0004);
			throw new BusinessException(message);
		}

		// * check vacancy
		int reserveMember = input.getAdultCount() + input.getChildCount();
		int aveRecMax = tourInfo.getAvaRecMax();
		// retrieve the number of current reservations
		Long sumCount = reserveMapper.countReservedPersonSumByTourInfo(tourInfo.getTourCode());
		if (sumCount == null) {
			sumCount = 0L;
		}

		// calculate vacancy
		long vacantCount = aveRecMax - sumCount;
		log.debug("vacantCount({}), reserveMember({})", vacantCount, reserveMember);
		// error if reserved number is larger than available vacancy
		if (vacantCount < reserveMember) {
			ResultMessages message = ResultMessages.error().add(BusinessMessageId.E_TR_0005);
			throw new BusinessException(message);
		}

		// * reserve
		PriceCalculateOutput priceCalculateOutput = priceCalculateService.calculatePrice(tourInfo.getBasePrice(),
				input.getAdultCount(), input.getChildCount());

		Reserve reserve = ReserveBuilder.reserve()
			.adultCount(input.getAdultCount())
			.childCount(input.getChildCount())
			.remarks(input.getRemarks())
			.customer(input.getCustomer())
			.tourInfo(tourInfo)
			.sumPrice(priceCalculateOutput.getSumPrice())
			.reservedDay(Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
			.transfer(Reserve.NOT_TRANSFERED)
			.build();

		reserveMapper.insert(reserve);
		log.debug("reserved {}", reserve);

		// * create output
		ReserveTourOutput tourReserveOutput = new ReserveTourOutput();
		tourReserveOutput.setPriceCalculateOutput(priceCalculateOutput);
		tourReserveOutput.setReserve(reserve);
		tourReserveOutput.setTourInfo(tourInfo);
		tourReserveOutput.setPaymentTimeLimit(tourInfo.getPaymentLimit());
		tourReserveOutput.setCustomer(input.getCustomer());

		return tourReserveOutput;
	}

	public void cancel(String reserveNo) throws BusinessException {
		Reserve reserve = findOneWithTourInfo(reserveNo);

		String transfer = reserve.getTransfer();
		if (Reserve.TRANSFERED.equals(transfer)) {
			ResultMessages message = ResultMessages.error().add(BusinessMessageId.E_TR_0001);
			throw new BusinessException(message);
		}

		TourInfo info = tourInfoSharedService.findOneWithDetails(reserve.getTourInfo().getTourCode());

		// compare system date and payment limit.
		// if the payment limit has been exceeded,
		// navigate to business error screen
		if (tourInfoSharedService.isOverPaymentLimit(info)) {
			ResultMessages message = ResultMessages.error().add(BusinessMessageId.E_TR_0002);
			throw new BusinessException(message);
		}

		// cancel the reservation
		// reserve = reserveRepository.findForUpdate(reserveNo); TODO
		reserve = reserveMapper.findOneForUpdate(reserveNo);
		if (reserve != null) {
			reserveMapper.deleteById(reserveNo);
		}
		else {
			ResultMessages message = ResultMessages.error().add(BusinessMessageId.E_TR_0003);
			throw new BusinessException(message);
		}
		log.debug("canceled reserveNo={}", reserveNo);
	}

	public ReservationUpdateOutput update(ReservationUpdateInput input) throws BusinessException {
		Reserve found = findOneWithTourInfo(input.getReserveNo());
		ReserveBuilder reserveBuilder = ReserveBuilder.from(found);
		if (input.getAdultCount() != null) {
			reserveBuilder.adultCount(input.getAdultCount());
		}
		if (input.getChildCount() != null) {
			reserveBuilder.childCount(input.getChildCount());
		}
		Reserve reserve = reserveBuilder.build();

		TourInfo info = reserve.getTourInfo();
		PriceCalculateOutput price = priceCalculateService.calculatePrice(info.getBasePrice(), input.getAdultCount(),
				input.getChildCount());

		reserve.setSumPrice(price.getSumPrice());
		reserveMapper.update(reserve);

		ReservationUpdateOutput output = new ReservationUpdateOutput();
		output.setReserve(reserve);
		output.setPriceCalculateOutput(price);
		output.setPaymentTimeLimit(info.getPaymentLimit());

		return output;
	}

}
