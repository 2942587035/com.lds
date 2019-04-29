package enums;

/**
 * 枚举类：
 * 定义一些公用枚举
 * @author lds
 *
 */
public class EnumPub {
	/**
	 * 用户名枚举
	 * @author lds
	 *
	 */
    public enum UserCode {
    	IsBlank("用户名为空！"),
    	NotFound("用户名输入错误，请重新输入！"),
    	Repeat("库中存在多个相同用户名！");
    	
    	//提示信息
    	private String message;
    	
    	private UserCode(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * 密码枚举
	 * @author lds
	 *
	 */
    public enum Passworld {
    	IsBlank("密码为空！"),
    	Error("密码输入错误，请重新输入！");
    	
    	//提示信息
    	private String message;
    	
    	private Passworld(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * 登录
	 * @author lds
	 *
	 */
    public enum Login {
    	Success("登录成功！");
    	
    	//提示信息
    	private String message;
    	
    	private Login(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
    
	/**
	 * 消息存储
	 * @author lds
	 *
	 */
    public enum Message {
    	RemoveSuccess("信息删除成功！");
    	
    	//提示信息
    	private String message;
    	
    	private Message(String str) {
    		this.message = str;
    	}
    	
		public String getMessage() {
			return message;
		}
		
    }
}
