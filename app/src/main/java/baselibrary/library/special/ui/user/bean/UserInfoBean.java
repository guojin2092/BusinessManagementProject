package baselibrary.library.special.ui.user.bean;

import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.io.Serializable;

/**
 * 获取用户信息
 *
 * @author Administrator
 */
public class UserInfoBean extends BaseSimpleBean {
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
        public User user;
        public String avatar;
        public String avatar_big;

        public class User implements Serializable {
            public String comments;
            public String collects;
            public String follow;
        }
    }

    public class Message implements Serializable {
        public String messageval;
        public String messagestr;
    }
}
