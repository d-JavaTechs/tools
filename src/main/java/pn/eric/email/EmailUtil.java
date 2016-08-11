package pn.eric.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EmailUtil {

    private static String HOST_NAME = "smtp.iplas.com.cn";
    private static String USER_NAME = "support@iplas.com.cn";
    private static String PASSWORD = "password1";
    private static String FROM_EAMIL = "support@iplas.com.cn";
    private static int SMTP_PORT = -1;

    public static boolean sendEmail(String emailAdds, String subject, String msg, String... attachsPath) {
        HOST_NAME = System.getProperty("hostName", HOST_NAME);
        USER_NAME = System.getProperty("userName", USER_NAME);
        PASSWORD = System.getProperty("fromEmail", PASSWORD);
        FROM_EAMIL = System.getProperty("password", FROM_EAMIL);
        SMTP_PORT = Integer.valueOf(System.getProperty("smtpPort", "-1"));

        HtmlEmail email = new HtmlEmail();
        email.setDebug(true);
        email.setHostName(HOST_NAME);
        email.setAuthenticator(new DefaultAuthenticator(USER_NAME, PASSWORD));
        if (SMTP_PORT != -1)
            email.setSmtpPort(SMTP_PORT);
        try {
            email.setFrom(FROM_EAMIL, "iplas-log"); // 发送方
            String[] eAdds = emailAdds.split(",");
            for (String emailAdd : eAdds) {
                String[] split = emailAdd.split(":");

                if (split.length > 1)
                    email.addTo(split[0], split[1]);// 接收方
                else
                    email.addTo(split[0]); // 接收方
            }
            email.setCharset("GB2312");
            email.setSubject(subject); // 标题
            email.setHtmlMsg(msg.toString());// 内容

            if (attachsPath != null) {
                for (String attachPath : attachsPath) {
                    try {
                        String fileName = attachPath.substring(attachPath.lastIndexOf("/") + 1);

                        EmailAttachment attachment = new EmailAttachment();
                        attachment.setPath(attachPath);
                        attachment.setName(MimeUtility.encodeText(fileName));
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);
                        attachment.setDescription(fileName);

                        email.attach(attachment);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void send(){
        Calendar cal  =  Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
        System.out.println(yesterday);
        String[] attachsPaths= new String[]{String.format("/home/weihu/production-logs/log-tools/standlone/ksservice.master.%s.log",yesterday),
                                            String.format("/home/weihu/production-logs/log-tools/standlone/ksservice.slave.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/kuaisu.master.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/kuaisu.slave.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/kuaisuadmin.master.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/kuaisuadmin.slave.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/soa.master.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/standlone/soa.slave.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/aggregation/ks.aggregation.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/aggregation/kuaisu.aggregation.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/aggregation/kuaisuadmin.aggregation.%s.log",yesterday),
                String.format("/home/weihu/production-logs/log-tools/aggregation/soa.aggregation.%s.log",yesterday)};
        sendEmail("duwupeng@iplas.com.cn",String.format("ksservice,kuaisu,kuaisuadin,soa-dapeng %s 日志統計", yesterday),"大家好,  附件为昨天维护机中的生产日志统计信息",attachsPaths);
    }


    public static void main(String[] args) {
        send();
    }
}