package com.example.dcp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PromptActivity extends Activity {
    private int guideResourceId = 0;//引导页图片资源
    private PromptSharedPreferences psp;

    @Override
    protected void onStart() {
        super.onStart();
        addGuideImage();//添加引导页
    }
/*
添加引导图片
 */
public void addGuideImage() {
    psp = new PromptSharedPreferences();
    View view = getWindow().getDecorView().findViewById(R.id.my_content_view);//查找根布局
    if(view==null)
        return;
    if(psp.takeSharedPreferences(this)){
        return;//引导过了,则跳过
    }
    ViewParent viewParent=view.getParent();
    if(viewParent instanceof FrameLayout){
        final FrameLayout frameLayout = (FrameLayout)viewParent;
        if(guideResourceId!=0){
            //设置了引导图片
            final ImageView guideImage  = new ImageView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            guideImage.setLayoutParams(params);
            guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
            guideImage.setImageResource(guideResourceId);
            guideImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //删除引导图片
                    frameLayout.removeView(guideImage);
                    //保存记录
                    psp.saveSharedPreferences(PromptActivity.this," 启动了");
                }
            });
            frameLayout.addView(guideImage);//添加引导图片
            }
        }
    }
    //获得引导图片
    protected void setGuideResourceId(int resourceId){
        this.guideResourceId=resourceId;
    }
}
