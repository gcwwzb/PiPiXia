package com.yangfan.Util;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialogs {
	private String s="数据疯狂加载中！骚等骚等";
	public static ProgressDialog dialog;
	public Dialogs(Context context){
		dialog = new ProgressDialog(context);
		dialog.setTitle("提示");
		dialog.setMessage(s);
	}

}
