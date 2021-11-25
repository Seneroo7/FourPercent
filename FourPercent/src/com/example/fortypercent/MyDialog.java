package com.example.fortypercent;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class MyDialog extends Dialog {
    /**
     * ���췽��������ʽ
     * ����Ҫ�ĵط�
     * @param context
     */
    public MyDialog(final Context context) {
//      ������ʽ
        super(context, R.style.CustomDialog);
    }

    /**
     *
     *���벼���ļ�������ʾ
     */
    public View createView(Context context,int dialog_layout){
//      ���벼���ļ�
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      ����ת��View����
        View view = inflate.inflate(dialog_layout, null);
//      ��ʾ����
        setContentView(view);
        return view;

    }
}