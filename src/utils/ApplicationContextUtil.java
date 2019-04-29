package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 使用spring配置获取bean工具类
 * @author lds
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 * 此方法会在web.xml配置的spring中注入ApplicationContext
	 */
	public void setApplicationContext(ApplicationContext context) {
		ApplicationContextUtil.context = context;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	private static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return context;
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		context = null;
	}

	private static void checkApplicationContext() {
		if (context == null) {
			throw new IllegalStateException("applicaitonContext未注入，请在applicationContext.xml中定义ApplicationContextUtil");
		}
	}

	/**
	 * 获取bean
	 * 
	 * @param service
	 */
	public static Object getBean(String service) {
		return getApplicationContext().getBean(service);
	}
}
