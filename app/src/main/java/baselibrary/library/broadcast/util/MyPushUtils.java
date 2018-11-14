package baselibrary.library.broadcast.util;

import android.content.Context;
import android.support.v4.app.Fragment;

import baselibrary.library.broadcast.bean.PushAdTagBean;


/**
 * Created by Administrator on 2015/6/2.
 * 推送自定义工具类
 */
public class MyPushUtils {
    /*   public static String logStringCache = "";*/
    private static boolean isClickPush = false;
    /*精选婚礼*/
    public final static String PUSH_FEATURED_WEDDINGS = "featured_weddings";
    /*婚礼专题*/
    public final static String PUSH_WEDDING_THEMES = "wedding_themes";
    /*精选视频*/
    public final static String PUSH_FEATURED_VIDEO = "featured_video";
    /*优惠活动*/
    public final static String PUSH_PREFERENTAIL_ACTIVITY = "preferential_activity";
    /*线下活动*/
    public final static String PUSH_OFFLINE_ACTIVITY = "offline_activity";
    /*团队详情页*/
    public final static String PUSH_TEAM_DETAIL = "team_detail";
    /*公司详情页*/
    public final static String PUSH_COMPANY_DETAIL = "company_detail";
    /* 酒店详情页*/
    public final static String PUSH_HOTEL_DETAIL = "hotel_detail";
    /*网页链接*/
    public final static String PUSH_WEB_URL = "web_url";
    /*私信消息页*/
    public final static String PUSH_NOTIFY_PM = "notify_pm";
    /*帖子消息页*/
    public final static String PUSH_NOTIFY_POST = "notify_post";
    /*系统消息页*/
    public final static String PUSH_NOTIFY_SYSTEM = "notify_system";


    /**
     * 根据推送类型打开相应界面
     *
     * @param fragment
     * @param pushAdTagBean
     */
    public static void openPush(Fragment fragment, PushAdTagBean pushAdTagBean) {
        openPush(fragment.getActivity(), pushAdTagBean);
    }


    /**
     * 根据推送类型打开相应界面
     *
     * @param context
     * @param pushAdTagBean
     */
    public static void openPush(Context context, PushAdTagBean pushAdTagBean) {
        switch (pushAdTagBean.type) {
          /*  case PUSH_FEATURED_WEDDINGS:
                ChoiceWeddingInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_WEDDING_THEMES:
                ChoiceWeddingSubjectInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_FEATURED_VIDEO:
                ChoiceVideoInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_PREFERENTAIL_ACTIVITY:
                ChoicePreferentialActionInfoActivity.startActivity(context, pushAdTagBean.params.title, pushAdTagBean.params.desc, pushAdTagBean.params.img);
                break;
            case PUSH_OFFLINE_ACTIVITY:
                ChoiceOfflineActionInfoActivity.startActivity(context, pushAdTagBean.params.title, pushAdTagBean.params.desc, pushAdTagBean.params.img);
                break;
            case PUSH_TEAM_DETAIL:
                TeamInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_COMPANY_DETAIL:
                CompanyInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_HOTEL_DETAIL:
                HotelInfoActivity.startActivity(context, pushAdTagBean.detail);
                break;
            case PUSH_WEB_URL:
                WebViewWebsiteActivity.startActivity(context, pushAdTagBean.detail, pushAdTagBean.params.title);
                break;
            case PUSH_NOTIFY_PM:
                *//*跳转系统消息界面*//*
                MyMessageListTabActivity.startActivity(context, 0);
                break;
            case PUSH_NOTIFY_POST:
                *//*跳转系统消息界面*//*
                MyMessageListTabActivity.startActivity(context, 1);
                break;
            case PUSH_NOTIFY_SYSTEM:
                *//*跳转系统消息界面*//*
                MyMessageListTabActivity.startActivity(context, 2);
                break;*/
        }
    }

    /**
     * 是否查看过推送信息
     *
     * @param isPush
     * @return
     */
    public static void setClickPush(boolean isPush) {
        isClickPush = isPush;
    }

    public static boolean getClickPush() {
        return isClickPush;
    }

}
