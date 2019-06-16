package org.fkjava.travel.core.vo;

public class Result<T> {

	/**
	 * 操作成功
	 */
	public static final int STATUS_OK = 1;
	/**
	 * 操作失败
	 */
	public static final int STATUS_ERROR = 2;
	/**
	 * 操作状态
	 */
	private int status;
	/**
	 * 提示信息
	 */
	private String message;
	/**
	 * 附加对象
	 */
	private T attachment;

	public static <T> Result<T> of(int status, String message, T t) {
		Result<T> r = new Result<>();
		r.setAttachment(t);
		r.setMessage(message);
		r.setStatus(status);
		return r;
	}

	public static <T> Result<T> of(int status, String message) {
		Result<T> r = new Result<>();
		r.setMessage(message);
		r.setStatus(status);
		return r;
	}

	public static <T> Result<T> of(int status, T t) {
		Result<T> r = new Result<>();
		r.setAttachment(t);
		r.setStatus(status);
		return r;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getAttachment() {
		return attachment;
	}

	public void setAttachment(T attachment) {
		this.attachment = attachment;
	}
}
