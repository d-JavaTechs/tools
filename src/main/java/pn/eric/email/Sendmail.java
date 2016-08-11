package pn.eric.email;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 * @ClassName: Sendmail
 * @Description: 发送Email
 * @author: Eric Du
 * @date: 2015-1-12 下午9:42:56
 *
 */
public class Sendmail {
    /**
     * @param args
     * @throws Exception
     */
    public static void send() throws Exception {

        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.exmail.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        Message message = createAttachMail(session);

        Transport transport = session.getTransport("smtp");
        // smtp验证，就是你用来发邮件的邮箱用户名密码 需要設置第三方郵件
//        transport.connect("smtp.qq.com", "865361183@qq.com", "oedvaehekiaebbhd");
        transport.connect("smtp.exmail.qq.com", "duwupeng@iplas.com.cn", "2010Dwp123");
        //5、发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * @Method: createAttachMail
     * @Description: 创建一封带附件的邮件
     * @Anthor:孤傲苍狼
     *
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createAttachMail(Session session) throws Exception{
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress("duwupeng@iplas.com.cn"));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("duwupeng@iplas.com.cn"));

        Calendar cal  =  Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
        System.out.println(yesterday);

        //邮件标题
        message.setSubject(String.format("ksservice,kuaisu,kuaisuadin,soa-dapeng %s 日志統計", yesterday));

        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();


        text.setContent("大家好, \n   附件为昨天维护机中的生产日志统计信息", "text/html;charset=UTF-8");

        //创建邮件附件

        MimeBodyPart attachKsMaster = new MimeBodyPart();
        DataHandler dhKsMaster = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/ksservice.master.%s.log",yesterday)));
        attachKsMaster.setDataHandler(dhKsMaster);
        attachKsMaster.setFileName(dhKsMaster.getName());

        MimeBodyPart attachKsSlave = new MimeBodyPart();
        DataHandler dhKsSlave = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/ksservice.slave.%s.log",yesterday)));
        attachKsSlave.setDataHandler(dhKsSlave);
        attachKsSlave.setFileName(dhKsSlave.getName());

        MimeBodyPart attachKuaisuMaster = new MimeBodyPart();
        DataHandler dhKuaisuMaster = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/kuaisu.master.%s.log",yesterday)));
        attachKuaisuMaster.setDataHandler(dhKuaisuMaster);
        attachKuaisuMaster.setFileName(dhKuaisuMaster.getName());

        MimeBodyPart attachKuaisuSlave= new MimeBodyPart();
        DataHandler dhKuaisuSlave = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/kuaisu.slave.%s.log",yesterday)));
        attachKuaisuSlave.setDataHandler(dhKuaisuSlave);
        attachKuaisuSlave.setFileName(dhKuaisuSlave.getName());


        MimeBodyPart attachKuaisuAdminMaster = new MimeBodyPart();
        DataHandler dhKuaisuAdminMaster = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/kuaisuadmin.master.%s.log",yesterday)));
        attachKuaisuAdminMaster.setDataHandler(dhKuaisuAdminMaster);
        attachKuaisuAdminMaster.setFileName(dhKuaisuAdminMaster.getName());

        MimeBodyPart attachKuaisuAdminSlave = new MimeBodyPart();
        DataHandler dhKuaisuAdminSlave = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/kuaisuadmin.slave.%s.log",yesterday)));
        attachKuaisuAdminSlave.setDataHandler(dhKuaisuAdminSlave);
        attachKuaisuAdminSlave.setFileName(dhKuaisuAdminSlave.getName());

        MimeBodyPart attachSoaMaster = new MimeBodyPart();
        DataHandler dhSoaMaster = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/soa.master.%s.log",yesterday)));
        attachSoaMaster.setDataHandler(dhSoaMaster);
        attachSoaMaster.setFileName(dhSoaMaster.getName());

        MimeBodyPart attachSoaSlave = new MimeBodyPart();
        DataHandler dhSoaSlave = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/standlone/soa.slave.%s.log",yesterday)));
        attachSoaSlave.setDataHandler(dhSoaSlave);
        attachSoaSlave.setFileName(dhSoaSlave.getName());




        MimeBodyPart attachKsAggregation = new MimeBodyPart();
        DataHandler dhKsAggregation = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/aggregation/ks.aggregation.%s.log",yesterday)));
        attachKsAggregation.setDataHandler(dhKsAggregation);
        attachKsAggregation.setFileName(dhKsAggregation.getName());


        MimeBodyPart attachKuaisuAdminAggregation = new MimeBodyPart();
        DataHandler dhKuaisuAdminAggregation = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/aggregation/kuaisu.aggregation.%s.log",yesterday)));
        attachKuaisuAdminAggregation.setDataHandler(dhKuaisuAdminAggregation);
        attachKuaisuAdminAggregation.setFileName(dhKuaisuAdminAggregation.getName());

        MimeBodyPart attachKuaisuAggregation = new MimeBodyPart();
        DataHandler dhKuaisuAggregation = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/aggregation/kuaisuadmin.aggregation.%s.log",yesterday)));
        attachKuaisuAggregation.setDataHandler(dhKuaisuAggregation);
        attachKuaisuAggregation.setFileName(dhKuaisuAggregation.getName());

        MimeBodyPart attachSoaAggregation = new MimeBodyPart();
        DataHandler dhSoaAggregation = new DataHandler(new FileDataSource(String.format("C:/Users/Shadow/Desktop/日志/aggregation/soa.aggregation.%s.log",yesterday)));
        attachSoaAggregation.setDataHandler(dhSoaAggregation);
        attachSoaAggregation.setFileName(dhSoaAggregation.getName());



        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);

        mp.addBodyPart(attachKsMaster);
        mp.addBodyPart(attachKsSlave);

        mp.addBodyPart(attachKuaisuAdminMaster);
        mp.addBodyPart(attachKuaisuAdminSlave);

        mp.addBodyPart(attachKuaisuSlave);
        mp.addBodyPart(attachKuaisuMaster);

        mp.addBodyPart(attachSoaMaster);
        mp.addBodyPart(attachSoaSlave);

        mp.addBodyPart(attachKuaisuMaster);
        mp.addBodyPart(attachKsAggregation);
        mp.addBodyPart(attachKuaisuAdminAggregation);
        mp.addBodyPart(attachSoaAggregation);

        mp.setSubType("mixed");
        message.setContent(mp);
        message.saveChanges();
        //将创建的Email写入到E盘存储
        message.writeTo(new FileOutputStream("C:/Users/Shadow/Desktop/日志/temp/attachMail.eml"));
        //返回生成的邮件
        return message;
    }


    public static void main(String[] args) throws Exception{
        send();
    }
}
