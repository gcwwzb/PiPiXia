package com.example.dcp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yangfan.Util.Dialogs;

public class MoreInfo extends MainActivity {
    private Button aboutus;  //关于我们
    private Button special;  //彩蛋

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MoreInfo", "intent");
        setContentView(R.layout.more_info);
        aboutus = (Button) findViewById(R.id.aboutus);
        special = (Button) findViewById(R.id.special);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreInfo.this, aboutus.class));  //打开关于我们界面
            }
        });

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreInfo.this, Special.class));  //打开彩蛋界面
            }
        });

//        special.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Special;
//                //special1();
//
//                // startActivity(new Intent(MoreInfo.this, MainActivity.class));
//            }
//        }
//        );
    }
//     //1.你喜欢的西安吗
//    public static void special1() {
//        AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(MoreInfo.this);
//        alertdialogbuilder_plane.setTitle("来自皮皮侠");
//        alertdialogbuilder_plane.setMessage("说你喜欢西安！");
//        alertdialogbuilder_plane.setPositiveButton("我喜欢西安！", special2);
//       // alertdialogbuilder_plane.setNegativeButton("我不喜欢" );
//        AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
//        alertdialog_plane.show();
//    }
//    //2.你喜欢的西安电子科技大学吗
//    private DialogInterface.OnClickListener special2 = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface arg0, int arg1) {
//            AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(MoreInfo.this);
//            alertdialogbuilder_plane.setTitle("来自皮皮侠");
//            alertdialogbuilder_plane.setMessage("说你喜欢西电，快！");
//            alertdialogbuilder_plane.setPositiveButton("我喜欢西电！", special3);
//            //   alertdialogbuilder_plane.setNegativeButton("确定");
//            AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
//            alertdialog_plane.show();
//        }
//    };
//    //3.那你喜欢的皮皮侠吗
//    private DialogInterface.OnClickListener special3 = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface arg0, int arg1) {
//            AlertDialog.Builder alertdialogbuilder_plane = new AlertDialog.Builder(MoreInfo.this);
//            alertdialogbuilder_plane.setTitle("来自皮皮侠");
//            alertdialogbuilder_plane.setMessage("说你喜欢皮皮侠！");
//            alertdialogbuilder_plane.setPositiveButton("我喜欢皮皮侠！",moreinfo_to_main );
//            //   alertdialogbuilder_plane.setNegativeButton( );
//            AlertDialog alertdialog_plane = alertdialogbuilder_plane.create();
//            alertdialog_plane.show();
//        }
//    };
//    //退出彩蛋,到达主界面
//    private DialogInterface.OnClickListener moreinfo_to_main = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface arg0, int arg1) {
//            Intent intent = new Intent(MoreInfo.this,MainActivity.class);
//            startActivity(intent);
//            Toast.makeText(MoreInfo.this, "皮皮侠也喜欢你！", Toast.LENGTH_SHORT).show();
//        }
//    };

}

