package com.hvq.smart_parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class Activity_Login extends AppCompatActivity {

    EditText edtname, edtpassword;
    Button btndangnhap, btndangky, btnthoat;
    String tenDn, mk;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        ControlButton();

        database =new Database(this,"SmartParking.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS NhanVien(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenDN VARCHAR(150), MatKhau VARCHAR(150), Ten VARCHAR(150), Sdt VARCHAR(150), NgaySinh VARCHAR(150))");

        //check account has been log in

        SharedPreferences sh_Pref = this.getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
        boolean check = sh_Pref.getBoolean("IS_LOGIN", false);
        if(check){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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
            final EditText edtTen = (EditText)dialog.findViewById(R.id.edtTen);

            final EditText edtNgaySinh = (EditText)dialog.findViewById(R.id.edtNgaySinh);

            final EditText edtSdt = (EditText)dialog.findViewById(R.id.edtSdt);

            Button btnhuy = (Button)dialog.findViewById(R.id.btnhuy);
            Button btndongy = (Button)dialog.findViewById(R.id.btndongy);

            btndongy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tenDn = edttk.getText().toString().trim();
                    mk = edtmk.getText().toString().trim();
                    String tenNv = edtTen.getText().toString().trim();
                    String sdt = edtSdt.getText().toString().trim();
                    String ngaySinh = edtNgaySinh.getText().toString().trim();

                    edtname.setText(tenDn);
                    edtpassword.setText(mk);


                    if (database.INSERT_NHANVIEN(null, tenDn, mk, tenNv, sdt, ngaySinh)) {
                        Toast.makeText(Activity_Login.this, "Dang Ki Thanh Cong!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    } else {
                        Toast.makeText(Activity_Login.this, "Dang Ki That Bai!", Toast.LENGTH_SHORT).show();
                    }
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
            String tenDn = edtname.getText().toString().trim();
            String mk = edtpassword.getText().toString().trim();

            if(tenDn.length() !=0 && edtpassword.getText().length() !=0){
                //query
                Cursor cursor = database.GetData("SELECT * FROM NhanVien where TenDN='"+tenDn+"' and MatKhau = '"+mk+"'");
                if(cursor != null && cursor.getCount()>0){
                    Toast.makeText(Activity_Login.this, "Ban da dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                    //save
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("IS_LOGIN", true);
                    editor.apply();
                    Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(Activity_Login.this, "Ban da dang nhap that bai",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("IS_LOGIN", false);
                    editor.apply();
                }
            }else {
                Toast.makeText(Activity_Login.this, "Moi ban nhap du thong tin",Toast.LENGTH_SHORT).show();
            }
        }
    });
}

    private void Anhxa(){
        edtname = (EditText)findViewById(R.id.edtname);;
        edtpassword = (EditText)findViewById(R.id.edtpassword);
        btndangnhap = (Button)findViewById(R.id.btndangnhap);
        btndangky = (Button)findViewById(R.id.btndangky);
        btnthoat = (Button)findViewById(R.id.btnthoat);
    }
}

