package com.tencent.connect.common;

public class Constants {
  public static final int ACTIVITY_CANCEL = 0;
  
  public static final int ACTIVITY_OK = -1;
  
  public static final String CANCEL_URI = "auth://cancel";
  
  public static final String CLOSE_URI = "auth://close";
  
  public static final int CODE_REQUEST_MAX = 6656;
  
  public static final int CODE_REQUEST_MIN = 5656;
  
  public static final String DEFAULT_PF = "openmobile_android";
  
  public static final String DEFAULT_UIN = "1000";
  
  public static final String DOWNLOAD_URI = "download://";
  
  public static final int ERROR_CONNECTTIMEOUT = -7;
  
  public static final int ERROR_FILE_EXISTED = -11;
  
  public static final int ERROR_HTTPSTATUS_ERROR = -9;
  
  public static final int ERROR_IO = -2;
  
  public static final int ERROR_JSON = -4;
  
  public static final int ERROR_LOCATION_TIMEOUT = -13;
  
  public static final int ERROR_LOCATION_VERIFY_FAILED = -14;
  
  public static final int ERROR_NETWORK_UNAVAILABLE = -10;
  
  public static final int ERROR_NO_SDCARD = -12;
  
  public static final int ERROR_PARAM = -5;
  
  public static final int ERROR_QQVERSION_LOW = -15;
  
  public static final int ERROR_SOCKETTIMEOUT = -8;
  
  public static final int ERROR_UNKNOWN = -6;
  
  public static final int ERROR_URL = -3;
  
  public static final boolean FLAG_DEBUG = true;
  
  public static final String HTTP_GET = "GET";
  
  public static final String HTTP_POST = "POST";
  
  public static final String KEY_ACTION = "key_action";
  
  public static final String KEY_APP_NAME = "oauth_app_name";
  
  public static final String KEY_ERROR_CODE = "key_error_code";
  
  public static final String KEY_ERROR_DETAIL = "key_error_detail";
  
  public static final String KEY_ERROR_MSG = "key_error_msg";
  
  public static final String KEY_PARAMS = "key_params";
  
  public static final String KEY_REQUEST_CODE = "key_request_code";
  
  public static final String KEY_RESPONSE = "key_response";
  
  public static final String LOGIN_INFO = "login_info";
  
  public static final String MOBILEQQ_PACKAGE_NAME = "com.tencent.mobileqq";
  
  public static final String MSG_CONNECTTIMEOUT_ERROR = "网络连接超时!";
  
  public static final String MSG_IMAGE_ERROR = "图片读取失败，请检查该图片是否有效";
  
  public static final String MSG_IO_ERROR = "网络连接异常，请检查后重试!";
  
  public static final String MSG_JSON_ERROR = "服务器返回数据格式有误!";
  
  public static final String MSG_LOCATION_TIMEOUT_ERROR = "定位超时，请稍后再试或检查网络状况！";
  
  public static final String MSG_LOCATION_VERIFY_ERROR = "定位失败，验证定位码错误！";
  
  public static final String MSG_NOT_CALL_ON_MAIN_THREAD = "没有在主线程调用！";
  
  public static final String MSG_NO_SDCARD = "检测不到SD卡，无法发送语音！";
  
  public static final String MSG_OPEN_BROWSER_ERROR = "打开浏览器失败!";
  
  public static final String MSG_PARAM_APPSHARE_TOO_LOW = "手Q版本过低，应用分享只支持手Q5.0及其以上版本";
  
  public static final String MSG_PARAM_ERROR = "传入参数有误!";
  
  public static final String MSG_PARAM_IMAGE_ERROR = "纯图分享，imageUrl 不能为空";
  
  public static final String MSG_PARAM_IMAGE_URL_FORMAT_ERROR = "非法的图片地址!";
  
  public static final String MSG_PARAM_IMAGE_URL_MUST_BE_LOCAL = "手Q版本过低，纯图分享不支持网路图片";
  
  public static final String MSG_PARAM_NULL_ERROR = "传入参数不可以为空";
  
  public static final String MSG_PARAM_QQ_VERSION_ERROR = "低版本手Q不支持该项功能!";
  
  public static final String MSG_PARAM_TARGETURL_ERROR = "targetUrl有误";
  
