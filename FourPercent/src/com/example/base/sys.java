package com.example.base;

public class sys {
	public static int add_err = 0;  //�����Ƿ�ɹ���־λ
	public static String Jijin_num = "0";  //��������
	public static int MAX = 12;  //��������
	public static String Proportion = "4";  //�������
	public static String Principal_used = "0";  //��ʹ�ñ���
	public static String Jijin_fundcode[] = new String[MAX];  //���������
	public static data_c jata = null;
	
	public static int day_type = 0;
	public static String day_name = "";

	
	public static class data_c {
		public static jijin_c jijin[] = null;
		public static jijin_buy jijin_buy[] = null;

		public data_c() {
			if (jijin == null) {
				jijin = new jijin_c[12];
				for (int i = 0; i < 12; i++)
					jijin[i] = new jijin_c();
			}
			
			if (jijin_buy == null) {
				jijin_buy = new jijin_buy[12];
				for (int i = 0; i < 12; i++)
					jijin_buy[i] = new jijin_buy();
			}
			
		}
		
		/*
		 *����ӻ�����Ϣ 
		 */
		
		public static final class jijin_c {
			public String fundcode = "";
			public String name = "";
			public String jzrq = "";
			public String dwjz = "";
			public String gsz = "";
			public String gszzl = "";
			public String gztime = "";
			//public static final String url = new String("http://fundgz.1234567.com.cn/js/001186.js?rt=1463558676006");
		}
		
		/*
		 *�������ѹ���Ϣ 
		 */
		
		public static final class jijin_buy {
			public String money = new String("--");
			public String date = new String(" - ");
			public String jz = new String("1.2022");
			public String zzl = new String("1.20%");
			//public static final String url = new String("http://fundgz.1234567.com.cn/js/001186.js?rt=1463558676006");
		}
	}
	
	public static final class edit_data {
		public static String fundcode = new String("001186");
		public static String url = new String("http://fundgz.1234567.com.cn/js/001186.js?rt=1463558676006");
	}

	
}