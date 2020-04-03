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

public class HistoryActivity extends AppCompatActivity {
    ListView listView;
    List<HashMap<String, Object>> listdata;
    HashMap<String, Object> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] histories = bundle.getStringArray("history");

        listView = (ListView) findViewById(R.id.listView);
        listdata = new ArrayList<HashMap<String, Object>>();
        int[] imgs = {R.drawable.tian,R.drawable.sheng,R.drawable.wo,R.drawable.cai,
                R.drawable.bi,R.drawable.you,R.drawable.yong,R.drawable.di,
                R.drawable.zao,R.drawable.wu3,R.drawable.shen,R.drawable.shi,
                R.drawable.wu2,R.drawable.shuang};
        for (int i = 0; i < histories.length ;i++){
            data = new HashMap<String, Object>();
            if(i<=13){
                data.put("img",imgs[i]);
            }else {
                data.put("img",imgs[i-14]);
            }
            data.put("history",histories[i]);
            listdata.add(data);
        }
        String[] from  = {"img","history"};
        int[] to = {R.id.imageview, R.id.textview};
        SimpleAdapter adapter = new SimpleAdapter(HistoryActivity.this,listdata,R.layout.item,from,to);
        listView.setAdapter(adapter);

        final ArrayList<String> collection = new ArrayList<String>();   //将要传给收藏夹的ArrayList

        Toast.makeText(HistoryActivity.this, "长按记录可加入收藏夹", Toast.LENGTH_LONG).show();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> itemdata = listdata.get(position);
                String coll = itemdata.get("history").toString();
                if(!collection.contains(coll)) {
                    collection.add(coll + "");
                    Toast.makeText(HistoryActivity.this, "加入收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HistoryActivity.this, "不能重复收藏呦", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        //查看收藏
        Button showco = (Button)findViewById(R.id.showco);
        showco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HistoryActivity.this,CollectionActivity.class);
                if(collection!= null && !collection.isEmpty()) {
                    intent1.putStringArrayListExtra("collection", collection);
                    HistoryActivity.this.startActivity(intent1);
                }else {
                    Toast.makeText(HistoryActivity.this, "请先至少收藏一条记录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
