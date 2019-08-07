package com.xl.cloud.phoneBean;

/**
 * 电子邮箱 QQ邮箱通讯录对象
 * @author hongyongda
 *
 */
public class QmailContacts {
    //邮箱地址
    private String mail;
    
    //联系人名称
    private String cName;
    
    //md5加密
    private String md5;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    @Override
    public String toString(){
        return "Contacts [mail=" + mail 
                + ", cName=" + cName
                + ", md5=" + md5 + "]";
    }
}
