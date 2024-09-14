package org.terasoluna.tourreservation.common;

public class BusinessException extends RuntimeException {

	private final ResultMessages resultMessages;

	public BusinessException(ResultMessages resultMessages) {
		this.resultMessages = resultMessages;
	}

	public ResultMessages getResultMessages() {
		return resultMessages;
	}

}
