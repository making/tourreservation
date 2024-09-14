package org.terasoluna.tourreservation.config;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.terasoluna.tourreservation.common.LabelCodeList;
import org.terasoluna.tourreservation.common.RefreshableLabelCodeList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import static org.terasoluna.tourreservation.common.JdbcLabelCodeListBuilder.jdbc;
import static org.terasoluna.tourreservation.common.LabelCodeBuilder.labelCode;
import static org.terasoluna.tourreservation.common.LabelCodeLists.refreshable;
import static org.terasoluna.tourreservation.common.NumberRangeLabelCodeListBuilder.numberRange;

@Configuration(proxyBeanMethods = false)
public class LabelCodeListConfig {

	@Bean
	@RequestScope
	public LabelCodeList depYearLabelCodeList() {
		LocalDate now = LocalDate.now();
		return numberRange().from(now.getYear()).to(now.plusYears(1).getYear()).build();
	}

	@Bean
	@RequestScope
	public LabelCodeList birthYearLabelCodeList() {
		return numberRange().from(1900).to(LocalDate.now().getYear()).build();
	}

	@Bean
	public LabelCodeList monthLabelCodeList() {
		return numberRange().from(1).to(12).labelFormat("%02d").build();
	}

	@Bean
	public LabelCodeList dayOfMonthLabelCodeList() {
		return numberRange().from(1).to(31).labelFormat("%02d").build();
	}

	@Bean
	public LabelCodeList tourDaysLabelCodeList() {
		return LabelCodeList.of(labelCode().label("制限なし").code("0"), labelCode().label("日帰り").code("1"),
				labelCode().label("1泊以下").code("2"), labelCode().label("2泊以下").code("3"),
				labelCode().label("3泊以下").code("4"), labelCode().label("4泊以下").code("5"),
				labelCode().label("5泊以下").code("6"));
	}

	@Bean
	public LabelCodeList basePriceLabelCodeList() {
		return LabelCodeList.of(labelCode().label("上限なし").code("0"), labelCode().label("10,000円以下").code("10000"),
				labelCode().label("20,000円以下").code("20000"), labelCode().label("30,000円以下").code("30000"),
				labelCode().label("40,000円以下").code("40000"), labelCode().label("50,000円以下").code("50000"),
				labelCode().label("60,000円以下").code("60000"));
	}

	@Bean
	public LabelCodeList existenceLabelCodeList() {
		return LabelCodeList.of(labelCode().label("無").code("0"), labelCode().label("有").code("1"));
	}

	@Bean
	public LabelCodeList adultCountLabelCodeList() {
		return numberRange().from(1).to(5).build();
	}

	@Bean
	public LabelCodeList childCountLabelCodeList() {
		return numberRange().from(1).to(5).build();
	}

	@Bean
	public RefreshableLabelCodeList prefectureLabelCodeList(DataSource dataSource) {
		return refreshable(() -> jdbc().dataSource(dataSource)
			.querySql("SELECT arr_code, arr_name FROM arrival ORDER BY arr_code")
			.labelColumn("arr_name")
			.codeColumn("arr_code")
			.build());
	}

}
