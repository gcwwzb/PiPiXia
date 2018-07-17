package com.example.dcp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import java.util.ArrayList;

public class Introduce extends Activity implements View.OnClickListener{
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);

        //找到viewpager
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewPager);
        //获取两个页面
        LayoutInflater inflater = getLayoutInflater();
        View page1 = inflater.inflate(R.layout.intro2, null);
        View page2 = inflater.inflate(R.layout.intro3, null);
        //加入到集合里
        ArrayList<View> pageList = new ArrayList<View>();
        pageList.add(page1);
        pageList.add(page2);
        //建一个适配器
        MyPagerAdater pagerAdater = new MyPagerAdater(pageList);
        //设置到viewpager里，到此完成了。
        viewpager.setAdapter(pagerAdater);
        bt = (Button)page2.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String PREFS_NAME = "Guide";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(PREFS_NAME,false);
                editor.commit();
                Intent intent = new Intent(Introduce.this,MainActivity.class);
                startActivity(intent); // 点击最后一页的button进入主界面
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
    class MyPagerAdater extends PagerAdapter {
        //view集合
        ArrayList<View> pageList;
        public MyPagerAdater(ArrayList<View> pageList) {
            this.pageList = pageList;
        }
        //返回页面
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position), position);
            return pageList.get(position);
        }
        //这里是返回页面的个数，如当返回0时，则无页面，我们这里返回2个
        public int getCount() {
            return pageList.size();
        }
        //这里要返回true
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
