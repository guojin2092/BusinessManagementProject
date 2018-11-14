package baselibrary.library.special.ui.user.bean;

import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.io.Serializable;

/**
 * 推送bean
 *
 * @author Administrator
 */
public class PushBaiduBean extends BaseSimpleBean {

    public String Version;
    public String Charset;
    public UserVariable Variables;
    public Message message;

    public class Message implements Serializable {
        public String messageval;
        public String messagestr;
    }
}
