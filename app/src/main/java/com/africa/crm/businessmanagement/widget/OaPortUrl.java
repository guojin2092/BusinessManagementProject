package com.africa.crm.businessmanagement.widget;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/4/28 14:50
 * Modification  History:
 * Why & What is modified:
 */
public class OaPortUrl {
    //        public final static String TEST_URL = "http://192.168.10.102:9017/oabg_cz/api/";
    public final static String TEST_URL = "http://192.168.10.162:8084/OABG_CZ/api/";
    //        public final static String RELEASE_URL = "http://58.216.242.82:8081/OABG_CZ/api/";
    public final static String RELEASE_URL = "http://58.216.242.82:8081/OABG_CZTEST/api/";
    public final static String RELEASE_TEST_URL = "http://192.168.10.102:9017/oabg_cz/api/";
    public static String ROOT_URL = TEST_URL;

    public final static String AUTH_PASSWORD = "simple@01!";

    /**
     * 附件下载地址
     */
    public final static String Download_Url = ROOT_URL.substring(0, ROOT_URL.length() - 5);

    /*图片上传和加载*/
    public final static String UPLOAD_IMAGE_URL = ROOT_URL + "uploadImg";//图片上传
    /*头像更新*/
    public final static String UPDATE_HEAD_URL = ROOT_URL + "updateHeadImg";//头像更新
    /**
     * 用户相关接口
     */
    public static String USER = "user/";

    //登录
    public final static String requestLogin = ROOT_URL + USER + "login";

    //验证账号是否存在
    public final static String requestcheckUser = ROOT_URL + USER + "checkUser";

    //发送验证码
    public final static String requestSendPhoneCode = ROOT_URL + USER + "sendPhoneCode";

    //重置密码
    public final static String requestResetPassword = ROOT_URL + USER + "resetPassword";

    //修改密码
    public final static String requestModifyPassword = ROOT_URL + USER + "modifyPassword";

    //获取局领导列表
    public final static String requestGetJld = ROOT_URL + "dic/getJld";

    //获取分管领导列表
    public final static String requestGetFgld = ROOT_URL + "dic/getFgld";

    //获取字典
    public final static String requestGetDic = ROOT_URL + "dic/getDicItems";

    //获取用户申请code
    public final static String requestGetMenuCode = ROOT_URL + "dic/getMenuCodeByUserId";

    /**
     * 待办事项相关接口
     */
    public static String DBSX = "dbsx/";

    //获取待办事项分类列表
    public final static String DBSX_TYPE_LIST_URL = ROOT_URL + DBSX + "getDbsxTypeList";

    //获取公章申请列表
    public final static String DBSX_GZSQ_LIST_URL = ROOT_URL + "gz/getGzshList";

    //获取公章申请详情
    public final static String DBSX_GZSQ_DETAIL_URL = ROOT_URL + "gz/getGzshDetail";

    //公章申请审批
    public final static String DBSX_GZSQ_SP_URL = ROOT_URL + "gz/gzsh";

    //获取办公用品申请列表
    public final static String DBSX_BGYPSQ_LIST_URL = ROOT_URL + "bzyp/getBgypshList";

    //获取办公用品申请列表
    public final static String DBSX_BGYPSQ_DETAIl_URL = ROOT_URL + "bzyp/getBgypDetail";

    //办公用品申请审批
    public final static String DBSX_BGYPSQ_SP_URL = ROOT_URL + "bzyp/bzypsh";

    //流程查看列表
    public final static String DBSX_LCCK_LIST_URL = ROOT_URL + "common/getCllcList";

    //获取劳务用品申请列表
    public final static String DBSX_LWYPSQ_LIST_URL = ROOT_URL + "bzyp/getLwypshList";

    //获取劳务用品申请详情
    public final static String DBSX_LWYPSQ_DETAIl_URL = ROOT_URL + "bzyp/getLwypDetail";

    //劳务用品申请审批
    public final static String DBSX_LWYPSQ_SP_URL = ROOT_URL + "bzyp/lwypsh";

    //获取设备维修申请列表
    public final static String DBSX_SBWXSQ_LIST_URL = ROOT_URL + "bzyp/getSbwxshList";

    //获取设备维修申请详情
    public final static String DBSX_SBWXSQ_DETAIl_URL = ROOT_URL + "bzyp/getSbwxDetail";

