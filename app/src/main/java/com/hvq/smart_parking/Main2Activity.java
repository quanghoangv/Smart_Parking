package com.hvq.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ImageView imgHinh;
    TextView lbId;
    Button btnXeVao, btnHuyBo;
    TextView tvIdNfc, tvGioVao, tvGioRa;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        Intent intent = this.getIntent();
//        String ID = intent.getStringExtra("IdNfc").toString();
//        if(ID != null){
//            tvIdNfc.setText(ID);
//        }

        Anhxa();


        btnXeVao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                Log.e("ninh", hinhAnh.toString());
                MainActivity.database.INSERT_THONGTINXE(
                        tvIdNfc.getText().toString().trim(),
                        tvGioVao.getText().toString().trim(),
                        hinhAnh,
                        null,
                        null

                );
                Toast.makeText(Main2Activity.this,"Da Them", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });





        btnHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mhdanhsach = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mhdanhsach);
            }
        });

        //Chụp hình
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mayChupHinh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(mayChupHinh, 8888);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Intent intent = this.getIntent();
        String ID = intent.getStringExtra("IdNfc").toString();
        tvIdNfc.setText(ID);
        String GioVao = intent.getStringExtra("Gio Vao").toString();
        tvGioVao.setText(GioVao);

        if(requestCode== 8888  && resultCode == RESULT_OK && data != null){
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            imgHinh.setImageBitmap(photo);
            btnHuyBo.setVisibility(View.VISIBLE);
            btnXeVao.setVisibility(View.VISIBLE);
            tvIdNfc.setVisibility(View.VISIBLE);
            tvGioVao.setVisibility(View.VISIBLE);
            tvGioRa.setVisibility(View.VISIBLE);
            lbId.setVisibility(View.VISIBLE);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    //    @Override
//    protected void onNewIntent(Intent intent) {
//        intent = this.getIntent();
//        String firstName= intent.getStringExtra("firstName");
//        String lastName = intent.getStringExtra("lastName");
//        Log.d("NfcTag", firstName + lastName);
//    }
    public void Anhxa(){
        imgHinh= (ImageView)findViewById(R.id.imageViewCamera);
        btnXeVao = (Button)findViewById(R.id.btnXeVao);
        btnHuyBo = (Button)findViewById(R.id.btnHuyBo);
        tvIdNfc = (TextView) findViewById(R.id.tvIdNfc);
        tvGioVao = (TextView) findViewById(R.id.tvGioVao);
        tvGioRa = (TextView)findViewById(R.id.tvGioRa);
        lbId = (TextView) findViewById(R.id.lbID);

        btnHuyBo.setVisibility(View.INVISIBLE);
        btnXeVao.setVisibility(View.INVISIBLE);
        tvIdNfc.setVisibility(View.INVISIBLE);
        tvGioVao.setVisibility(View.INVISIBLE);
        tvGioRa.setVisibility(View.INVISIBLE);
        lbId.setVisibility(View.INVISIBLE);
    }
//
//    public byte[] ImageView_To_Byte(ImageView h){
//        BitmapDrawable drawable = (BitmapDrawable) h.getDrawable();
//        Bitmap bmp = drawable.getBitmap();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }

}