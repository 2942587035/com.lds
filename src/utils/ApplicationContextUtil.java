package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ʹ��spring���û�ȡbean������
 * @author lds
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	/**
	 * ʵ��ApplicationContextAware�ӿڵ�contextע�뺯��, ������뾲̬����.
	 * �˷�������web.xml���õ�spring��ע��ApplicationContext
	 */
	public void setApplicationContext(ApplicationContext context) {
		ApplicationContextUtil.context = context;
	}

	/**
	 * ȡ�ô洢�ھ�̬�����е�ApplicationContext.
	 */
	private static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return context;
	}

	/**
	 * ���applicationContext��̬����.
	 */
	public static void cleanApplicationContext() {
		context = null;
	}

	private static void checkApplicationContext() {
		if (context == null) {
			throw new IllegalStateException("applicaitonContextδע�룬����applicationContext.xml�ж���ApplicationContextUtil");
		}
	}

	/**
	 * ��ȡbean
	 * 
	 * @param service
	 */
	public static Object getBean(String service) {
		return getApplicationContext().getBean(service);
	}
}
