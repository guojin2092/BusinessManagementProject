package com.simplesoft.basesimplelibrary.bean;


import java.io.Serializable;

public class UserVariable implements Serializable {
	public String cookiepre;
	public String auth;
	public String imauth;
	public String saltkey;
	public String member_uid;
	public String member_username;
	/*public String groupid;*/
	public String formhash;
	/*public String ismoderator;*/
	/*public String readaccess;*/
	public Notice notice;

	public String hash;
	public String avatar;
	public Store store;

	public String qiniu_mall_token;
	public String qiniu_forum_token;
	public String qiniu_avatar_token;
	public String avatar_token_key;
}
