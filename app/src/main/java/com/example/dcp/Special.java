package com.example.dcp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/**
 * 彩蛋的程序
 */
public class Special extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MoreInfo", "intent");
        setContentView(R.layout.more_info);
        special1();
    }

    //1.你喜欢的西安吗
    public void special1(){
        AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(Special.this);
        alertdialogbuilder_plane.setTitle("来自皮皮侠");
        alertdialogbuilder_plane.setMessage("说你喜欢西安！");
        alertdialogbuilder_plane.setPositiveButton("我喜欢西安！", special2);
       // alertdialogbuilder_plane.setNegativeButton("我不喜欢" );
        AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
        alertdialog_plane.show();
    }
    //2.你喜欢的西安电子科技大学吗
    private DialogInterface.OnClickListener special2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(Special.this);
            alertdialogbuilder_plane.setTitle("来自皮皮侠");
            alertdialogbuilder_plane.setMessage("说你喜欢西电，快！");
            alertdialogbuilder_plane.setPositiveButton("我喜欢西电！", special3);
            //   alertdialogbuilder_plane.setNegativeButton("确定");
            AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
            alertdialog_plane.show();
        }
    };
    //3.那你喜欢的皮皮侠吗
    private DialogInterface.OnClickListener special3 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(Special.this);
            alertdialogbuilder_plane.setTitle("来自皮皮侠");
            alertdialogbuilder_plane.setMessage("说你喜欢皮皮侠！");
            alertdialogbuilder_plane.setPositiveButton("我喜欢皮皮侠！",moreinfo_to_main );
            //   alertdialogbuilder_plane.setNegativeButton( );
            AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
            alertdialog_plane.show();
        }
    };
    //退出彩蛋,到达主界面
    private DialogInterface.OnClickListener moreinfo_to_main = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            Intent intent = new Intent(Special.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(Special.this, "皮皮侠也喜欢你！", Toast.LENGTH_SHORT).show();
        }
    };

}
