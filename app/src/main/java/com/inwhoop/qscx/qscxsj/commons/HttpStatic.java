package com.inwhoop.qscx.qscxsj.commons;

public class HttpStatic {

    /**
     * 请求网络的地址
     **/
//    public static final String GEN = "http://120.77.53.19/index.php/Home/";
//    public static final String GEN = "http://www.rongzhouxing.com/index.php/Home/";
//    public static final String GEN = "http://49.232.23.71:88/index.php/home/";
//    public static final String GEN = "http://192.168.31.31/index.php/home/";
//    public static final String GEN = "http://rysy.demo.dlshangcai.com/index.php/home/";
    public static final String GEN = "http://ryks.ychlkj.cn/index.php/home/";
//    public static final String GEN = "http://192.168.110.83/index.php/home/";

    public static final String MD5 = "4EF82E3603825745124695977A46E8C2";

    //=========================================================================================================

    //公共--用户
    public static final String USER_LOGIN = GEN + "User/login";//登录
    public static final String USER_GET_VERIFY_CODE = GEN + "User/get_verify_code";//获取验证码
    public static final String USER_INFO = GEN + "User/info";//用户所有信息
    public static final String USER_PROBATE = GEN + "User/probate";//司机认证
    public static final String USER_PERSONAL = GEN + "User/personal";//修改个人资料
    public static final String USER_CHANGE_PHONE = GEN + "User/edit_phone"; //修改手机号
    public static final String USER_UNREGISTER = GEN + "User/logoff"; //注销
    public static final String USER_GET_PROBATE = GEN + "User/get_probate";//获取认证信息（仅认证通过）
    public static final String DRIVERPICK_COMPLAIN = GEN + "User/complaint";//投诉
    public static final String USER_GET_INFO = GEN + "DriverPick/get_info";//公司信息

    //=========================================================================================================

    //公共--获取基础资料
    public static final String GETBASIC_TAKER_TYPE = GEN + "GetBasic/taker_type";//获取接单模式
    public static final String GETBASIC_ROUTE = GEN + "GetBasic/route";//获取线路（城际拼车）
    public static final String GETBASIC_CAR_TYPE = GEN + "GetBasic/car_type";//获取车型
    public static final String GETBASIC_GET_DEAL = GEN + "GetBasic/get_deal";//获取协议
    public static final String GETBASIC_GET_AGREEMENT_LIST = GEN + "GetBasic/get_agreement_list";//获取协议列表

    //=========================================================================================================

    //公共--用户中心 : 联系我们、设置、24小时电话约车
    public static final String MEMBER_CONTACT_US = GEN + "Member/contact_us";//联系我们
    public static final String MEMBER_FEEDBACK = GEN + "Member/feedback";//意见反馈
    public static final String MEMBER_ABOUT_US = GEN + "Member/about_us";//关于我们

    //=========================================================================================================

    //司机--我的订单
    public static final String DRIVERORDER_LISTS = GEN + "DriverOrder/lists";//订单列表
    public static final String DRIVERORDER_LINE_ON_CAR = GEN + "DriverOrder/line_on_car";//城际拼车 - 线下 - 乘客上车
    public static final String DRIVERORDER_LINE_ON_CAR_OK = GEN + "DriverOrder/line_on_car_ok";//城际拼车 - 线下 - 完成订单
    public static final String DRIVERPICK_GET_ORDER_INFO = GEN + "DriverOrder/get_order_info";//(新)
    public static final String DRIVERPICK_GET_ORDER_LIST_CAN_RECEIVE = GEN + "DriverOrder/order_lists";//可接订单列表
    public static final String DRIVERPICK_GET_ORDER_LIST_ING = GEN + "DriverOrder/order_lists_ing";//进行中的订单列表

    //=========================================================================================================

    //司机--接单
    public static final String DRIVERPICK_GET_BASIC = GEN + "DriverPick/get_basic";//司机接单信息
    public static final String DRIVERPICK_WORKING = GEN + "DriverPick/working";//上班
    public static final String DRIVERPICK_OFF_DUTY = GEN + "DriverPick/off_duty";//下班
    public static final String DRIVERPICK_UPDATE_COORDINATE = GEN + "DriverPick/update_coordinate";//更新坐标
    public static final String DRIVERPICK_GET_POPUP = GEN + "DriverPick/get_popup";//获取接单弹窗消息
    public static final String DRIVERPICK_HANDLE_POPUP = GEN + "DriverPick/handle_popup";//操作弹窗订单(接单/拒绝接单)
    public static final String DRIVERPICK_GET_ORDER = GEN + "DriverPick/get_order";//获取订单信息(城际拼车)-不要了
    public static final String DRIVERPICK_TOWN_GET_ORDER = GEN + "DriverPick/town_get_order";//获取订单信息(市区出行)
    public static final String DRIVERPICK_CANCEL = GEN + "DriverPick/cancel";//取消订单
    public static final String DRIVERPICK_ABOARD = GEN + "DriverPick/aboard";//乘客上车-不要了
    public static final String DRIVERPICK_START_TRIP = GEN + "DriverPick/start_trip";//开始行程
    public static final String DRIVERPICK_SMALL_OK = GEN + "DriverPick/small_ok";//完成小单
    public static final String DRIVERPICK_ORDER_OK = GEN + "DriverPick/order_ok";//完成大单
    public static final String DRIVERPICK_PICK_UP = GEN + "DriverPick/pickUp";//司机取货(提交货物图片)
    public static final String DRIVERPICK_CHECK_CODE = GEN + "DriverPick/check_code";//司机提交验货码
    public static final String DRIVERPICK_CHANGE_ORDER_STATUS = GEN + "DriverPick/change_order_status";//更改订单状态

    //=========================================================================================================

    //司机--我的余额
    public static final String USERBALANCE_DETAILED = GEN + "UserBalance/detailed";//余额明细
    public static final String USERBALANCE_POSTAL = GEN + "UserBalance/postal";//提现

    //=========================================================================================================

    //公共--消息
    public static final String MESSAGE_UNREAD_NUM = GEN + "Message/unread_num";//未读消息
    public static final String MESSAGE_LISTS = GEN + "Message/lists";//消息列表
    public static final String MESSAGE_DETAILS = GEN + "Message/details";//消息详情
    public static final String MESSAGE_DEL = GEN + "Message/del";//删除消息

    //=========================================================================================================
}
