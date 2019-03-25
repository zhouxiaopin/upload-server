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
    public interface FtpConf{
        String ftpIp = "192.168.239.211";
        int ftpPort = 21;
        String ftpUser = "ftpuser";
        String ftpPass = "123456";

    }
}
