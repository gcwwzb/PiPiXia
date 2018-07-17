package com.example.dcp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class aboutus extends Activity {
    private List<Name> nameList = new ArrayList<Name>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        initNames();
        IntroAdapter adapter = new IntroAdapter(aboutus.this, R.layout.listitem, nameList);
        ListView listView = (ListView) findViewById(R.id.aboutus);
        listView.setAdapter(adapter);
    }
    private void initNames() {
        Name name1 = new Name("组长——张斌", R.drawable.member1);
        nameList.add(name1);
        Name name2 = new Name("组员——黄子祎", R.drawable.member2);
        nameList.add(name2);
        Name name3 = new Name("组员——李奕辰", R.drawable.member3);
        nameList.add(name3);
    }

    public class Name {
        private String name;
        private int imageId;

        public Name(String name, int imageId) {
            this.name = name;
            this.imageId = imageId;
        }

        public String getName() {
            return name;
        }

        public int getImageId() {
            return imageId;
        }
    }

    public class IntroAdapter extends ArrayAdapter<Name> {
        private int resourceId;
        public  IntroAdapter(Context context, int textViewResourceId, List<aboutus.Name> objects){
            super(context,textViewResourceId,objects);
            resourceId = textViewResourceId;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            aboutus.Name name= getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            ImageView bookImage = (ImageView) view.findViewById(R.id.image);
            TextView bookName = (TextView) view.findViewById(R.id.name);
            bookImage.setImageResource(name.getImageId());
            bookName.setText(name.getName());
            return view;
        }
    }
}