  public static final String MSG_PARAM_TARGETURL_NULL_ERROR = "targetUrl为必填项，请补充后分享";
  
  public static final String MSG_PARAM_TITLE_NULL_ERROR = "title不能为空!";
  
  public static final String MSG_PARAM_VERSION_TOO_LOW = "手Q版本过低，请下载安装最新版手Q";
  
  public static final String MSG_PUBLISH_VIDEO_ERROR = "请选择有效的视频文件";
  
  public static final String MSG_SHARE_GETIMG_ERROR = "获取分享图片失败!";
  
  public static final String MSG_SHARE_NOSD_ERROR = "分享图片失败，检测不到SD卡!";
  
  public static final String MSG_SHARE_TO_QQ_ERROR = "分享的手机QQ失败!";
  
  public static final String MSG_SHARE_TYPE_ERROR = "请选择支持的分享类型";
  
  public static final String MSG_SOCKETTIMEOUT_ERROR = "网络连接超时!";
  
  public static final String MSG_UNKNOWN_ERROR = "未知错误!";
  
  public static final String MSG_URL_ERROR = "访问url有误!";
  
  public static final String PACKAGE_QIM = "com.tencent.qim";
  
  public static final String PACKAGE_QQ = "com.tencent.mobileqq";
  
  public static final String PACKAGE_QQ_PAD = "com.tencent.minihd.qq";
  
  public static final String PACKAGE_QZONE = "com.qzone";
  
  public static final String PACKAGE_TIM = "com.tencent.tim";
  
  public static final String PARAM_ACCESS_TOKEN = "access_token";
  
  public static final String PARAM_APP_ID = "appid";
  
  public static final String PARAM_CLIENT_ID = "client_id";
  
  public static final String PARAM_CONSUMER_KEY = "oauth_consumer_key";
  
  public static final String PARAM_EXPIRES_IN = "expires_in";
  
  public static final String PARAM_EXPIRES_TIME = "expires_time";
  
  public static final String PARAM_HOPEN_ID = "hopenid";
  
  public static final String PARAM_KEY_STR = "keystr";
  
  public static final String PARAM_KEY_TYPE = "keytype";
  
  public static final String PARAM_OPEN_ID = "openid";
  
  public static final String PARAM_PLATFORM = "platform";
  
  public static final String PARAM_PLATFORM_ID = "pf";
  
  public static final String PARAM_SCOPE = "scope";
  
  public static final String PREFERENCE_PF = "pfStore";
  
  public static final String QQ_APPID = "100686848";
  
  public static final int REQUEST_API = 10100;
  
  public static final int REQUEST_APPBAR = 10102;
  
  public static final int REQUEST_AVATER = 11102;
  
  public static final int REQUEST_LOGIN = 11101;
  
  public static final int REQUEST_OLD_QZSHARE = 11104;
  
  public static final int REQUEST_OLD_SHARE = 11103;
  
  public static final int REQUEST_QQ_FAVORITES = 10105;
  
  public static final int REQUEST_QQ_SHARE = 10103;
  
  public static final int REQUEST_QZONE_SHARE = 10104;
  
  public static final int REQUEST_SEND_TO_MY_COMPUTER = 10106;
  
  public static final int REQUEST_SHARE_TO_TROOP_BAR = 10107;
  
  public static final int REQUEST_SOCIAL_API = 11105;
  
  public static final int REQUEST_SOCIAL_H5 = 11106;
  
  public static final String SDK_BUILD = "5575";
  
  public static final String SDK_QUA = "V1_AND_OpenSDK_3.3.0.lite_1077_RDM_B";
  
  public static final String SDK_VERSION = "3.3.0.lite";
  
  public static final String SDK_VERSION_REPORT = "OpenSdk_3.3.0.lite";
  
  public static final String SDK_VERSION_STRING = "Android_SDK_3.3.0.lite";
  
  public static final String SIGNATRUE_QZONE = "ec96e9ac1149251acbb1b0c5777cae95";
  
  public static final String SOURCE_QQ = "QQ";
  
  public static final String SOURCE_QZONE = "qzone";
  
  public static final String STR_EMPTY = "";
  
  public static final int UI_ACTIVITY = 1;
  
  public static final int UI_CHECK_TOKEN = 3;
  
  public static final int UI_DIALOG = 2;
  
  public static final int UI_DOWNLOAD_QQ = 4;
  
