package com.yangfan.Sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 初始化数据库的....复制来的
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public MySQLiteOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public MySQLiteOpenHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            //db.execSQL("CREATE TABLE [TrainInfo]([TrainCode] CHAR PRIMARY KEY, [FirstStation] CHAR, [LastStation] CHAR, [StartTime] CHAR, [ArriveTime] CHAR, [KM] INT, [UseDate] CHAR);");
            db.execSQL("CREATE TABLE [PiPiXiaInfo]([trainno] CHAR PRIMARY KEY, [type] CHAR, [station] CHAR, [endstation] CHAR, [departuretime] CHAR, [arrivaltime] CHAR, [costtime] CHAR,[distance] CHAR);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
