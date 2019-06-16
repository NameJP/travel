package org.fkjava.travel.commons;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 定义一个通用的日期转换器
public class DateEditor extends PropertyEditorSupport {

	// 定义日期的格式
	private DateFormat df;

	public DateEditor(String pattern) {
		df = new SimpleDateFormat(pattern);
	}

	// 负责把字符串转换为需要的对象
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			// 把字符串转换为Date对象
			Date d = df.parse(text);

			// 必须要调用父类的setValue方法，把值设置到目标对象里面去！
			super.setValue(d);

		} catch (ParseException e) {
			throw new IllegalArgumentException("无法把 " + text + " 转换为 yyyy-MM-dd 格式的Date对象", e);
		}
	}
}
