package com.example.fortypercent;
/*
 * 
 * Created by BSBE on 2021/10/20.
 * version V03.136 Beta
 * 
 * MainActivity  ��ʾpage
 * 
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import com.example.base.FileHelper;
import com.example.base.HtmlService;
import com.example.base.IsHoliday;
import com.example.base.List_dataAdapter;
import com.example.base.Refresh_run;
import com.example.base.list_data;
import com.example.base.sys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Preconditions;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class MainActivity extends Activity {

	public static Button btn_Main_Note,btn_Main_Main,main_btn_Main_Settings,edit_note,add_build,Inv_btn_qur,Pri_btn_qur;
	public static LinearLayout main_Note,main_Settings,main_Main,Time2_LinearLayout;
	public static EditText note_edit_text,edit_build,Inv_edit,Pri_edit;
	public static TextView note_text,dialog_qur,dialog_cancel,err_dialog_cancel,err_add_text,about_me,version_text;
	public static TextView Nowtime_data,Used_data,Nowdate_data,Time2_text,paoma_light;
	private ImageView netImg;
	private Context mContext = this;
	
	private static List<list_data> mData = null;
    public static List_dataAdapter mAdapter = null;
    private static PullToRefreshListView list_jijin;
    public static int turn_num = 0; 
    public static boolean buytime = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		main_Note = (LinearLayout)this.findViewById(R.id.main_Note);
		main_Settings = (LinearLayout)this.findViewById(R.id.main_Settings);
		main_Main = (LinearLayout)this.findViewById(R.id.Main_menu);
		

		about_me = (TextView)this.findViewById(R.id.about_me_text);
		about_me.setText("��л��ʹ��FourPercent.���app������ԭ������Ϊһλup���Ƽ��Ļ���4%��Ͷ��,�����һЩ�����������ͨ��Ͷ�������ܶ�Ͷ��,"
				+ "�ŵ����ڿ��Ը����Լ�Ԥ��ı���(Ĭ���Ƽ�4%),�����չ��㾻ֵ����һ������ľ�ֵ����4%,����Ƽ��㹺��.���������Ҳ��\"���ܶ�Ͷ\"һ"
				+ "���͸�ͶҪ������Щ,�����мƻ��İѿط���,app����������ʱ��������,���ܻ��и��ָ�����bug,�ٴθ�л��Ը��ʹ����������������.����,��"
				+ "Ȼһ��������Դ��\"�������\"�Ľӿ�,��Ȼ�������Լ�����Ļ������Ϊ׼,��app�����ο�,������ҵ���е��κ�����.���ף�������Ƶĵ�·����Խ��Խ��Ŀɹ۵�����.");
		
		version_text = (TextView)this.findViewById(R.id.version_text);
		version_text.setText("\n--FourPercent V03.13E Beta.  Best For You--");
		paoma_light = (TextView)this.findViewById(R.id.paoma_light);
        paoma_light.setText(getResources().getString(R.string.paoma_light1)); 
		Time2_text = (TextView)this.findViewById(R.id.Time2_text);
		Time2_LinearLayout = (LinearLayout)this.findViewById(R.id.Time2_LinearLayout);
		
		btn_Main_Note = (Button) this.findViewById(R.id.btn_Main_Note);
		btn_Main_Note.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main_Note.setVisibility(LinearLayout.VISIBLE);
				main_Main.setVisibility(LinearLayout.GONE);
				main_Settings.setVisibility(LinearLayout.GONE);
			}
		});
		
		btn_Main_Main = (Button) this.findViewById(R.id.btn_Main_page);
		btn_Main_Main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShowMain();
			}
		});
		
		main_btn_Main_Settings = (Button) this.findViewById(R.id.main_btn_Main_Settings);
		main_btn_Main_Settings.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main_Note.setVisibility(LinearLayout.GONE);
				main_Main.setVisibility(LinearLayout.GONE);
				main_Settings.setVisibility(LinearLayout.VISIBLE);
			}
		});
		
		String data = "";
		note_text = (TextView)this.findViewById(R.id.editText1);
        final String Filename = "Fourprecentnote.text";
        FileHelper Helper2 = new FileHelper(mContext);
        
        try {
			data = Helper2.readFromSD(Filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //String s = note_text.getText().toString();
        note_text.setText(data);
		note_text.setMovementMethod(ScrollingMovementMethod.getInstance());
		edit_note = (Button) this.findViewById(R.id.edit_note);
		edit_note.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final MyDialog dialog = (MyDialog) new MyDialog(mContext);
		        View view = dialog.createView(mContext, R.layout.dialog_layout);
		        note_edit_text = (EditText)view.findViewById(R.id.note_nedit_text);
		        String s = note_text.getText().toString();
		        note_edit_text.setText(s);
		        dialog.show();
		        
		        dialog_qur = (TextView) view.findViewById(R.id.dialog_qur);
		        dialog_qur.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	String s2 = note_edit_text.getText().toString();
						note_text.setText(s2);
						
						FileHelper fHelper = new FileHelper(mContext);
						try {
							fHelper.savaFileToSD(Filename, s2);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						dialog.dismiss();
		            }
		        });
		        
		        dialog_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
		        dialog_cancel.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
						dialog.dismiss();
					}
				});
		
			}
		});
		
		edit_build = (EditText)this.findViewById(R.id.add_edit);
		add_build = (Button) this.findViewById(R.id.add_btn_qur);
		add_build.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(Integer.parseInt(sys.Jijin_num)<12){
						String s2 = edit_build.getText().toString();
						sys.edit_data.fundcode = s2;
						Refresh_run.append_data(s2);
					
					if(sys.add_err==2)
					{
						Toast.makeText(mContext, "��������⡭", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(mContext, "��ӳɹ�", Toast.LENGTH_SHORT).show();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						ShowMain();
					}
				}else
					Toast.makeText(mContext, "12����ͦ����", Toast.LENGTH_SHORT).show();
			}
		});
		
		Inv_edit = (EditText)this.findViewById(R.id.Inv_edit);
		Inv_btn_qur = (Button) this.findViewById(R.id.Inv_btn_qur);
		Inv_btn_qur.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String s2 = Inv_edit.getText().toString();
				if(!s2.equals("")){
					if(checkDigit(s2)){
						//��ȡ���
						Refresh_run.loadJson_settings_data();
						sys.Proportion = s2;
						Refresh_run.saveJson_settings();
					}else
						Toast.makeText(mContext, "������1-9���ڵ�����", Toast.LENGTH_SHORT).show();
				}else
					Toast.makeText(mContext, "��Ҳ̫����", Toast.LENGTH_SHORT).show();
			}
		});
		
		Nowtime_data = (TextView)this.findViewById(R.id.Time_data);
		Used_data = (TextView)this.findViewById(R.id.Used_data);
		Nowdate_data = (TextView)this.findViewById(R.id.Date_data);
		ShowMain();
		new TimeThread().start(); //�����µ��߳�
		
	}
	
	public void ShowMain(){
		main_Note.setVisibility(LinearLayout.GONE);
		main_Main.setVisibility(LinearLayout.VISIBLE);
		main_Settings.setVisibility(LinearLayout.GONE);
		Refresh_run.loaddata();
		Refresh_run.checked_Thread();
		ListView();
		
		Used_data.setText(sys.Principal_used);

		check_nowday(); 
		
		try {
			LeaveSignIn();//������濪ʼ�߳�ǰ���жϴ˿��Ƿ�Ϊ����ʱ��
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		paoma_light();//�����
	}
	
	public void ListView(){
		list_jijin = (PullToRefreshListView) this.findViewById(R.id.jijin_list);
		
		list_jijin.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
 
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});

        mData = new LinkedList<list_data>();
        for(int i = 0;i<Integer.parseInt(sys.Jijin_num);i++){
        	mData.add(new list_data(
        			sys.data_c.jijin[i].name, 
        			sys.data_c.jijin[i].fundcode,
        			sys.data_c.jijin[i].dwjz, 
        			sys.data_c.jijin[i].gsz,
        			sys.data_c.jijin[i].gszzl, 
        			sys.data_c.jijin_buy[i].money, 
        			sys.data_c.jijin_buy[i].jz, 
        			sys.data_c.jijin_buy[i].zzl,
        			sys.data_c.jijin_buy[i].date));
        }
        mAdapter = new List_dataAdapter((LinkedList<list_data>) mData, mContext);
        list_jijin.setAdapter(mAdapter);
        
        list_jijin.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			final MyDialog dialog = (MyDialog) new MyDialog(mContext);
	        View view2 = dialog.createView(mContext, R.layout.img_dialog_layout);
	        String url = "http://j4.dfcfw.com/charts/pic6/"+sys.Jijin_fundcode[position-1]+".png";
	        netImg = (ImageView)view2.findViewById(R.id.netImg);

		    //Glide.with(view2).load(url).into(netImg);
		    Glide.with(view2).load(url + "?key=" + Math.random()).into(netImg);
		    dialog.show();
		}
        
        });
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String> {
		 
		//��̨������
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String str="Added after refresh...I add";
			return str;
		}
 
		//�����Ƕ�ˢ�µ���Ӧ����������addFirst()��addLast()�������¼ӵ����ݼӵ�LISTView��
		//����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		@Override
		protected void onPostExecute(String result) {
			ShowMain();
			// Call onRefreshComplete when the list has been refreshed.
			list_jijin.onRefreshComplete();
 
			super.onPostExecute(result);
		}
	}
	
	public static boolean checkDigit(String digit) {
        String regex = "^[0-9]*$";
        return Pattern.matches(regex, digit);
    }
	
	class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                	msg.what = 1;  //��Ϣ(һ������ֵ)
                	mHandler.sendMessage(msg);// ÿ��1�뷢��һ��msg��mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
 
    //�����߳����洦����Ϣ������UI����
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(buytime){
            	switch (msg.what) {
		            case 1:
		                try {
							LeaveSignIn();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                
		                break;
		            default:
		                break;
		        }
            }else{
            	long sysTime = System.currentTimeMillis();
                CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);

                Time2_LinearLayout.setVisibility(TextView.VISIBLE);
                Time2_text.setText(sysTimeStr); 
    			
                CharSequence sysDataStr = DateFormat.format("yyyy-MM-dd", sysTime);
                Nowdate_data.setText(sysDataStr); 
            }
        }
    };
  
 
    //@RequestMapping("leaveSignIn")
    public void LeaveSignIn() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date nowTime =df.parse(df.format(new Date()));

        Date BeginTime = df.parse("09:30");
        Date EndTime = df.parse("15:00");
        if(sys.day_type==0){
        	boolean isTime = timeCalendar(nowTime, BeginTime, EndTime);
        	 
            if(isTime){
            	buytime = true;
            	long sysTime = System.currentTimeMillis();//��ȡϵͳʱ��
                CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);//ʱ����ʾ��ʽ
                Nowtime_data.setText(sysTimeStr); //����ʱ��
                //Time2_text.setText("");
                Time2_LinearLayout.setVisibility(TextView.GONE);
                CharSequence sysDataStr = DateFormat.format("yyyy-MM-dd", sysTime);//ʱ����ʾ��ʽ
                Nowdate_data.setText(sysDataStr); //����ʱ��
                
            }else{
            	buytime = false;
            	Nowtime_data.setText("���ս����ѽ���"); 
        		//paoma_light.setText("���ս����ѽ�������ȴ��½�����(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)  ���ս����ѽ�������ȴ��½�����(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���) ���ս����ѽ�������ȴ��½�����(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)");
            }
        }else{
        	buytime = false;
            Nowtime_data.setText("������"+sys.day_name); 
            //paoma_light.setText("��ǰ����"+sys.day_name+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)  ��ǰ����"+sys.day_name+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���) ��ǰ����"+sys.day_name+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)");  
        }
        
    }

    public static boolean timeCalendar(Date nowTime, Date BeginTime, Date EndTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar amBegin = Calendar.getInstance();
        amBegin.setTime(BeginTime);

        Calendar amEnd = Calendar.getInstance();
        amEnd.setTime(EndTime);

        if ((date.after(amBegin) && date.before(amEnd))) {
            return true;
        } else {
            return false;
        }
    }
    
    public void check_nowday(){
    	IsHoliday.append_data();
    	IsHoliday.loadJsonString();
    }
    
    public void paoma_light(){
    	if(buytime && sys.day_type==0){
    		paoma_light.setText(getResources().getString(R.string.paoma_light1)); 
    	}else if(!buytime && sys.day_type==0){
    		paoma_light.setText(getResources().getString(R.string.paoma_light2)); 
    	}else{
    		paoma_light.setText("��ǰ����"+sys.day_name+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)  ��ǰ����"+sys.day_name
    				+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���) ��ǰ����"+sys.day_name
    				+"����ͨ��������������ȷ�ϵ����Ƿ���(������������ȷ�ϵĽ����� 9��30-15��00 �ڼ��ڽ���)"); 
    	}
    }
}