  public static final int UI_NONE = -1;
  
  public static final String VIA_ACT_TYPE_EIGHTEEN = "18";
  
  public static final String VIA_ACT_TYPE_FIVE = "5";
  
  public static final String VIA_ACT_TYPE_NINETEEN = "19";
  
  public static final String VIA_ACT_TYPE_SEVEN = "7";
  
  public static final String VIA_ACT_TYPE_THREE = "3";
  
  public static final String VIA_ACT_TYPE_TWENTY_EIGHT = "28";
  
  public static final String VIA_BIND_GROUP = "ANDROIDSDK.BINDGROUP.XX";
  
  public static final String VIA_CALL_SOURCE_H5 = "2";
  
  public static final String VIA_CALL_SOURCE_SQ = "1";
  
  public static final String VIA_JOIN_GROUP = "ANDROIDQQ.JOININGROUP.XX";
  
  public static final String VIA_MAKE_FRIEND = "ANDROIDQQ.MAKEAFRIEND.XX";
  
  public static final String VIA_NO_VALUE = "0";
  
  public static final String VIA_REPORT_TYPE_BIND_GROUP = "18";
  
  public static final String VIA_REPORT_TYPE_DATALINE = "22";
  
  public static final String VIA_REPORT_TYPE_JOININ_GROUP = "13";
  
  public static final String VIA_REPORT_TYPE_MAKE_FRIEND = "14";
  
  public static final String VIA_REPORT_TYPE_QQFAVORITES = "21";
  
  public static final String VIA_REPORT_TYPE_SET_AVATAR = "12";
  
  public static final String VIA_REPORT_TYPE_SHARE_TO_QQ = "10";
  
  public static final String VIA_REPORT_TYPE_SHARE_TO_QZONE = "11";
  
  public static final String VIA_REPORT_TYPE_SHARE_TO_TROOPBAR = "23";
  
  public static final String VIA_REPORT_TYPE_SSO_LOGIN = "1";
  
  public static final String VIA_REPORT_TYPE_START_GROUP = "17";
  
  public static final String VIA_REPORT_TYPE_START_WAP = "16";
  
  public static final String VIA_REPORT_TYPE_WPA_STATE = "15";
  
  public static final String VIA_RESULT_FAIL = "1";
  
  public static final String VIA_RESULT_SUCCESS = "0";
  
  public static final String VIA_SDK = "2";
  
  public static final String VIA_SET_AVATAR = "ANDROIDSDK.SETAVATAR.XX";
  
  public static final String VIA_SET_AVATAR_SUCCEED = "ANDROIDSDK.SETAVATAR.SUCCEED";
  
  public static final String VIA_SHARE_TO_QQ = "ANDROIDQQ.SHARETOQQ.XX";
  
  public static final String VIA_SHARE_TO_QZONE = "ANDROIDQQ.SHARETOQZ.XX";
  
  public static final String VIA_SHARE_TO_TROOPBAR = "ANDROIDSDK.SHARETOTROOPBAR.XX";
  
  public static final String VIA_SHARE_TYPE_APP = "4";
  
  public static final String VIA_SHARE_TYPE_IMAGE = "2";
  
  public static final String VIA_SHARE_TYPE_IMAGE_TEXT = "1";
  
  public static final String VIA_SHARE_TYPE_INFO = "6";
  
  public static final String VIA_SHARE_TYPE_MUSIC = "3";
  
  public static final String VIA_SHARE_TYPE_PUBLISHMOOD = "7";
  
  public static final String VIA_SHARE_TYPE_PUBLISHVIDEO = "8";
  
  public static final String VIA_SHARE_TYPE_TEXT = "5";
  
  public static final String VIA_SSO_LOGIN = "2";
  
  public static final String VIA_START_WAP = "ANDROIDSDK.STARTWPA.XX";
  
  public static final String VIA_TO_TYPE_QQ_DISCUSS_GROUP = "3";
  
  public static final String VIA_TO_TYPE_QQ_FRIEND = "1";
  
  public static final String VIA_TO_TYPE_QQ_GROUP = "1";
  
  public static final String VIA_TO_TYPE_QZONE = "4";
  
  public static final String VIA_WAP_STATE = "ANDROIDSDK.WPASTATE.XX";
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\common\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */