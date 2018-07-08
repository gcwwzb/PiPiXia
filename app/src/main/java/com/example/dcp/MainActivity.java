package com.example.dcp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yangfan.Entity.Bus;
import com.yangfan.Entity.Plane;
import com.yangfan.Entity.Station;
import com.yangfan.Entity.Bus.ResultBean;
import com.yangfan.Util.DatabaseHelper;
import com.yangfan.Util.Dialogs;
import com.yangfan.Util.NetConnectionUtil;
import com.yangfan.Util.HttpUtil;
import com.yangfan.Xmlpull.Xmlpullplane;
import com.yangfan.Xmlpull.xmlpull;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends Activity {
    private MainActivity activity;
    private static final int SHOW_DATAPICK = 0;   //这4个是时间方面的.我移植来的.能用即可
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;
    private final static int NULLS = 4; //查询到火车,加载列表
    private final static int TRAIN = 5; //查询到火车,加载列表
    private final static int PLANE = 6; //查询到飞机,加载列表
    private final static int BUS = 9; //查询到大巴,加载列表
    private final static int NULL = 7; //没有查询到火车车次返回值
    private final static int NULLPLANE = 8; //没有查询到飞机航班返回值
    private final static int NULLBUS = 10;  //没有查询到大巴班次返回值

    private Dialogs dialogs;            //网络连接异常弹出的窗口
    private SimpleAdapter adapter;        //火车列表适配器
    private SimpleAdapter adapters;        //飞机列表适配器
    private SimpleAdapter adapterBus;    //大巴列表适配器
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case NULLS:
                    TitleTextView("");
                    Toast.makeText(MainActivity.this, "网络连接中断请检查网络", Toast.LENGTH_SHORT).show();
                    break;
//                case NULLPLANE:
//                    dialogs.dialog.dismiss();
//                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//                    dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialog.setTitle("提示");
//                    dialog.setMessage("没有直达航班,请查询附近城市,或选择火车飞机综合换乘方案");
//                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达航班,请查询附近城市,或选择列车飞机综合换乘方案");
//                            return;
//                        }
//                    });
//                    dialog.show();
//                    break;


                case NULLPLANE:
                    dialogs.dialog.dismiss();

                    AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(MainActivity.this);
                    alertdialogbuilder_plane.setTitle("提示");
                    alertdialogbuilder_plane.setMessage("没有直达航班，请查询附近城市");
                    alertdialogbuilder_plane.setPositiveButton("确定", click_noplane_ok);
                    AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
                    alertdialog_plane.show();
//                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//                    dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialog.setTitle("提示");
//                    dialog.setMessage("没有直达航班,请查询附近城市,或选择火车飞机综合换乘方案");
//                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达航班,请查询附近城市,或选择列车飞机综合换乘方案");
//                            return;
//                        }
//                    });
//                    dialog.show();
                    break;

//                case NULL:
//                    dialogs.dialog.dismiss();
//                    ProgressDialog dialoga = new ProgressDialog(MainActivity.this);
//                    dialoga.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialoga.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialoga.setTitle("提示");
//                    dialoga.setMessage("没有直达列车，请查询附近城市");
//                    dialoga.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达列车，请查询附近城市,或选择列车飞机综合换乘方案");
//                            return;
//                        }
//                    });
//                    dialoga.show();
//                    break;
                case NULL:
                    dialogs.dialog.dismiss();

                    AlertDialog.Builder alertdialogbuilder_train=new AlertDialog.Builder(MainActivity.this);
                    alertdialogbuilder_train.setTitle("提示");
                    alertdialogbuilder_train.setMessage("没有直达列车，请查询附近城市");
                    alertdialogbuilder_train.setPositiveButton("确定", click_notrain_ok);
                    AlertDialog alertdialog_train=alertdialogbuilder_train.create();
                    alertdialog_train.show();
//                    ProgressDialog dialoga = new ProgressDialog(MainActivity.this);
//                    dialoga.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialoga.setCanceledOnTouchOutside(false);//点击屏幕，dialog不消失；点击物理返回键dialog消失
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialoga.setTitle("提示");
//                    dialoga.setMessage("没有直达列车，请查询附近城市");
//                    dialoga.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达列车，请查询附近城市,或选择列车飞机综合换乘方案");
//                            return;
//                        }
//                    });
//                    dialoga.show();
                    break;

//                case NULLBUS:
//                    dialogs.dialog.dismiss();
//                    ProgressDialog dialogBus = new ProgressDialog(MainActivity.this);
//                    dialogBus.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialogBus.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialogBus.setTitle("提示");
//                    dialogBus.setMessage("没有直达班车，请查询附近城市,或选择其他出行方案");
//                    dialogBus.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达班车，请查询附近城市,或选择其他出行方案");
//                            return;
//                        }
//                    });
//                    dialogBus.show();
//                    break;
                case NULLBUS:
                    dialogs.dialog.dismiss();

                    AlertDialog.Builder alertdialogbuilder_bus=new AlertDialog.Builder(MainActivity.this);
                    alertdialogbuilder_bus.setTitle("提示");
                    alertdialogbuilder_bus.setMessage("没有直达班车，请查询附近城市");
                    alertdialogbuilder_bus.setPositiveButton("确定", click_nobus_ok);
                    AlertDialog alertdialog_bus=alertdialogbuilder_bus.create();
                    alertdialog_bus.show();
//                    ProgressDialog dialogBus = new ProgressDialog(MainActivity.this);
//                    dialogBus.setCancelable(true);// 设置是否可以通过点击Back键取消
//                    dialogBus.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//                    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                    dialogBus.setTitle("提示");
//                    dialogBus.setMessage("没有直达班车，请查询附近城市");
//                    dialogBus.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            listview2.setVisibility(View.GONE);
//                            listview1.setVisibility(View.GONE);
//                            listview3.setVisibility(View.GONE);
//                            TitleTextView("没有直达班车，请查询附近城市");
//                            return;
//                        }
//                    });
//                    dialogBus.show();
                    break;

                case TRAIN:    //显示火车
                    listview1.setVisibility(View.VISIBLE);
                    listview2.setVisibility(View.GONE);
                    listview3.setVisibility(View.GONE);
                    adapter = new SimpleAdapter(MainActivity.this, listviews, R.layout.items, new String[]{"TrainCode", "FirstStation", "LastStation", "StartStation", "StartTime", "ArriveStation", "ArriveTime", "KM", "UseDate"}, new int[]{R.id.TrainCode, R.id.LastStation, R.id.FirstStation, R.id.textViews5, R.id.StartTime, R.id.textViews6, R.id.ArriveTime, R.id.KM, R.id.UseDate});
                    dialogs.dialog.dismiss();
                    /*特效源码！！*/
                    listview1.setLayoutAnimation(getListAnim());
                    listview1.setAdapter(adapter);
                    TitleTextView("查询到有" + listviews.size() + "趟次列车");
                    break;
                case PLANE:    //显示飞机
                    listview1.setVisibility(View.GONE);
                    listview2.setVisibility(View.VISIBLE);
                    listview3.setVisibility(View.GONE);
                    adapters = new SimpleAdapter(MainActivity.this, listviews, R.layout.itemplane, new String[]{"Company", "AirlineCode", "StartDrome", "ArriveDrome", "StartTime", "ArriveTime", "Mode", "Week"}, new int[]{R.id.textView21, R.id.textView23, R.id.textView6, R.id.textView24, R.id.textView26, R.id.textView11, R.id.textView27, R.id.textView22});
                    dialogs.dialog.dismiss();
                    listview2.setLayoutAnimation(getListAnim());
                    listview2.setAdapter(adapters);
                    TitleTextView("查询到有" + listviews.size() + "班次飞机");
                    break;
                case BUS:  //显示班车
                    listview1.setVisibility(View.GONE);
                    listview2.setVisibility(View.GONE);
                    listview3.setVisibility(View.VISIBLE);
                    adapterBus = new SimpleAdapter(MainActivity.this, listviews, R.layout.itembus, new String[]{"bustype", "startcity", "endcity", "startstation", "endstation", "distance", "starttime", "price",}, new int[]{R.id.bustype, R.id.startcity, R.id.endcity, R.id.startstation, R.id.endstation, R.id.distance, R.id.starttime, R.id.price});
                    dialogs.dialog.dismiss();
                    /*特效源码！！*/
                    listview3.setLayoutAnimation(getListAnim());
                    listview3.setAdapter(adapterBus);
                    TitleTextView("查询到有" + listviews.size() + "趟大巴");
                    break;
            }
        }

        ;
    };
    /**
     * 火车的网址
     */
    String host = "http://ws.webxml.com.cn";
    String getPart1 = "/WebServices/TrainTimeWebService.asmx/getStationAndTimeByStationName?StartStation=";
    String getPart2 = "&ArriveStation=";
    String getPart3 = "&UserID=";
    /**
     * 飞机的网址
     */
    String getPart4 = "/webservices/DomesticAirline.asmx/getDomesticAirlinesTime?startCity=";
    String getPart5 = "&lastCity=";
    String getPart6 = "&theDate=";
    String getPart7 = "&UserID=";
    /**
     * 大巴的网址
     */
    String hostOfBus = "http://api.jisuapi.com/bus/city2c";
    String APPKEY = "575f26104b13e249";// 你的appkey

    /**
     * -----------------------------------------------
     */
    private String date;  //飞机查询需要的日期
    private String startName;//edittext1  出发城市
    private String arriveName; //edittext2 目的地城市
    private String xmlUrl;    //火车的网址
    private String xmlUrlplane;   //飞机的网址
    private String Urlbus;    //大巴的网址

    List<Map<String, Object>> listviews;  //为了适用SimpleAdapter的集合
    private List<Station> list;        //火车对应的集合
    private List<Plane> listplane;    //飞机的对应集合
    private List<Bus> listbus;    //大巴的对应集合
    private EditText et1;
    private EditText et2;
    public static Button bt1;        //火车查询
    public static Button bt2;        //飞机查询
    public static Button bt4;        //大巴查询
    public static Button bt3;        //跳转收藏页面
    private int mYear;    //依然是时间方面移植来的
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private ListView listview1;        //火车显示列表
    private ListView listview2;        //飞机显示列表
    private ListView listview3;        //大巴显示列表
    private ConnectivityManager cwjManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        setContentView(R.layout.activity_main);
        cwjManager = (ConnectivityManager)    //网络连接状态监测方面的,不懂
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //这段代码放到Activity类中才用this,SQLite方面的
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getWritableDatabase();

        /*------------下面这段是写航空时间选择方面的--------------*/
        final Calendar c = Calendar.getInstance();
        dialogs = new Dialogs(MainActivity.this);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        /*------------------------------------------------------*/

        tv = (TextView) findViewById(R.id.title);
        listview1 = (ListView) findViewById(R.id.listView1);//火车
        listview2 = (ListView) findViewById(R.id.listView2);//飞机
        listview3 = (ListView) findViewById(R.id.listView3);//大巴

        listview1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //maps 或者选中item在listviews里面的下标所对应的值,然后取出里面的值
                Map<String, Object> maps = listviews.get(position);
                Station station = new Station();
                station.TrainCode = (String) maps.get("TrainCode");
                //数据库里面查询这个值是不是存在,存在了就不保存了.不存在的保存下来.
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
                Cursor c = sqliteDatabase.rawQuery("select * from station where TrainCode=?;", new String[]{station.TrainCode.toString()});
                if (c.getCount() <= 0) {
                    station.FirstStation = (String) maps.get("FirstStation");
                    station.LastStation = (String) maps.get("LastStation");
                    station.StartStation = (String) maps.get("StartStation");
                    station.StartTime = (String) maps.get("StartTime");
                    station.ArriveStation = (String) maps.get("ArriveStation");
                    station.ArriveTime = (String) maps.get("ArriveTime");
                    station.KM = (String) maps.get("KM");
                    station.UseDate = (String) maps.get("UseDate");
                    insert(station);
                    //Toast.makeText(MainActivity.this,station.TrainCode+" 列车加入收藏列表", 1).show();
                    Toast.makeText(MainActivity.this, station.TrainCode + " 列车加入收藏列表", Toast.LENGTH_LONG).show();
                    /*-------------------------------------------------------*/
                } else {
                    //Toast.makeText(MainActivity.this, "该车次已经添加过了", 1).show();
                    Toast.makeText(MainActivity.this, "该车次已经添加过了", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        listview2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                /*--------------------和火车的一样参考上面的-----------------------*/
                Map<String, Object> maps = listviews.get(arg2);
                Plane plane = new Plane();
                plane.Company = (String) maps.get("Company");
                plane.AirlineCode = (String) maps.get("AirlineCode");
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
                Cursor cs = sqliteDatabase.rawQuery("select * from plane where AirlineCode=?;", new String[]{plane.AirlineCode.toString()});
                if (cs.getCount() <= 0) {
                    plane.StartDrome = (String) maps.get("StartDrome");
                    plane.ArriveDrome = (String) maps.get("ArriveDrome");
                    plane.StartTime = (String) maps.get("StartTime");
                    plane.ArriveTime = (String) maps.get("ArriveTime");
                    plane.Mode = (String) maps.get("Mode");
                    plane.Week = (String) maps.get("Week");
                    Log.i("aaa", plane.toString());
                    inserts(plane);
                    //Toast.makeText(MainActivity.this,plane.AirlineCode+" 航班加入收藏列表", 1).show();
                    Toast.makeText(MainActivity.this, plane.AirlineCode + " 航班加入收藏列表", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(MainActivity.this, "该班次已经添加过了", 1).show();
                    Toast.makeText(MainActivity.this, "该班次已经添加过了", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        listview3.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int busPosition, long arg3) {
                /*--------------------和火车的一样参考上面的-----------------------*/
                //maps 或者选中item在listviews里面的下标所对应的值,然后取出里面的值
                Map<String, Object> maps = listviews.get(busPosition);
                ResultBean bus = new ResultBean();
                bus.setBustype((String) maps.get("bustype"));
                //数据库里面查询这个值是不是存在,存在了就不保存了.不存在的保存下来.
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
                Cursor css = sqliteDatabase.rawQuery("select * from bus where bustype=?;", new String[]{bus.getBustype().toString()});
                if (css.getCount() <= 0) {
                    bus.setDistance((String) maps.get("distance"));
                    bus.setStartcity((String) maps.get("startcity"));
                    bus.setStartstation((String) maps.get("startstation"));
                    bus.setEndcity((String) maps.get("endcity"));
                    bus.setEndstation((String) maps.get("endstation"));
                    bus.setStarttime((String) maps.get("starttime"));
                    bus.setPrice((String) maps.get("price"));
                    insertBus(bus);
                    //Toast.makeText(MainActivity.this,station.TrainCode+" 列车加入收藏列表", 1).show();
                    Toast.makeText(MainActivity.this, bus.getStarttime() + " 班车加入收藏列表", Toast.LENGTH_LONG).show();
                    /*-------------------------------------------------------*/
                } else {
                    //Toast.makeText(MainActivity.this, "该车次已经添加过了", 1).show();
                    Toast.makeText(MainActivity.this, "该车次已经添加过了", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });


        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);

        bt1 = (Button) findViewById(R.id.bt1);//火车
        bt2 = (Button) findViewById(R.id.bt2);//飞机
        bt3 = (Button) findViewById(R.id.btshoucang);//收藏
        bt4 = (Button) findViewById(R.id.bt4);//大巴

        bt1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                listviews = null;//每次点击都要刷新下列表不然的话就会重叠显示
                listviews = new ArrayList<Map<String, Object>>();
                startName = et1.getText().toString();
                arriveName = et2.getText().toString();
                if ("".equals(startName)) {
                    Toast.makeText(MainActivity.this, "出发地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(arriveName)) {
                    Toast.makeText(MainActivity.this, "到达地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!"".equals(startName) && !"".equals(arriveName)) {
                    /*------------------前两句是把输入法取消掉.不然占屏幕空间太多---------------------*/
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(bt1.getApplicationWindowToken(), 0);
                    /*------输入框不为空的情况下,该方面的返回值判断当前网络是不是可以连接-------*/
                    if (NetWorkStatus() == true) {
                        dialogs.dialog.show();//加载中显示的一个提示框加载完成后自己要用代码取消掉
                        /*网址*/
                        xmlUrl = host + getPart1 + startName + getPart2 + arriveName + getPart3;
                        new Thread(new MyThread()).start();//启动线程.下载内容
                    } else {
                        Toast.makeText(MainActivity.this, "网络有问题", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*------------------前两句是把打字法取消掉.不然占屏幕空间太多---------------------*/
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(bt1.getApplicationWindowToken(), 0);
                listviews = null;//每次点击都要刷新下列表不然的话就会重叠显示
                listviews = new ArrayList<Map<String, Object>>();
                startName = et1.getText().toString();
                arriveName = et2.getText().toString();
                /*-----------判断输入框内容--------------*/
                if ("".equals(startName)) {
                    Toast.makeText(MainActivity.this, "出发地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(arriveName)) {
                    Toast.makeText(MainActivity.this, "到达地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!"".equals(startName) && !"".equals(arriveName)) {
                    /*------输入框不为空的情况下,该方面的返回值判断当前网络是不是可以连接-------*/
                    if (NetWorkStatus() == true) {
                        Message msg = new Message();
                        if (bt2.equals((Button) v)) {
                            /*和火车不一样的是飞机点击后显示的是时间选取的提示窗用的也是handler*/
                            msg.what = MainActivity.SHOW_DATAPICK;
                        }
                        MainActivity.this.dateandtimeHandler.sendMessage(msg);
                    } else {
                        Toast.makeText(MainActivity.this, "网络有问题", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {   // 大巴

            @Override
            public void onClick(View viewBus) {
                listviews = null;//每次点击都要刷新下列表不然的话就会重叠显示
                listviews = new ArrayList<Map<String, Object>>();
                startName = et1.getText().toString();
                arriveName = et2.getText().toString();
                if ("".equals(startName)) {
                    Toast.makeText(MainActivity.this, "出发地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(arriveName)) {
                    Toast.makeText(MainActivity.this, "到达地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!"".equals(startName) && !"".equals(arriveName)) {
                    /*------------------前两句是把输入法取消掉.不然占屏幕空间太多---------------------*/
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(bt4.getApplicationWindowToken(), 0);
                    /*------输入框不为空的情况下,该方面的返回值判断当前网络是不是可以连接-------*/
                    if (NetWorkStatus() == true) {
                        dialogs.dialog.show();//加载中显示的一个提示框加载完成后自己要用代码取消掉
                        /*网址*/
                        new Thread(new BusThread()).start();//启动线程.下载内容
                    } else {
                        Toast.makeText(MainActivity.this, "网络有问题", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        bt3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //跳转收藏页面 三个方法分别执行的是取出火车、飞机和大巴在数据库里存入的内容
                //取出内容然后把内容赋值给另一个页面的三个list<map<String,object>>
                tv.setText("");
                querys();
                querysplane();
                querysbus();
                startActivity(new Intent(MainActivity.this, PlaneActivity.class));
                MainActivity.this.finish();
            }
        });
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                //网络连接的方法
                InputStream inputStream = NetConnectionUtil.GetInputStream(xmlUrl);
                //解析XML文件的方法
                xmlpull xmlpull = new xmlpull();
                list = xmlpull.getpull(inputStream);
                //循环读取解析好的列表集合list<map<String,object>>类型的
                for (Station lists : list) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    String ArriveStation = lists.ArriveStation.toString();
                    String ArriveTime = lists.ArriveTime.toString();
                    String FirstStation = lists.FirstStation.toString();
                    String LastStation = lists.LastStation.toString();
                    String StartStation = lists.StartStation.toString();
                    String StartTime = lists.StartTime.toString();
                    String TrainCode = lists.TrainCode.toString();
                    String UseDate = lists.UseDate.toString();
                    String KM = lists.KM.toString();
                    map.put("ArriveStation", ArriveStation);
                    map.put("ArriveTime", ArriveTime);
                    map.put("FirstStation", FirstStation);
                    map.put("LastStation", LastStation);
                    map.put("StartStation", StartStation);
                    map.put("StartTime", StartTime);
                    map.put("TrainCode", TrainCode);
                    map.put("UseDate", UseDate);
                    map.put("KM", KM);
                    listviews.add(map);
                    if ("----".equals(TrainCode)) {
                        //判断车次是不是存在.在这次我用的是车次的判断,"----"的就是没有车次
                        //返回的what和有车次的肯定不一样.
                        Message msg = new Message();
                        msg.what = NULL;
                        handler.sendMessage(msg);
                        break;
                    }else{
                        Message msg = new Message();
                        msg.what = TRAIN;
                        handler.sendMessage(msg);
                    }
                }

            } catch (Exception e) {
                Dialogs.dialog.dismiss();
                Log.i("网络错误", "执行了网络错误1");
                Message msg = new Message();
                msg.what = NULLS;
                handler.sendMessage(msg);
                //return;
            }
        }
    }

    /*----------------------和火车的一样不再解释了----------------------------*/
    //唯一一点不一样的是飞机选的是班次的判断.班次内容是没有航班的时候返回what改变
    class PlaneThread implements Runnable {

        @Override
        public void run() {
            try {
                InputStream inputStream = NetConnectionUtil.GetInputStream(xmlUrlplane);
                Xmlpullplane xmlpull = new Xmlpullplane();
                listplane = xmlpull.getpullplane(inputStream);
                for (Plane planes : listplane) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    String Company = planes.Company.toString();
                    String AirlineCode = planes.AirlineCode.toString();
                    String StartDrome = planes.StartDrome.toString();
                    String ArriveDrome = planes.ArriveDrome.toString();
                    String StartTime = planes.StartTime.toString();
                    String ArriveTime = planes.ArriveTime.toString();
                    String Mode = planes.Mode.toString();
                    String Week = planes.Week.toString();
                    map.put("Company", Company);
                    map.put("AirlineCode", AirlineCode);
                    map.put("StartDrome", StartDrome);
                    map.put("ArriveDrome", ArriveDrome);
                    map.put("StartTime", StartTime);
                    map.put("ArriveTime", ArriveTime);
                    map.put("Mode", Mode);
                    map.put("Week", Week);
                    listviews.add(map);

                    if ("没有航班".equals(Company.toString())) {
                        Message mes = new Message();
                        mes.what = NULLPLANE;
                        handler.sendMessage(mes);
                        Log.i("aaa", "查询到没有此路线的直达航班");
                    } else {
                        Message msg = new Message();
                        msg.what = PLANE;
                        handler.sendMessage(msg);
                    }
                }

            } catch (Exception e) {
                Dialogs.dialog.dismiss();
                Log.i("网络错误", "执行了网络错误1");
                Message msg = new Message();
                msg.what = NULLS;
                handler.sendMessage(msg);
                //return;
            }
        }
    }

    /*-----------大巴的线程-----------和火车的一样不再解释了----其实最麻烦----解析json数据--------------------*/
    //唯一一点不一样的是大巴选的是发车时间的判断，发车时间是没有班次的时候返回0
    class BusThread implements Runnable {

        @Override
        public void run() {

            JSONObject jsonObject;
            try {
                Urlbus = hostOfBus + "?appkey=" + APPKEY + "&start=" + startName + "&end=" + arriveName;

                /**---------------------下面几个输出是测试----Urlbus------results------status---------------------*/
                //System.out.println("这是Urlbus的内容-----" + Urlbus);
                String results = HttpUtil.sendGet(Urlbus, "utf-8");
                //System.out.println("这是最后要解析的数据-----" + results);

                /**---------------------------- 解析 ------------------------*/
                jsonObject = new JSONObject(results);
                System.out.println("这是jsonObject-----" + jsonObject.toString());
                /**---------------------------------------------------------------------------*/
                System.out.println("这是第一个元素status-----" + jsonObject.getString("status"));
                if (jsonObject.getInt("status") != 0) {
                    System.out.println(jsonObject.getString("msg"));
                    //下面这三句是在查找不到的时候弹出提示框查询失败。。。
                    Message msg = new Message();
                    msg.what = NULLBUS;
                    handler.sendMessage(msg);
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String startcity = obj.getString("startcity");
                        String endcity = obj.getString("endcity");
                        String startstation = obj.getString("startstation");
                        String endstation = obj.getString("endstation");
                        String starttime = obj.getString("starttime");
                        String price = obj.getString("price");
                        String bustype = obj.getString("bustype");
                        String distance = obj.getString("distance");
                        System.out.println(startcity + " " + endcity + " " + startstation + " " + endstation + " " + starttime + " " + price + " " + bustype + " " + distance);

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("startcity", startcity);
                        map.put("endcity", endcity);
                        map.put("startstation", startstation);
                        map.put("endstation", endstation);
                        map.put("starttime", starttime);
                        map.put("price", price);
                        map.put("bustype", bustype);
                        map.put("distance", distance);
                        listviews.add(map);
                        Message msg = new Message();
                        msg.what = BUS;
                        handler.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                Dialogs.dialog.dismiss();
                Log.i("网络错误", "执行了网络错误1");
                Message msg = new Message();
                msg.what = NULLS;
                handler.sendMessage(msg);
                //return;
            }
        }
    }

    /**
     * -----------特效源码---------listview加载的效果----------------
     */
    public static LayoutAnimationController getListAnim() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        return controller;
        /*-----------------------------------------*/
    }

    /**
     * --------------------以下为时间选择方面的我不懂,移植来的--------------------
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
    }

    private void updateDateDisplay() {
        date = new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append((mDay < 10) ? "0" + mDay : mDay) + "";

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            /*------这是提示框的确认按钮,点击后选择时间,然后得到网址,执行线程--------*/
            updateDateDisplay();
            xmlUrlplane = host + getPart4 + startName + getPart5 + arriveName + getPart6 + date + getPart7;
            dialogs.dialog.show();
            new Thread(new PlaneThread()).start();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
            case TIME_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
                break;
        }
    }

    Handler dateandtimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MainActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
                case MainActivity.SHOW_TIMEPICK:
                    showDialog(TIME_DIALOG_ID);
                    break;
            }
        }
    };
    /**------------------------------以上为时间选取-------------------------------------*/


    /**
     * ------------------------单击item的时候保存这个item所携带的内容到数据库--------------------------
     */
    public void insert(Station station) {
        ContentValues values = new ContentValues();
        // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
        values.put("TrainCode", station.getTrainCode().toString());
        values.put("FirstStation", station.getFirstStation().toString());
        values.put("LastStation", station.getLastStation().toString());
        values.put("StartStation", station.getStartStation().toString());
        values.put("StartTime", station.getStartTime().toString());
        values.put("ArriveStation", station.getArriveStation().toString());
        values.put("ArriveTime", station.getArriveTime().toString());
        values.put("KM", station.getKM().toString());
        values.put("UseDate", station.getUseDate().toString());
        // 创建DatabaseHelper对象
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.insert("station", null, values);
        // 调用insert方法，就可以将数据插入到数据库当中
        // 第一个参数:表名称
        // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
        // 第三个参数：ContentValues对象
    }

    /**
     * -----------------上面的是火车的,这个是飞机的-------------------
     */
    public void inserts(Plane plane) {
        ContentValues values = new ContentValues();
        // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
        values.put("Company", plane.getCompany().toString());
        values.put("AirlineCode", plane.getAirlineCode().toString());
        values.put("StartDrome", plane.getStartDrome().toString());
        values.put("ArriveDrome", plane.getArriveDrome().toString());
        values.put("StartTime", plane.getStartTime().toString());
        values.put("ArriveTime", plane.getArriveTime().toString());
        values.put("Mode", plane.getMode().toString());
        values.put("Week", plane.getWeek().toString());
        // 创建DatabaseHelper对象
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.insert("plane", null, values);
    }

    /**
     * -----------------上面的是飞机的,这个是大巴的-------------------
     */
    public void insertBus(ResultBean result) {
        ContentValues values = new ContentValues();
        // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
        values.put("bustype", result.getBustype().toString());
        values.put("distance", result.getDistance().toString());
        values.put("startcity", result.getStartcity().toString());
        values.put("startstation", result.getStartstation().toString());
        values.put("endcity", result.getEndcity().toString());
        values.put("endstation", result.getEndstation().toString());
        values.put("starttime", result.getStarttime().toString());
        values.put("price", result.getPrice().toString());
        // 创建DatabaseHelper对象
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.insert("bus", null, values);
    }

    /**
     * ------------------------读取保存的火车item在数据库里的所有内容---------------------------
     */
    public void querys() {
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        Cursor c = sqliteDatabase.query("station", null, null, null, null, null, null);
        if (c.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < c.getCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                String TrainCode = c.getString(c.getColumnIndex("TrainCode"));
                String FirstStation = c.getString(c.getColumnIndex("FirstStation"));
                String LastStation = c.getString(c.getColumnIndex("LastStation"));
                String StartStation = c.getString(c.getColumnIndex("StartStation"));
                String StartTime = c.getString(c.getColumnIndex("StartTime"));
                String ArriveStation = c.getString(c.getColumnIndex("ArriveStation"));
                String ArriveTime = c.getString(c.getColumnIndex("ArriveTime"));
                String KM = c.getString(c.getColumnIndex("KM"));
                String UseDate = c.getString(c.getColumnIndex("UseDate"));
                map.put("TrainCode", TrainCode);
                map.put("FirstStation", FirstStation);
                map.put("LastStation", LastStation);
                map.put("StartStation", StartStation);
                map.put("StartTime", StartTime);
                map.put("ArriveStation", ArriveStation);
                map.put("ArriveTime", ArriveTime);
                map.put("KM", KM);
                map.put("UseDate", UseDate);
                PlaneActivity.listqlite.add(map);
                Log.i("listsqlitestation", map.toString());
                c.moveToNext();
            }
        }
        c.close();
    }

    /*---------------------读取飞机在数据库保存的所有item----------------------------*/
    public void querysplane() {
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        Cursor c = sqliteDatabase.query("plane", null, null, null, null, null, null);
        if (c.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < c.getCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                String Company = c.getString(c.getColumnIndex("Company"));
                String AirlineCode = c.getString(c.getColumnIndex("AirlineCode"));
                String StartDrome = c.getString(c.getColumnIndex("StartDrome"));
                String ArriveDrome = c.getString(c.getColumnIndex("ArriveDrome"));
                String StartTime = c.getString(c.getColumnIndex("StartTime"));
                String ArriveTime = c.getString(c.getColumnIndex("ArriveTime"));
                String Mode = c.getString(c.getColumnIndex("Mode"));
                String Week = c.getString(c.getColumnIndex("Week"));
                map.put("Company", Company);
                map.put("AirlineCode", AirlineCode);
                map.put("StartDrome", StartDrome);
                map.put("ArriveDrome", ArriveDrome);
                map.put("StartTime", StartTime);
                map.put("ArriveTime", ArriveTime);
                map.put("Mode", Mode);
                map.put("Week", Week);
                PlaneActivity.listqliteplane.add(map);
                Log.i("listsqliteplane", map.toString());
                c.moveToNext();
            }
        }
        c.close();
    }

    /*---------------------读取大巴在数据库保存的所有item----------------------------*/
    public void querysbus() {
        // 得到一个可写的SQLiteDatabase对象
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        //Cursor c = sqliteDatabase.query("bus", null, null, null, null, null, null);
        Cursor c = sqliteDatabase.query("bus", null, null, null, null, null, null);
        if (c.moveToFirst()) {//判断游标是否为空
            for (int i = 0; i < c.getCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                String bustype = c.getString(c.getColumnIndex("bustype"));
                String distance = c.getString(c.getColumnIndex("distance"));
                String startcity = c.getString(c.getColumnIndex("startcity"));
                String startstation = c.getString(c.getColumnIndex("startstation"));
                String endcity = c.getString(c.getColumnIndex("endcity"));
                String endstation = c.getString(c.getColumnIndex("endstation"));
                String starttime = c.getString(c.getColumnIndex("starttime"));
                String price = c.getString(c.getColumnIndex("price"));
                map.put("bustype", bustype);
                map.put("distance", distance);
                map.put("startcity", startcity);
                map.put("startstation", startstation);
                map.put("endcity", endcity);
                map.put("endstation", endstation);
                map.put("starttime", starttime);
                map.put("price", price);
                PlaneActivity.listqlitebus.add(map);
                Log.i("listsqlitebus", map.toString());
                c.moveToNext();
            }
        }
        c.close();
    }

    /*-----------------------判断当前手机联网状态----------------------------*/
    private boolean NetWorkStatus() {
        boolean netSataus = false;
        cwjManager.getActiveNetworkInfo();
        if (cwjManager.getActiveNetworkInfo() != null) {
            netSataus = true;
            netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
        } else {
            AlertDialog.Builder builder = new Builder(MainActivity.this);
            builder.setTitle("设置网络");
            builder.setMessage("网络错误，请设置网络");
            builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        //3.0以上打开设置界面
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        MainActivity.this.finish();
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        MainActivity.this.finish();
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    return;
                }
            });
            builder.create().show();
        }
        return netSataus;

    }

    private void TitleTextView(String s) {
        tv.setText(s);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确认要退出应用?");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.create().show();
        }
        return super.onKeyDown(keyCode, event);
    }


    private DialogInterface.OnClickListener click_notrain_ok =new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            listview2.setVisibility(View.GONE);
            listview1.setVisibility(View.GONE);
            listview3.setVisibility(View.GONE);
            TitleTextView("没有直达列车，请查询附近城市,或选择列车飞机综合换乘方案");
            return;
        }
    };
    private DialogInterface.OnClickListener click_noplane_ok =new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            listview2.setVisibility(View.GONE);
            listview1.setVisibility(View.GONE);
            listview3.setVisibility(View.GONE);
            TitleTextView("没有直达航班，请查询附近城市,或选择列车飞机综合换乘方案");
            return;
        }
    };
    private DialogInterface.OnClickListener click_nobus_ok =new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            listview2.setVisibility(View.GONE);
            listview1.setVisibility(View.GONE);
            listview3.setVisibility(View.GONE);
            TitleTextView("没有直达班车，请查询附近城市,或选择列车飞机综合换乘方案");
            return;
        }
    };

}