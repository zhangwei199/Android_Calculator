package com.example.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Intent intent = getIntent();
        ArrayList<String> collection = (ArrayList<String>)intent.getStringArrayListExtra("collection");

        final ListView lv_co = (ListView) findViewById(R.id.listView_co);
        final List<HashMap<String, Object>> listdata = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> data ;

        int[] imgs = {R.drawable.qian,R.drawable.bu,R.drawable.jian,R.drawable.gu,
                R.drawable.ren,R.drawable.hou,R.drawable.bu,R.drawable.jian,
                R.drawable.lai,R.drawable.zhe};
        for (int i = 0; i < collection.size() ;i++){
            data = new HashMap<String, Object>();
            if(i<=9){
                data.put("img",imgs[i]);
            }else {
                data.put("img",imgs[i-10]);
            }
            data.put("collection",collection.get(i));
            listdata.add(data);
        }
        String[] from  = {"img","collection"};
        int[] to ={R.id.imageview,R.id.textview};
        final SimpleAdapter adapter = new SimpleAdapter(CollectionActivity.this,listdata,R.layout.item,from,to);
        lv_co.setAdapter(adapter);

        Toast.makeText(CollectionActivity.this, "长按可删除收藏", Toast.LENGTH_LONG).show();
        lv_co.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listdata.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(CollectionActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CollectionActivity.this,MainActivity.class);
                CollectionActivity.this.startActivity(intent1);
            }
        });
    }
}
