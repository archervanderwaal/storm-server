package me.stormma.config;

/**
 * @description mail config
 * @author stormma
 * @date 2017/8/18
 */
public class MailConfig {

    /**
     * 启用邮件服务，默认关闭
     */
    public static boolean isEnabled = false;

    /**
     * 发件人
     */
    public static String EMAIL_FROM_ADDRESS = "18292817803@163.com";

    /**
     * 发送邮件的用户名，默认是邮件地址@之前的内容
     */
    public static String EMAIL_USER_NAME = "18292817803";

    /**
     * 发送邮件的密码
     */
    public static String EMAIL_PASSWORD = "7803myb";

    /**qq
     * 发送邮件的邮件服务器
     */
    public static String EMAIL_HOST = "smtp.163.com";

    /**
     * 收件人邮箱
     */
    public static String EMAIL_TO_ADDRESS;
}
