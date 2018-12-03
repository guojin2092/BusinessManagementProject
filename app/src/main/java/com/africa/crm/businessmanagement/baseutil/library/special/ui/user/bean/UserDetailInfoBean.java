package com.africa.crm.businessmanagement.baseutil.library.special.ui.user.bean;

import java.io.Serializable;

/**
 * 获取用户信息
 * 
 * @author Administrator
 * 
 */
public class UserDetailInfoBean implements Serializable {
	public String Version;
	public String Charset;
	public Variable Variables;

	public class Variable implements Serializable {
		public String cookiepre;
		public String auth;
		public String saltkey;
		public String member_uid;
		public String member_username;
		public String groupid;
		public String formhash;
		public String ismoderator;
		public String readaccess;
		public Notice notice;
		public Space space;
		public String hash;

		public class Space implements Serializable {
			public String gender;
			public String occupation;
			public String field1;
			public String field2;
			public String field3;
			public String sightml;
		}
	}
}
