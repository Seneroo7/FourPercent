package com.example.base;

public class list_data {
    private String afund_Name; //��������
    private String afund_Num;  //�����
    private String afund_netvalue;  //����ֵ
    private String afund_netvalue_estimate;  //��ֵ����  new
    private String afund_netvalue_estimate_ratio;  //���������
    
    
    private String last_buy_price;  //�ϴβ�����
    private String last_buy_netvalue;  //�ϴι��뾻ֵ
    private String last_advices_netvalue;  //�Աȱ���
    private String last_buy_date;  //��������  new

    public list_data() {
    }

    public list_data(String aName, String aNum, String ajzzz, String ajzgs, String agsbl, String lst_Num
    		,String lst_jzzz,String lst_jzbl,String lst_buy_date) {
        this.afund_Name = aName;
        this.afund_Num = aNum;
        this.afund_netvalue = ajzzz;
        this.afund_netvalue_estimate = ajzgs;
        this.afund_netvalue_estimate_ratio = agsbl;
        
        this.last_buy_price = lst_Num;
        this.last_buy_netvalue = lst_jzzz;
        this.last_advices_netvalue = lst_jzbl;
        this.last_buy_date = lst_buy_date;
    }

    public String getafund_Name() {
        return afund_Name;
    }

    public String getafund_Num() {
        return afund_Num;
    }

    public String getafund_netvalue() {
        return afund_netvalue;
    }
    
    public String getafund_netvalue_estimate() {
        return afund_netvalue_estimate;
    }
    
    public String getafund_netvalue_estimate_ratio() {
        return afund_netvalue_estimate_ratio;
    }
    
    public String getlast_buy_price() {
        return last_buy_price;
    }

    public String getlast_buy_netvalue() {
        return last_buy_netvalue;
    }
    
    public String getlast_advices_netvalue() {
        return last_advices_netvalue;
    }
    
    public String getlast_buy_date() {
        return last_buy_date;
    }

    public void setafund_Name(String aName) {
        this.afund_Name = aName;
    }

    public void setafund_Num(String aNum) {
        this.afund_Num = aNum;
    }

    public void setafund_netvalue(String ajzzz) {
        this.afund_netvalue = ajzzz;
    }
    
    public void setafund_netvalue_estimate(String ajzgs) {
        this.afund_netvalue_estimate = ajzgs;
    }
    
    public void setafund_netvalue_estimate_ratio(String agsbl) {
        this.afund_netvalue_estimate_ratio = agsbl;
    }
    
    public void setlast_buy_price(String lst_Num) {
        this.last_buy_price = lst_Num;
    }

    public void setlast_buy_netvalue(String lst_jzzz) {
        this.last_buy_netvalue = lst_jzzz;
    }
    
    public void setlast_advices_netvalue(String lst_jzbl) {
        this.last_advices_netvalue = lst_jzbl;
    }
    
    public void setlast_buy_date(String lst_buy_date) {
        this.last_buy_date = lst_buy_date;
    }
}