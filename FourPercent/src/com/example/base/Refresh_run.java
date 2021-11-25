package com.example.base;
/*
 * 
 * Created by BSBE on 2021/10/20.
 * version V03.136 Beta
 * 
 * Refresh_run  刷新存取各条数据
 * 
 */
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.base.FileHelper;
import com.example.base.HtmlService;
import com.example.base.sys;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
public class Refresh_run{
	
	public static boolean RunThread = false;
    public static int turn_num = 0; 
    public static Context context;
    public static boolean read_settings_err = false;
    
    /*
     * part 1 
	 * 每5秒钟从接口调用更新一次基金信息
	 */
	public static void checked_Thread(){	
		new Thread() {
			public void run(){	
			while(!RunThread){				
				try {
					Refresh();
					Thread.sleep(5*1000);
					loaddata();
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}	
		}
		}.start();
	}
	
	public static void Refresh(){
		loadJson_name_list();  //先得知有多少已存基金及对应基金号
		String data;
		for(int i=0;i<=Integer.parseInt(sys.Jijin_num);i++){
			data = sys.Jijin_fundcode[i];
			
			String url1 = "http://fundgz.1234567.com.cn/js/";
			String url2 = ".js?rt=1463558676006";
			String url3 = url1 + data + url2;
			if(data!=null)
				reload_urldata(url3);
		}
	}
	
	public static void reload_urldata(final String url){
        try {
            String html = HtmlService.getHtml(url);
            int start = html.indexOf("(");
            int end = html.lastIndexOf(")");
            String substring = html.substring(start+1, end);
            resave_urlJsonString(substring,getJsonString(substring,"fundcode"));  
        } catch (Exception e) {
        }
    }
	
	//resave <fundcode>.xml
	public static void resave_urlJsonString(String substring,String fundcode){
		final String Filename = fundcode+".xml";
        FileHelper fHelper = new FileHelper();
		try {
			fHelper.savaFileToSD(Filename, substring);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	/*
	 * part 2 
	 * 建仓时添加的基金
	 * 每个基金有以基金号为名称的单独xml文件
	 * 另有一个存放基金总数和每条基金号的 namelist.xml文件
	 */
	
	public static void append_data(String fundcode){
		String url1 = "http://fundgz.1234567.com.cn/js/";
		String url2 = ".js?rt=1463558676006";

		sys.edit_data.url = url1 + fundcode + url2;
		jijin_data(sys.edit_data.url);
	}
	
	public static void jijin_data(final String url){
        
    	new Thread() {
			public void run(){				
				try {
					sys.add_err = 0;
		            String html = HtmlService.getHtml(url);
		            int start = html.indexOf("(");
		            int end = html.lastIndexOf(")");
		            String substring = html.substring(start+1, end);
		            Log.i("substring",getJsonString(substring,"name"));
		            //getJsonString(substring);   //从表中读具体数据
		            //ListView();  //Main show
		            saveJsonString(substring,getJsonString(substring,"fundcode"));  
		        } catch (Exception e) {
		        	sys.add_err = 2;
		        }     
			}
	}.start();
    	
    }
	
	public static void loaddata(){
		loadJson_name_list();  //先得知有多少已存基金及对应基金号
		String data;
		for(int i=0;i<=Integer.parseInt(sys.Jijin_num);i++){
			data = sys.Jijin_fundcode[i];
			turn_num = i;
			loadJsonString(data);   //重新获取每个 基金
			//MainActivity.ListView();
		}
		if(read_settings_err && !sys.Jijin_num.equals("0"))
			saveJson_bought_data();
		loadJson_settings_data();//获取设置参数
		loadJson_bought_data();  //获取每个 基金中已买的信息
		saveJson_bought_data();
	}
	

	public static void data_getJsonString(String substring){
        String bfundcode = getJsonString(substring,"fundcode");
        String bname = getJsonString(substring,"name");
        String bjzrq = getJsonString(substring,"jzrq");
        String bdwjz = getJsonString(substring,"dwjz");
        String bgsz = getJsonString(substring,"gsz");
        String bgszzl = getJsonString(substring,"gszzl");
        String bgztime = getJsonString(substring,"gztime");
        
        if (sys.jata == null)
        	sys.jata = new sys.data_c();
        
        if(bfundcode!=null || bname!=null || bjzrq!=null || bdwjz!=null || bgsz!=null 
        		|| bgszzl!=null || bgztime!=null){
        	sys.data_c.jijin[turn_num].fundcode= bfundcode;
        	sys.data_c.jijin[turn_num].name= bname;
        	sys.data_c.jijin[turn_num].jzrq= bjzrq;
        	sys.data_c.jijin[turn_num].dwjz= bdwjz;
        	sys.data_c.jijin[turn_num].gsz= bgsz;
        	sys.data_c.jijin[turn_num].gszzl= bgszzl;
        	sys.data_c.jijin[turn_num].gztime= bgztime;
        }
	}
	
	public static String getJsonString(String jsonstr,String key) {
        String regex = "\""+key+"\":\"(.*?)\"";
        Matcher matcher = Pattern.compile(regex).matcher(jsonstr);
        while (matcher.find()) {
            //set.add(matcher.group(1));
            return matcher.group(1);
        }
        return null;
    }
	
	//save <fundcode>.xml
	public static void saveJsonString(String substring,String fundcode){
		int allow=0;
		String Jijin_fundcode_s;
		
		final String Filename = fundcode+".xml";
        FileHelper fHelper = new FileHelper();
		try {
			Log.i("err-equse-0",sys.Jijin_num);
			for(int i = 0;i<Integer.parseInt(sys.Jijin_num);i++){
				Jijin_fundcode_s = sys.Jijin_fundcode[i];
				if(Jijin_fundcode_s.equals(sys.edit_data.fundcode)){
					allow=2;
					Log.i("err-equse-1",String.valueOf(allow));
					break;
				}else{
					allow=0;
					Log.i("err-equse-2",String.valueOf(allow));
				}
			}
			if(allow!=2&&fundcode!=null){
				sys.Jijin_num = String.valueOf(Integer.parseInt(sys.Jijin_num) + 1);  //基金总数+1
	        	sys.Jijin_fundcode[Integer.parseInt(sys.Jijin_num)-1] = fundcode;  //存入数组
				saveJson_name_list(); //保存name_list
			}
			fHelper.savaFileToSD(Filename, substring);
			saveJson_bought_data();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	public static void getJsonString2(String data){
        sys.Jijin_num = getJsonString(data,"Jijin_num");
        for(int i=0;i<Integer.parseInt(sys.Jijin_num);i++){
        	sys.Jijin_fundcode[i]= getJsonString(data,"fundcode"+(i+1));
        }
	}
	
	//从<fundcode>.xml中提数据
	public static void loadJsonString(String fundcode){
		String data = "";
		final String Filename = fundcode+".xml";
        FileHelper Helper2 = new FileHelper();
        try {
			data = Helper2.readFromSD(Filename);
			data_getJsonString(data);//////
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
	}
	
	//name_list：max+fundcode*i 拼接  save name_list
	public static void saveJson_name_list(){
		String substring = null,substring1 = null,substring2 = null,substring3 = null;
		substring1 = "{\""+"Jijin_num"+"\":\""+sys.Jijin_num+"\"";
		for(int i=1;i<=Integer.parseInt(sys.Jijin_num);i++){
			if(substring2 != null){
				substring2 +="\""+ ",\""+"fundcode"+i+"\":\""+sys.Jijin_fundcode[i-1];
			}else
				substring2 = ",\""+"fundcode"+i+"\":\""+sys.Jijin_fundcode[i-1];
		}
		substring3 = "\"}";
		substring = substring1 + substring2 + substring3;
		final String namelist_Filename = "namelist.xml";
        FileHelper fHelper = new FileHelper();
		try {
			fHelper.savaFileToSD(namelist_Filename,substring);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	//从name_list中提数据
	public static void loadJson_name_list(){
		String data = "";
		final String namelist_Filename = "namelist.xml";
        FileHelper fHelper = new FileHelper();
        try {
			data = fHelper.readFromSD(namelist_Filename);
			getJsonString2(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
	}
	
	/*
	 * part 3 
	 * 存储和读取已购买的基金数据：购入金额、净值、日期、比例
	 */
	public static void saveJson_bought_data(){
		String substring = null,substring1 = null,substring2 = null,substring3 = null;
		substring1 = "{\"";
		for(int i=1;i<=Integer.parseInt(sys.Jijin_num);i++){
			if(sys.data_c.jijin_buy[i-1].zzl!=null){
				DecimalFormat df = new DecimalFormat("0.00%");
        		double bought_jingzhi = Double.parseDouble(sys.data_c.jijin_buy[i-1].jz);
        		double gs_jingzhi = Double.parseDouble(sys.data_c.jijin[i-1].gsz);
        		double result = 0;
        		result = (gs_jingzhi - bought_jingzhi) / bought_jingzhi;
        		sys.data_c.jijin_buy[i-1].zzl= String.valueOf(df.format(result));
        	}else{
        		sys.data_c.jijin_buy[i-1].money = "--";
        		sys.data_c.jijin_buy[i-1].date = " - ";
        		sys.data_c.jijin_buy[i-1].jz = "1.2022";
        		sys.data_c.jijin_buy[i-1].zzl = "1.20%";
        		
        	}
			

			if(substring2 != null){
				substring2 +="\",\""+"money"+i+"\":\""+sys.data_c.jijin_buy[i-1].money
						+ "\",\""+"date"+i+"\":\""+sys.data_c.jijin_buy[i-1].date
						+ "\",\""+"jz"+i+"\":\""+sys.data_c.jijin_buy[i-1].jz
						+ "\",\""+"zzl"+i+"\":\""+sys.data_c.jijin_buy[i-1].zzl;
			}else{
				substring2 = "money"+i+"\":\""+sys.data_c.jijin_buy[i-1].money 
						+ "\",\""+"date"+i+"\":\""+sys.data_c.jijin_buy[i-1].date
						+ "\",\""+"jz"+i+"\":\""+sys.data_c.jijin_buy[i-1].jz
						+ "\",\""+"zzl"+i+"\":\""+sys.data_c.jijin_buy[i-1].zzl;
			}
		}
		substring3 = "\"}";
		substring = substring1 + substring2 + substring3;
		final String namelist_Filename = "boughtdata.xml";
        FileHelper fHelper = new FileHelper();
		try {
			fHelper.savaFileToSD(namelist_Filename,substring);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void loadJson_bought_data(){
		String data = "";
		final String namelist_Filename = "boughtdata.xml";
        FileHelper fHelper = new FileHelper();
        try {
			data = fHelper.readFromSD(namelist_Filename);
			getJsonString3(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
	}
	
	public static float p = 0;
	public static void getJsonString3(String data){
		float result = 0;
        for(int i=0;i<Integer.parseInt(sys.Jijin_num);i++){
        	sys.data_c.jijin_buy[i].money= getJsonString(data,"money"+(i+1));
        	sys.data_c.jijin_buy[i].date= getJsonString(data,"date"+(i+1));
        	sys.data_c.jijin_buy[i].jz= getJsonString(data,"jz"+(i+1));
        	sys.data_c.jijin_buy[i].zzl= getJsonString(data,"zzl"+(i+1));
        	
        	if(sys.data_c.jijin_buy[i].money!=null && !sys.data_c.jijin_buy[i].money.equals("--")){
        		p = Float.parseFloat(sys.data_c.jijin_buy[i].money);
        		result = result + p;
        		sys.Principal_used = String.valueOf(result);
    	    	if(sys.Proportion==null && sys.Principal_used==null && sys.Jijin_num!="0"){
    	    		loadJson_settings_data();
    				saveJson_settings();
    			}else
    	    		saveJson_settings();
        	}else{
        		sys.data_c.jijin_buy[i].money = "--";
        		//result = result + p;
        		break;
        	}
        }
	}
	
	public static String getPercentFormat(double d,int IntegerDigits,int FractionDigits){
		  NumberFormat nf = java.text.NumberFormat.getPercentInstance(); 
		  nf.setMaximumIntegerDigits(IntegerDigits);//小数点前保留几位
		  nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
		  String str = nf.format(d);
		  return str;
	}
	
	/*
	 * part 4
	 * 存储和读取本地设置的参数
	 */
	public static void saveJson_settings(){
		String substring;
		if(sys.Proportion==null)
			sys.Proportion = "4";
		substring ="{\"" + "Proportion"+"\":\""+sys.Proportion + "\""
		+ ",\""+"Principal_used"+"\":\""+sys.Principal_used + "\"}";
		final String namelist_Filename = "settings.xml";
        FileHelper fHelper = new FileHelper();
		try {
			fHelper.savaFileToSD(namelist_Filename,substring);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void loadJson_settings_data(){
		String data = "";
		final String Filename = "settings.xml";
        FileHelper fHelper = new FileHelper();
        try {
        	read_settings_err = false;
			data = fHelper.readFromSD(Filename);
			getJsonString4(data);
		} catch (IOException e1) {
			read_settings_err = true;
			// TODO Auto-generated catch block
		}
	}
	
	public static void getJsonString4(String data){
		sys.Proportion= getJsonString(data,"Proportion");
    	sys.Principal_used= getJsonString(data,"Principal_used");
	}

}