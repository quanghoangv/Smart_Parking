package com.hvq.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {

    private ListView lvDoanhThu;
    private XeAdapter adapter;
    private ArrayList<ThongTinXe> xeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        lvDoanhThu = findViewById(R.id.lvDoanhThu);
        xeArrayList = new ArrayList<>();
        adapter =new XeAdapter(this, R.layout.activity_chi_tiet_xe, xeArrayList);
        lvDoanhThu.setAdapter(adapter);

        //get Data
        Cursor cursor = MainActivity.database.GetData("SELECT * FROM ThongTinXe where GioRa is not null");
        if(cursor!=null){
            while (cursor.moveToNext()){
                xeArrayList.add(new ThongTinXe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));

            }
        }
        adapter.notifyDataSetChanged();
    }
}