    //设备维修申请审批
    public final static String DBSX_SBWXSQ_SP_URL = ROOT_URL + "bzyp/sbwxsh";

    //设备维修确认
    public final static String DBSX_SBWXSQ_QR_URL = ROOT_URL + "bzyp/sbwxywx";

    //获取借阅申请列表
    public final static String DBSX_JYSQ_LIST_URL = ROOT_URL + "jy/getJyshList";

    //获取借阅申请详情
    public final static String DBSX_JYSQ_DETAIl_URL = ROOT_URL + "jy/getJyDetail";

    //借阅申请审批
    public final static String DBSX_JYSQ_SP_URL = ROOT_URL + "jy/jysh";

    //借阅归还
    public final static String DBSX_JYSQ_GH_URL = ROOT_URL + "jy/jygh";

    //获取公务接待事前申请列表
    public final static String DBSX_HWJDSQ_LIST_URL = ROOT_URL + "hwjd/getHwjdshList";

    //获取公务接待详情
    public final static String DBSX_GWJDSQ_DETAIl_URL = ROOT_URL + "hwjd/getHwjdDetail";

    //公务接待审核
    public final static String DBSX_GWJDSH_URL = ROOT_URL + "hwjd/hwjdsh";

    //获取会议培训事前申请列表
    public final static String DBSX_HYPXSQ_LIST_URL = ROOT_URL + "hwpx/getHypxshList";

    //获取会议培训详情
    public final static String DBSX_HYPXSQ_DETAIl_URL = ROOT_URL + "hwpx/getHypxDetail";

    //会议培训审核
    public final static String DBSX_HYPXSH_URL = ROOT_URL + "hwpx/hwpxsh";

    //工单办理列表
    public final static String DBSX_GDPS_LIST_URL = ROOT_URL + "gdps/getGdpsList";

    //工单办理详情
    public final static String DBSX_GDPS_DETAIl_URL = ROOT_URL + "gdps/getGdpsDetail";

    //派送工单办理
    public final static String DBSX_GDPSBL_URL = ROOT_URL + "gdps/gdpsbl";

    //获取信息报送申请列表
    public final static String DBSX_XXBS_LIST_URL = ROOT_URL + "xxbs/getXxbsshList";

    //获取信息报送详情
    public final static String DBSX_XXBS_DETAIL_URL = ROOT_URL + "bzyp/getXxbsDetail";

    //信息报送录用
    public final static String DBSX_XXBS_LY_URL = ROOT_URL + "xxbs/xxbssh";

    //信息报送打分
    public final static String DBSX_XXBS_DF_URL = ROOT_URL + "xxbs/xxbsdf";

    //获取资产申请列表
    public final static String DBSX_ZCSQ_LIST_URL = ROOT_URL + "zcsq/getZcsqshList";

    //获取资产申请列表
    public final static String DBSX_ZCSQ_DETAIl_URL = ROOT_URL + "zcsq/zcspDetail";

    //资产申请审批
    public final static String DBSX_ZCSQ_SP_URL = ROOT_URL + "zcsq/zcsqsh";

    //特殊收文列表
    public final static String DBSX_TSSW_LIST_URL = ROOT_URL + "sw/tsswList";

    //特殊收文详情
    public final static String DBSX_TSSW_DETAIL_URL = ROOT_URL + "sw/getTsswDetail";

    //特殊收文回复
    public final static String DBSX_TSSW_HF_URL = ROOT_URL + "sw/tsswHf";

    //请示件列表
    public final static String DBSX_QSJ_LIST_URL = ROOT_URL + "qsj/getQsjList";

    //请示件详情
    public final static String DBSX_QSJ_DETAIL_URL = ROOT_URL + "qsj/getQsjDetail";

    //请示件流转
    public final static String DBSX_QSJ_LZ_URL = ROOT_URL + "qsj/doQsjLz";

    //请示件传办
    public final static String DBSX_QSJ_CB_URL = ROOT_URL + "qsj/doQsjCb";

    //请示件审批
    public final static String DBSX_QSJ_SP_URL = ROOT_URL + "qsj/doQsjSp";

    //出差申请列表
    public final static String DBSX_CCSP_LIST_URL = ROOT_URL + "ccsp/getCcspList";

    //出差申请详情
    public final static String DBSX_CCSP_DETAIL_URL = ROOT_URL + "ccsp/getCcspDetail";

    //出差审批
    public final static String DBSX_CCSP_URL = ROOT_URL + "ccsp/doCcsp";

