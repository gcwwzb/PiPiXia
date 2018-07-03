package com.example.dcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yangfan.Util.DatabaseHelper;
import com.yangfan.texiao.SwipeDismissListView;
import com.yangfan.texiao.SwipeDismissListView.OnDismissCallback;

public class PlaneActivity extends Activity {
	private Button planes;
	private Button stations;
	private SimpleAdapter adapter;//适配器
	private SimpleAdapter adapters;
	private String TrainCode;
	private String AirlineCode;
	private SwipeDismissListView listsqlitestation;//自定义的listview 复制来的.
	//特效是滑动删除,当然也可以不删除.动作自己定义
	private SwipeDismissListView listsqliteplane;
	private Button back;
	private TextView tv;
	public static List<Map<String, Object>>listqlite=new ArrayList<Map<String,Object>>();
	public static List<Map<String, Object>>listqliteplane=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_plane);
		stations = (Button) findViewById(R.id.station);//火车
		planes = (Button) findViewById(R.id.plane);	//飞机
		back = (Button) findViewById(R.id.back);
		tv = (TextView) findViewById(R.id.sctitle);
		//返回主界面
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TextViews("");
				startActivity(new Intent(PlaneActivity.this,MainActivity.class));
				finish();
			}
		});
		listsqlitestation=(SwipeDismissListView)findViewById(R.id.listsqlite1);
		listsqliteplane=(SwipeDismissListView)findViewById(R.id.listsqlite2);
		//点击查询火车在数据库里的数据,实际上就是主界面收藏按钮传过来的数据
		//其实查数据可以再找个界面里面写的.都一样
		stations.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				adapter = new SimpleAdapter(PlaneActivity.this,
						listqlite, R.layout.items, new String[] { "TrainCode",
						"FirstStation", "LastStation", "StartStation",
						"StartTime","ArriveStation", "ArriveTime", "KM",
						"UseDate"}, new int[] { R.id.TrainCode,
						R.id. LastStation, R.id.FirstStation,R.id.textViews5,
						R.id.StartTime, R.id.textViews6,R.id.ArriveTime,
						R.id.KM, R.id.UseDate });
				//一直用的都是两个listview来分开显示.不然好像是会出错的
				listsqlitestation.setAdapter(null);
				listsqlitestation.setVisibility(View.VISIBLE);
				listsqliteplane.setVisibility(View.GONE);
				listsqlitestation.setAdapter(adapter);
				TextViews("收藏了"+listqlite.size()+"个趟次火车");
			}
		});
		//飞机的.和火车一样的写法
		planes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				adapters = new SimpleAdapter(PlaneActivity.this,
						listqliteplane, R.layout.itemplane, new String[] {
						"Company",
						"AirlineCode", "StartDrome",
						"ArriveDrome", "StartTime", "ArriveTime",
						"Mode","Week"}, new int[] { R.id.textView1,
						R.id.textView3, R.id.textView6,
						R.id.textView7, R.id.textView10,
						R.id.textView11, R.id.textView15,R.id.textView13 });
				listsqliteplane.setAdapter(null);
				listsqlitestation.setVisibility(View.GONE);
				listsqliteplane.setVisibility(View.VISIBLE);
				listsqliteplane.setAdapter(adapters);
				TextViews("收藏了"+listqliteplane.size()+"个班次飞机");
			}
		});
		//自定义的onclick
		listsqlitestation.setOnDismissCallback(new OnDismissCallback() {

			@Override
			public void onDismiss(int dismissPosition) {
				Map<String, Object> str = listqlite.get(dismissPosition);
				TrainCode=str.get("TrainCode").toString();
				listqlite.remove(str);
				DatabaseHelper dbHelper = new DatabaseHelper(PlaneActivity.this);
				// 得到一个可写的SQLiteDatabase对象
				SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
				String whereClause = "TrainCode=?";//删除的条件
				String[] whereArgs = {TrainCode};//删除的条件参数
				sqliteDatabase.delete("station",whereClause,whereArgs);//执行删除
				adapter = new SimpleAdapter(PlaneActivity.this,
						listqlite, R.layout.items, new String[] { "TrainCode",
						"FirstStation", "LastStation", "StartStation",
						"StartTime","ArriveStation", "ArriveTime", "KM",
						"UseDate"}, new int[] { R.id.TrainCode,
						R.id. LastStation, R.id.FirstStation,R.id.textViews5,
						R.id.StartTime, R.id.textViews6,R.id.ArriveTime,
						R.id.KM, R.id.UseDate });
				listsqlitestation.setAdapter(adapter);
				TextViews("收藏了"+listqlite.size()+"个趟次火车");

			}
		});
		//自定义的onclick
		listsqliteplane.setOnDismissCallback(new OnDismissCallback() {

			@Override
			public void onDismiss(int dismissPosition) {
				Map<String, Object> strs = listqliteplane.get(dismissPosition);
				AirlineCode=strs.get("AirlineCode").toString();
				listqliteplane.remove(strs);
				DatabaseHelper dbHelper = new DatabaseHelper(PlaneActivity.this);
				// 得到一个可写的SQLiteDatabase对象
				SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
				String whereClause = "AirlineCode=?";//删除的条件
				String[] whereArgs = {AirlineCode};//删除的条件参数
				sqliteDatabase.delete("plane",whereClause,whereArgs);//执行删除
				adapters = new SimpleAdapter(PlaneActivity.this,
						listqliteplane, R.layout.itemplane, new String[] { "Company",
						"AirlineCode", "StartDrome",
						"ArriveDrome", "StartTime", "ArriveTime",
						"Mode","Week"}, new int[] { R.id.textView1,
						R.id. textView3, R.id.textView6,
						R.id.textView7, R.id.textView10,
						R.id.textView11, R.id.textView15,R.id.textView13 });
				listsqliteplane.setAdapter(adapters);
				TextViews("收藏了"+listqliteplane.size()+"个班次飞机");
			}
		});
	}
	private void TextViews(String s){
		tv.setText(s);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==event.KEYCODE_BACK){
			startActivity(new Intent(PlaneActivity.this,MainActivity.class));
			PlaneActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	//切记!一点要在这里写listview的  clear方法.赋值为null是不可取的报错.
	//关于找个方法的问题.请了解activity的生命周期就知道了.每次退出当前activity系统会调用找个方法
	@Override
	protected void onDestroy() {
		listqlite.clear();
		listqliteplane.clear();
		super.onDestroy();
	}
}














