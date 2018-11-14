package baselibrary.library.special.ui.user.bean;

import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/3/20.
 * 短信验证码
 */
public class MsgCodeBean extends BaseSimpleBean {
    public String Version;
    public String Charset;
    public UserVariable Variables;
    public Message message;

    public class Message implements Serializable {
        public String messageval;
        public String messagestr;
    }
}
