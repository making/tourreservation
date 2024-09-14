package org.terasoluna.tourreservation.common;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

public final class LabelCodeLists {

	@Builder(style = BuilderStyle.STAGED, factoryMethod = "numberRange", className = "NumberRangeLabelCodeListBuilder")
	public static LabelCodeList numberRange(int from, int to, @Nullable String labelFormat,
			@Nullable String codeFormat) {
		return LabelCodeList.of(IntStream.rangeClosed(from, to)
			.mapToObj(i -> LabelCodeBuilder.labelCode()
				.label(Objects.requireNonNullElse(labelFormat, "%d").formatted(i))
				.code(Objects.requireNonNullElse(codeFormat, "%d").formatted(i))
				.build())
			.toList());
	}

	@Builder(style = BuilderStyle.STAGED, factoryMethod = "jdbc", className = "JdbcLabelCodeListBuilder")
	public static LabelCodeList jdbc(DataSource dataSource, String querySql, String labelColumn, String codeColumn,
			@Nullable Integer fetchSize) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setFetchSize(Objects.requireNonNullElse(fetchSize, 1000));
		return LabelCodeList.of(jdbcTemplate.query(querySql,
				(rs, rowNum) -> LabelCodeBuilder.labelCode()
					.label(rs.getString(labelColumn))
					.code(rs.getString(codeColumn))
					.build()));
	}

	public static RefreshableLabelCodeList refreshable(Supplier<LabelCodeList> supplier) {
		return new RefreshableLabelCodeList(supplier);
	}

}
