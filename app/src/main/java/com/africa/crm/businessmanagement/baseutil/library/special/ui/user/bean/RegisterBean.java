package com.africa.crm.businessmanagement.baseutil.library.special.ui.user.bean;

import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.io.Serializable;

/**
 * 用户登录
 * 
 * @author Administrator
 * 
 */
public class RegisterBean extends BaseSimpleBean {
	public String Version;
	public String Charset;
	public UserVariable Variables;
	public Message message;

	public class Message implements Serializable {
		public String messageval;
		public String messagestr;
	}
}
