package com.hvq.smart_parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Login extends AppCompatActivity {

    EditText edtname, edtpassword;
    Button btndangnhap, btndangky, btnthoat;
    String ten, mk;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        ControlButton();

        database =new Database(this,"SmartParking.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS NhanVien(IdNhanVien INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(150), Sdt INTEGER, NgaySinh DATE)");
    }
private void ControlButton() {
    btnthoat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
            builder.setTitle("Ban co chac muon thoat khoi app");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    });
    btndangky.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(Activity_Login.this);
            dialog.setTitle("Hop thoai xu ly");
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.customdialog);
            final EditText edttk = (EditText)dialog.findViewById(R.id.edttk);
            final EditText edtmk = (EditText)dialog.findViewById(R.id.edtmk);
            Button btnhuy = (Button)dialog.findViewById(R.id.btnhuy);
            Button btndongy = (Button)dialog.findViewById(R.id.btndongy);

            btndongy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ten = edttk.getText().toString().trim();
                    mk = edtmk.getText().toString().trim();

                    edtname.setText(ten);
                    edtpassword.setText(mk);

                    dialog.cancel();
                }
            });
            btnhuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    });
    btndangnhap.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(edtname.getText().length() !=0 && edtpassword.getText().length() !=0){
                if(edtname.getText().toString().equals(ten) && edtpassword.getText().toString().equals(mk)){
                    Toast.makeText(Activity_Login.this, "Ban da dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                    startActivity(intent);
                }else if(edtname.getText().toString().equals("quang")&& edtpassword.getText().toString().equals("anhquang11a3")){
                    Toast.makeText(Activity_Login.this, "Ban da dang nhap thanh cong",Toast.LENGTH_SHORT).show();
//                    database.INSERT_NHANVIEN();
                    Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Activity_Login.this, "Ban da dang nhap that bai",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Activity_Login.this, "Moi ban nhap du thong tin",Toast.LENGTH_SHORT).show();
            }
        }
    });
}

    private void Anhxa(){
        edtname = (EditText)findViewById(R.id.edtname);
        edtpassword = (EditText)findViewById(R.id.edtpassword);
        btndangnhap = (Button)findViewById(R.id.btndangnhap);
        btndangky = (Button)findViewById(R.id.btndangky);
        btnthoat = (Button)findViewById(R.id.btnthoat);
    }
}

