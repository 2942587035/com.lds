package log;

import java.text.SimpleDateFormat;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6SPY 自定义日志输出
 * @author 李东升
 *
 */
public class MySingleLineFormat implements MessageFormattingStrategy {
	private final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		if (sql == null || sql.equals("")) {
			return "";
		}
		return DateFormat.format(System.currentTimeMillis()) + " (" + elapsed + "ms) " + sql + ";";
	}

}
