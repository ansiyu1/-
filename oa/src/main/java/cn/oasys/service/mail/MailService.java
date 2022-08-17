package cn.oasys.service.mail;

public interface MailService {
    void pushmail(String mailAccount, String password, String nextToken, String mailUserName, String mailTitle, String mailContent, String attachmentPath, String attachmentName);
}
