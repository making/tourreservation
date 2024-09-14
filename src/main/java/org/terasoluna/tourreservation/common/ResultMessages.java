package org.terasoluna.tourreservation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

public record ResultMessages(Type type, List<String> messageCodes) {

	public static ResultMessages error() {
		return new ResultMessages(Type.ERROR, new ArrayList<>());
	}

	public ResultMessages add(String messageId) {
		this.messageCodes.add(messageId);
		return this;
	}

	public List<String> resolveMessages(MessageSource messageSource) {
		return this.messageCodes.stream()
			.map(code -> messageSource.getMessage(code, null, Locale.getDefault()))
			.toList();
	}

	public boolean isNotEmpty() {
		return this.messageCodes != null && !this.messageCodes.isEmpty();
	}

	public enum Type {

		ERROR;

		@Override
		public String toString() {
			return this.name().toLowerCase(Locale.ENGLISH);
		}

	}
}
