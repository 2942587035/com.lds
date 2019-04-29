package enums;

/**
 * ö���ࣺ
 * ����һЩ����ö��
 * @author lds
 *
 */
public class EnumPub {
	/**
	 * �û���ö��
	 * @author lds
	 *
	 */
    public enum UserCode {
    	IsBlank("�û���Ϊ�գ�"),
    	NotFound("�û�������������������룡"),
    	Repeat("���д��ڶ����ͬ�û�����");
    	
    	//��ʾ��Ϣ
    	private String message;
    	
    	private UserCode(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * ����ö��
	 * @author lds
	 *
	 */
    public enum Passworld {
    	IsBlank("����Ϊ�գ�"),
    	Error("��������������������룡");
    	
    	//��ʾ��Ϣ
    	private String message;
    	
    	private Passworld(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * ��¼
	 * @author lds
	 *
	 */
    public enum Login {
    	Success("��¼�ɹ���");
    	
    	//��ʾ��Ϣ
    	private String message;
    	
    	private Login(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * ��Ϣ�洢
	 * @author lds
	 *
	 */
    public enum Message {
    	RemoveSuccess("��Ϣɾ���ɹ���");
    	
    	//��ʾ��Ϣ
    	private String message;
    	
    	private Message(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
}
