package com.example.base;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Pattern;

import com.example.fortypercent.MainActivity;
import com.example.fortypercent.MyDialog;
import com.example.fortypercent.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by BSBE on 2021/10/20.
 */
public class List_dataAdapter extends BaseAdapter {

    private LinkedList<list_data> mData;
    private Context mContext;
    private LayoutInflater inflater;
    public static EditText buy_money_edit,buy_jz_edit;
    
    public List_dataAdapter(LinkedList<list_data> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.jijin_list,parent,false);
        
        String hightitem = "1.20%";
        for(int i=0;i<Integer.parseInt(sys.Jijin_num);i++){
        	if(sys.data_c.jijin_buy[i].zzl==null){
//        		Refresh_run.saveJson_bought_data();
        		sys.data_c.jijin_buy[i-1].money = "--";
        		sys.data_c.jijin_buy[i-1].date = " - ";
        		sys.data_c.jijin_buy[i-1].jz = "1.2022";
        		sys.data_c.jijin_buy[i-1].zzl = "1.20%";
        	}else{
        		hightitem = sys.data_c.jijin_buy[i].zzl;
        	}
			float a = changePercentToPoint(hightitem);
			int b = (int)a;
			if(position == i){
	        	if(b < (-Integer.parseInt(sys.Proportion))){
	            	convertView.setBackgroundResource(R.drawable.item_bg);
	            	break;
	        	} else {
	        		convertView.setBackgroundColor(Color.TRANSPARENT);
	            }
			}
        }
        
        TextView txt_aName = (TextView) convertView.findViewById(R.id.aName);
        TextView txt_aNum = (TextView) convertView.findViewById(R.id.aNum);
        TextView txt_adwjz = (TextView) convertView.findViewById(R.id.adwjz);
        TextView txt_aagsz = (TextView) convertView.findViewById(R.id.agsz);
        TextView txt_agszzl = (TextView) convertView.findViewById(R.id.agszzl);
        
        TextView txt_lst_Num = (TextView) convertView.findViewById(R.id.lst_Num);
        TextView txt_lst_jjjz = (TextView) convertView.findViewById(R.id.lst_jjjz);
        TextView txt_lst_zzl = (TextView) convertView.findViewById(R.id.lst_zzl);
        TextView txt_lst_data = (TextView) convertView.findViewById(R.id.lst_data);
        
        Button btn_Main_buy = (Button) convertView.findViewById(R.id.btn_Main_buy);
        btn_Main_buy.setTag(position);
        btn_Main_buy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	final MyDialog dialog = (MyDialog) new MyDialog(mContext);
		        View view = dialog.createView(mContext, R.layout.edit_dialog_layout);
		        buy_money_edit = (EditText)view.findViewById(R.id.buy_money_edit);
		        buy_jz_edit = (EditText)view.findViewById(R.id.buy_jz_edit);
		        buy_money_edit.setText(sys.data_c.jijin_buy[position].money);
		        buy_jz_edit.setText(sys.data_c.jijin_buy[position].jz);
		        dialog.show();
		        TextView dialog_qur = (TextView) view.findViewById(R.id.dialog_qur);
		        dialog_qur.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	String money = buy_money_edit.getText().toString();
				        String jz = buy_jz_edit.getText().toString();
		            	if(checkDigit(money) && checkDigit(jz) && money!=null && jz!=null){
		            		Calendar calendar = Calendar.getInstance();
		            		int month = calendar.get(Calendar.MONTH)+1;
		            		int day = calendar.get(Calendar.DAY_OF_MONTH);
		                	
		                	sys.data_c.jijin_buy[position].money= money;
		                	sys.data_c.jijin_buy[position].date= String.valueOf(month)+"-"+String.valueOf(day);
		                	sys.data_c.jijin_buy[position].jz= jz;
		                	Refresh_run.saveJson_bought_data();
		            	}else{
		            		Toast.makeText(mContext, "输入格式不太对", Toast.LENGTH_SHORT).show();
		            	}
						dialog.dismiss();
		            }
		        });
            	
            }
        });
        
        
        Button btn_Main_del = (Button) convertView.findViewById(R.id.btn_Main_del);
        btn_Main_del.setTag(position);
        btn_Main_del.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	final MyDialog dialog = (MyDialog) new MyDialog(mContext);
		        View view = dialog.createView(mContext, R.layout.query_dialog_layout);
		        dialog.show();
		        TextView dialog_qur = (TextView) view.findViewById(R.id.dialog_qur);
		        dialog_qur.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	for(int i=0;i<Integer.parseInt(sys.Jijin_num);i++){
		            		if(i==position){
		            			for(int j=position;j<(Integer.parseInt(sys.Jijin_num)-1);j++){
				            		sys.Jijin_fundcode[j] = sys.Jijin_fundcode[j+1];

				            		sys.data_c.jijin_buy[j].money= sys.data_c.jijin_buy[j+1].money;
				                	sys.data_c.jijin_buy[j].date= sys.data_c.jijin_buy[j+1].date;
				                	sys.data_c.jijin_buy[j].jz= sys.data_c.jijin_buy[j+1].jz;
				                	sys.data_c.jijin_buy[j].zzl= sys.data_c.jijin_buy[j+1].zzl;
		            			}
		            			sys.Jijin_num = String.valueOf(Integer.parseInt(sys.Jijin_num)-1);
		            		}
		            	}
            			Refresh_run.saveJson_name_list();
            			Refresh_run.saveJson_bought_data();
            			Refresh_run.loadJson_bought_data();  //获取每个 基金中已买的信息
            			
            			remove(position);  //删除list中数据
		            	Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
		            	
						dialog.dismiss();
		            }
		        });
            	
            }
        });
        
        //img_icon.setBackgroundResource(mData.get(position).getaIcon());
        txt_aName.setText(mData.get(position).getafund_Name());
        txt_aNum.setText(mData.get(position).getafund_Num());
        txt_adwjz.setText(mData.get(position).getafund_netvalue());
        txt_aagsz.setText(mData.get(position).getafund_netvalue_estimate());
        txt_agszzl.setText(mData.get(position).getafund_netvalue_estimate_ratio());
        txt_lst_Num.setText(mData.get(position).getlast_buy_price());
        txt_lst_jjjz.setText(mData.get(position).getlast_buy_netvalue());
        txt_lst_zzl.setText(mData.get(position).getlast_advices_netvalue());  
        txt_lst_data.setText(mData.get(position).getlast_buy_date());  
        return convertView;
    }

	public static boolean checkDigit(String digit) {
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        return Pattern.matches(regex, digit);
    }
	
	private float changePercentToPoint(String percent) {
        return new Float(percent.substring(0, percent.indexOf("%")));
    }  
	
	public void remove(int position) {
	    if(mData != null) {
	        mData.remove(position);
	    }
	    notifyDataSetChanged();
	}

}