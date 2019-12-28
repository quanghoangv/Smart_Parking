package com.hvq.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    ImageView imgHinh;
    TextView lbId;
    Button btnXeRa, btnHuyBo;
    TextView tvIdNfc, tvGioVao, tvGioRa, tvThanhTien;
    byte[] hinhAnh;
    String thanhTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        AnhXa();

      btnXeRa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id = tvIdNfc.getText().toString().trim();
            String gioVao = tvGioVao.getText().toString().trim();
            String gioRa = tvGioRa.getText().toString().trim();


            MainActivity.database.INSERT_THONGTINXE(id, gioVao, hinhAnh, gioRa, thanhTien);

            Toast.makeText(Main3Activity.this, "Da Them", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Main3Activity.this, MainActivity.class));
        }
    });

        Intent intent = getIntent();
        String ID = intent.getStringExtra("IdNfc");
        tvIdNfc.setText(ID);
        String GioVao = intent.getStringExtra("Gio Vao");
        tvGioVao.setText(GioVao);
        String GioRa = intent.getStringExtra("Gio Ra");
        tvGioRa.setText(GioRa);
        hinhAnh = intent.getByteArrayExtra("Hinh Anh");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(hinhAnh);
        Bitmap hinh = BitmapFactory.decodeStream(inputStream);
        imgHinh.setImageBitmap(hinh);
        thanhTien = tinhTien(tvGioVao.getText().toString().trim(), tvGioRa.getText().toString().trim());
        tvThanhTien.setText(thanhTien);
    }
    private String tinhTien(String gioVao, String gioRa){
        int heso = 5000;
        String res="Thanh Tien: 0 VND";
        long secondsGioVao = new Date(gioVao).getTime()/1000;
        long secondsGioRa = new Date(gioRa).getTime()/1000;
        long diff = secondsGioRa - secondsGioVao;
        long tien = diff * heso / 3600;
        res = "Thanh Tien: "+ String.valueOf(tien) +" VND";
        return res;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa() {
        imgHinh= (ImageView)findViewById(R.id.imageViewCamera);
        btnXeRa = (Button)findViewById(R.id.btnXeRa);
        btnHuyBo = (Button)findViewById(R.id.btnHuyBo);
        tvIdNfc = (TextView) findViewById(R.id.tvIdNfc);
        tvGioVao = (TextView) findViewById(R.id.tvGioVao);
        tvGioRa = (TextView)findViewById(R.id.tvGioRa);
        lbId = (TextView) findViewById(R.id.lbID);
        tvThanhTien = findViewById(R.id.tvThanhTien);
    }

}