    //出差审批
    public final static String DBSX_CCSQ_URL = ROOT_URL + "ccsp/doCcsq";

    //获取工作台目录
    public final static String DBSX_WORK_BENCH_URL = ROOT_URL + "dbsx/getWorkBench";

    //我的申请列表
    public final static String requestMyApplyListUrl = ROOT_URL + "list/sqList";

    //工作安排列表
    public final static String requestGzapListUrl = ROOT_URL + "gzjh/getGzjhList";

    //工作安排详情
    public final static String requestGzapDetailUrl = ROOT_URL + "gzjh/getGzjhInfo";
    /**
     * 通讯录相关
     */
    public static String CONTACT = "contact/";

    //获取通讯录列表
    public final static String requestGetContactUrl = ROOT_URL + CONTACT + "getContactList";

    //获取通讯录列表
    public final static String requestContactDetail = ROOT_URL + CONTACT + "getContactDetail";

    //获取组织架构列表
    public final static String requestDepartmentList = ROOT_URL + CONTACT + "getDepartmentList";

    //获取部门列表
    public final static String requestLzbmList = ROOT_URL + CONTACT + "getDwbm";

    /**
     * 发文相关
     */
    public static String FW = "fw/";

    //获取发文管理列表
    public final static String requestGetFwglList = ROOT_URL + FW + "getFwshList";

    //获取发文详情
    public final static String requestGetFwglDetail = ROOT_URL + FW + "getFwshDetail";

    //发文核稿
    public final static String requestFwglFwhg = ROOT_URL + FW + "hegao";

    //初审
    public final static String requestFwglFwcs = ROOT_URL + FW + "chushen";

    //流转审批
    public final static String requestFwglFwlzsp = ROOT_URL + FW + "lzsp";

    //分管领导会签
    public final static String requestFwglFwhq = ROOT_URL + FW + "ldsp";

    //局长审批
    public final static String requestFwglFwjzsp = ROOT_URL + FW + "jzsp";

    //编号登记
    public final static String requestFwglFwbhdj = ROOT_URL + FW + "jysDingGao";

    //发文查阅列表
    public final static String requestGetFwcyList = ROOT_URL + FW + "getFwcyList";

    //发文查阅详情
    public final static String requestGetFwcyDetail = ROOT_URL + FW + "getFwcyDetail";

    /**
     * 收文相关
     */
    public static String SW = "sw/";

    //获取收文管理列表
    public final static String requestGetSwglList = ROOT_URL + SW + "getSwshList";

    //获取收文管理详情
    public final static String requestGetSwglDetail = ROOT_URL + SW + "getSwshDetail";

    //收文拟办
    public final static String requestGetSwglNiBan = ROOT_URL + SW + "niban";

    //收文批阅
    public final static String requestGetSwglPiyue = ROOT_URL + SW + "piyue";

    //获取审批模板
    public final static String requestGetSpmb = ROOT_URL + "dic/getSpmb";

    //收文处理
    public final static String requestGetSwglChuli = ROOT_URL + SW + "swcl";

    //收文查阅列表
    public final static String requestGetSwcyList = ROOT_URL + SW + "getSwcyList";

    //收文查阅详情
    public final static String requestGetSwcyDetail = ROOT_URL + SW + "getSwcyDetail";

    //收文查阅回复
    public final static String requestDoSwcyHf = ROOT_URL + SW + "doSwcyHf";

    //收文回复信息
    public final static String requestGetSwcyHf = ROOT_URL + SW + "getSwcyHfxx";

    /**
     * 会议管理相关
     */
    public static String MEETING = "meeting/";

    //获取会议管理列表
    public final static String requestGetMeetingList = ROOT_URL + MEETING + "getMeetingList";

    //获取会议详情
    public final static String requestGetMeetingDetail = ROOT_URL + MEETING + "getMeetingDetail";

    //申请会议
    public final static String requestApplyMeetingUrl = ROOT_URL + MEETING + "launchMeeting";

    //会议反馈
    public final static String requestMeetingResponseUrl = ROOT_URL + MEETING + "meetingResponse";

    //会议反馈列表
    public final static String requestMeetingResponseListUrl = ROOT_URL + MEETING + "meetingRespStu";

    //会议领导意见
    public final static String requestMeetingLdyjListUrl = ROOT_URL + MEETING + "getMeetingLdjy";

