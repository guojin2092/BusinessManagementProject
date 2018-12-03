package baselibrary.library.special.ui.user.bean;


import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.io.Serializable;

public class ChangeAccountBean extends BaseSimpleBean {
	public String Version;
	public String Charset;
	public Variable Variables;
	public Message message;

	public class Variable implements Serializable {
		public String cookiepre;
		public String auth;
		public String saltkey;
		public String member_uid;
		public String member_username;

		public String formhash;

		public Notice notice;
	}

	public class Message implements Serializable {
		public String messageval;
		public String messagestr;

	}
}
