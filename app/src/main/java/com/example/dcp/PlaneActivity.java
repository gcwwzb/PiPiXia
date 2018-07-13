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
    private Button buses;
	private SimpleAdapter adapter;//适配器
	private SimpleAdapter adapters;
    private SimpleAdapter adapterbus;
	private String trainno;
	private String AirlineCode;
    private String starttime;
	private SwipeDismissListView listsqlitestation;//自定义的listview 复制来的
	//特效是滑动删除,当然也可以不删除。动作自己定义
	private SwipeDismissListView listsqliteplane;
    private SwipeDismissListView listsqlitebus;
	private Button back;
	private TextView tv;
	public static List<Map<String, Object>>listqlite=new ArrayList<Map<String,Object>>();
	public static List<Map<String, Object>>listqliteplane=new ArrayList<Map<String,Object>>();
    public static List<Map<String, Object>>listqlitebus=new ArrayList<Map<String,Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_plane);
		stations = (Button) findViewById(R.id.station);//火车
		planes = (Button) findViewById(R.id.plane);	//飞机
        buses = (Button) findViewById(R.id.bus);  //大巴
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
        listsqlitebus=(SwipeDismissListView)findViewById(R.id.listsqlite3);

        //点击查询火车在数据库里的数据,实际上就是主界面收藏按钮传过来的数据
		//其实查数据可以再找个界面里面写的.都一样
        //"trainno", "type", "station", "endstation", "departuretime", "arrivaltime", "costtime", "distance"
		stations.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				adapter = new SimpleAdapter(PlaneActivity.this,
						listqlite, R.layout.items, new String[] {
				        "trainno", "type", "station", "endstation",
						"departuretime","arrivaltime", "costtime", "distance"},
                        new int[] {
						R.id.trainno, R.id.type,R.id.station,
						R.id.endstation, R.id.departuretime,R.id.arrivaltime,
						R.id.costtime, R.id.distance });
				//一直用的都是两个listview来分开显示.不然好像是会出错的
				listsqlitestation.setAdapter(null);
				listsqlitestation.setVisibility(View.VISIBLE);
				listsqliteplane.setVisibility(View.GONE);
				listsqlitebus.setVisibility(View.GONE);
				listsqlitestation.setAdapter(adapter);
				TextViews("收藏了"+listqlite.size()+"个趟次火车");
			}
		});
		//飞机的和火车一样的写法
		planes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				adapters = new SimpleAdapter(PlaneActivity.this,
						listqliteplane, R.layout.itemplane, new String[] {
						"Company",
						"AirlineCode", "StartDrome",
						"ArriveDrome", "StartTime", "ArriveTime",
						"Mode","Week"}, new int[] { R.id.textView21,
						R.id.textView23, R.id.textView6,
						R.id.textView24, R.id.textView26,
						R.id.textView11, R.id.textView27,R.id.textView22});
				listsqliteplane.setAdapter(null);
				listsqlitestation.setVisibility(View.GONE);
				listsqliteplane.setVisibility(View.VISIBLE);
                listsqlitebus.setVisibility(View.GONE);
				listsqliteplane.setAdapter(adapters);
				TextViews("收藏了"+listqliteplane.size()+"个班次飞机");
			}
		});
		//大巴的和前面两个一样
        buses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                adapterbus = new SimpleAdapter(PlaneActivity.this,
                        listqlitebus, R.layout.itembus, new String[] {
                        "bustype", "distance", "starcity",
                        "starstation", "endcity", "endstation",
                        "starttime","price"}, new int[] {
                        R.id.bustype, R.id.distance,
                        R.id.startcity, R.id.startstation,
                        R.id.endcity, R.id.endstation,
                        R.id.starttime,R.id.price});
                listsqlitebus.setAdapter(null);
                listsqlitestation.setVisibility(View.GONE);
                listsqliteplane.setVisibility(View.GONE);
                listsqlitebus.setVisibility(View.VISIBLE);
                listsqlitebus.setAdapter(adapterbus);
                TextViews("收藏了"+listqlitebus.size()+"个班次大巴");
            }
        });
		//自定义的onclick
		listsqlitestation.setOnDismissCallback(new OnDismissCallback() {

			@Override
			public void onDismiss(int dismissPosition) {
				Map<String, Object> str = listqlite.get(dismissPosition);
                trainno=str.get("trainno").toString();
				listqlite.remove(str);
				DatabaseHelper dbHelper = new DatabaseHelper(PlaneActivity.this);
				// 得到一个可写的SQLiteDatabase对象
				SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
				String whereClause = "trainno=?";//删除的条件
				String[] whereArgs = {trainno};//删除的条件参数
				sqliteDatabase.delete("station",whereClause,whereArgs);//执行删除
				adapter = new SimpleAdapter(PlaneActivity.this,
						listqlite, R.layout.items, new String[] {
                        "trainno", "type", "station", "endstation",
                        "departuretime","arrivaltime", "costtime", "distance"},
                        new int[] {
				        R.id.trainno, R.id.type,R.id.station,
                        R.id.endstation, R.id.departuretime,R.id.arrivaltime,
                        R.id.costtime, R.id.distance });
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
						"Mode","Week"}, new int[] { R.id.textView21,
						R.id.textView23, R.id.textView6,
						R.id.textView24, R.id.textView26,
						R.id.textView11, R.id.textView27,R.id.textView22});
				listsqliteplane.setAdapter(adapters);
				TextViews("收藏了"+listqliteplane.size()+"个班次飞机");
			}
		});
        //自定义的onclick
        listsqlitebus.setOnDismissCallback(new OnDismissCallback() {

            @Override
            public void onDismiss(int dismissPosition) {
                Map<String, Object> strbus = listqlitebus.get(dismissPosition);
                starttime = strbus.get("starttime").toString();
                listqlitebus.remove(strbus);
                DatabaseHelper dbHelper = new DatabaseHelper(PlaneActivity.this);
                // 得到一个可写的SQLiteDatabase对象
                SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
                String whereClause = "starttime=?";//删除的条件
                String[] whereArgs = {starttime};//删除的条件参数
                sqliteDatabase.delete("bus",whereClause,whereArgs);//执行删除
                adapterbus = new SimpleAdapter(PlaneActivity.this,
                        listqlitebus, R.layout.itembus, new String[] {
                        "bustype", "distance", "startcity",
                        "starstation", "endcity", "endstation",
                        "starttime","price"}, new int[] {
                        R.id.bustype,
                        R.id.distance, R.id.startcity,
                        R.id.startstation, R.id.endcity,
                        R.id.endstation, R.id.starttime,R.id.price});
                listsqlitebus.setAdapter(adapterbus);
                TextViews("收藏了"+listqlitebus.size()+"个班次大巴");

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
	//切记!一点要在这里写listview的clear方法.赋值为null是不可取的报错.
	//关于这个个方法的问题.请了解activity的生命周期就知道了.每次退出当前activity系统会调用这个方法
	@Override
	protected void onDestroy() {
		listqlite.clear();
		listqliteplane.clear();
		listqlitebus.clear();
		super.onDestroy();
	}
}