    //会议收件人列表
    public final static String requestSjrListUrl = ROOT_URL + "dic/getHytzSjr";

    /**
     * 申请
     */
    //信息报送申请
    public final static String requestXxbsApply = ROOT_URL + "xxbs/addXxbs";
    //公章申请
    public final static String requestGzApply = ROOT_URL + "gz/addGzsq";
    //资产领用申请
    public final static String requestZclyApply = ROOT_URL + "zcsq/addZcly";
    //资产名称
    public final static String requestZcListApply = ROOT_URL + "zcsq/getZcList";
    //设备维修申请
    public final static String requestSbwxApply = ROOT_URL + "bzyp/addSbwxsq";
    //劳务用品列表
    public final static String requestLwypList = ROOT_URL + "bzyp/getLwypList";
    //办公用品列表
    public final static String requestBgypList = ROOT_URL + "bzyp/getBgypList";
    //劳务用品申请
    public final static String requestLwypApply = ROOT_URL + "bzyp/addLwypsq";
    //办公用品申请
    public final static String requestBgypApply = ROOT_URL + "bzyp/addBgypsq";
    //公务接待申请
    public final static String requestGwjdApply = ROOT_URL + "hwjd/addGwjdsq";
    //会议培训申请
    public final static String requestHypxApply = ROOT_URL + "hwpx/addHypxsq";
    //文档借阅申请
    public final static String requestWdjyApply = ROOT_URL + "wdjy/addWdjysq";

    /**
     * 20171220 新增接口  lmf
     */
    //文档借阅申请
    public final static String requestCkHytz = ROOT_URL + "meeting/ckHytz";

    /**
     * 20171227 新增接口 guoj
     */
    //会议室预定
    public final static String addHysydsq = ROOT_URL + "hysyd/addHysydsq";

    //会议室预定列表
    public final static String getListByCodeAndDate = ROOT_URL + "hysyd/getListByCodeAndDate";

    //会议室预定详情
    public final static String getHysydInfo = ROOT_URL + "hysyd/getHysydInfo";

    //每月预定列表
    public final static String getMonthListByCodeAndDate = ROOT_URL + "hysyd/getMonthListByCodeAndDate";

    //会议室预定审批列表
    public final static String getYdspList = ROOT_URL + "hysyd/getYdspList";

    //个人预定申请列表
    public final static String getYdsqList = ROOT_URL + "hysyd/getYdsqList";

    //个人预定申请审批
    public final static String getHysydSp = ROOT_URL + "hysyd/getHysydSp";

    //20180110 刘明峰 新增 业务数据接口
    //获取业务数据的权限
    public final static String getYwsjList = ROOT_URL + "ywsj/getYwsjResource";

    //请示件申请删除
    public final static String deleteQsjsqUrl = ROOT_URL + "qsj/deleteQsjsq";

    //会议阅读记录列表
    public final static String requestMeetingYdjlListUrl = ROOT_URL + MEETING + "getReadList";

    //会议阅读记录列表
    public final static String requestGzapYdjlListUrl = ROOT_URL + "gzjh/getGzjhReadList";

    //20180125 刘明峰 发文新流程的接口
    //发文审稿
    public final static String requestFwglFwsg = ROOT_URL + FW + "sg";
    //公开性审核
    public final static String requestFwglGKXSH = ROOT_URL + FW + "gkxsh";
    //公平竞争审核
    public final static String requestFwglGPJZSH = ROOT_URL + FW + "gpjzsh";
    //选择局领导
    public final static String requestFwglXZJLD = ROOT_URL + FW + "xzjld";
    //分管领导审批
    public final static String requestFwglFGLDSP = ROOT_URL + FW + "fgldsp";
    //局长审批
    public final static String requestFwglJZSP = ROOT_URL + FW + "jzsp";
    //发文分发
    public final static String requestFwglFF = ROOT_URL + FW + "ngrff";
    //发文阅读记录
    public final static String requestFwdyList = ROOT_URL + FW + "getFwdyList";

    //收文阅读记录列表
    public final static String requestSwYdjlListUrl = ROOT_URL + "sw/getSwcyReadList";

    //资料中心列表
    public final static String requestZlzxListUrl = ROOT_URL + "zlzx/getZlzxList";

    //资料详情
    public final static String requestZlzxInfoUrl = ROOT_URL + "zlzx/getZlzxInfo";

}