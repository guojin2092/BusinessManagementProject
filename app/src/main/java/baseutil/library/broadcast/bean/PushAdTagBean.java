package baseutil.library.broadcast.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/2.
 * 推送
 */
public class PushAdTagBean implements Serializable {
    public String type;
    public String detail;
    public Params params;

    public class Params implements Serializable {
        public String title;
        public String desc;
        public String img;
    }
}
