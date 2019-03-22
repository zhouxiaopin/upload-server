package cn.gzticc.uploadserver.common;

/**
 * 常量类
 */
public class Const {
    //短信相关url
    public static final String MSG_URL = "http://www.gzticc.cn:8080/ruyue-sms/v1/sms/";

    public interface RecordStatus{
        String DELETE = "00";
        String ABLE = "01";
        String DISABLE = "02";
    }
    public interface WechartConf{
        //饭堂网上订餐小程序ID
        String APPID = "wx5b3ea1376ec170fe";
        //饭堂网上订餐小程序密钥
        String APPSECRET = "eab92ec8053bc4a04c30c3679d5e750c";
        String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?";

    }
}
