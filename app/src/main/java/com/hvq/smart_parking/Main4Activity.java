package com.hvq.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private ListView lvNhanVien;
    private ArrayList<NhanVien> dsNhanVien ;
    private NhanVienAdapter adapter;
    public static Database database;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lvNhanVien = findViewById(R.id.lvNhanVien);
        txtEmpty = findViewById(R.id.txtEmpty);

        dsNhanVien = new ArrayList<>();
        adapter = new NhanVienAdapter(this, R.layout.activity_chi_tiet_tt_nv, dsNhanVien);
        lvNhanVien.setAdapter(adapter);


        //get Data
        getDataNv();
        Log.e("ninh",""+ dsNhanVien.size());
        adapter.notifyDataSetChanged();

        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        Main4Activity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        if(deleteThongTinNv(position)){
                            getDataNv();
                            adapter.notifyDataSetChanged();
                        }

                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }
        });


        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                NhanVien nv = dsNhanVien.get(pos);
                showEditDialog(nv);
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //get Data
//        getDataNv();
//        adapter.notifyDataSetChanged();
//
//    }

    private void getDataNv() {
        dsNhanVien.clear();
        Cursor cursor = Activity_Login.database.GetData("SELECT * FROM NhanVien");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                dsNhanVien.add(new NhanVien(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));

            }
        }
    }

    private void showEditDialog(final NhanVien nhanVien) {
        final Dialog dialog = new Dialog(Main4Activity.this);
        dialog.setTitle("Sua");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit_ttnv_dialog);
        final EditText edtTaiKhoan = (EditText)dialog.findViewById(R.id.edtTaiKhoan);
        final EditText edtMk = (EditText)dialog.findViewById(R.id.edtMk);
        final EditText edtTenNv = (EditText)dialog.findViewById(R.id.edtTenNv);
        final EditText edtNgaySinh = (EditText)dialog.findViewById(R.id.edtNgaySinh);
        final EditText edtSdt = (EditText)dialog.findViewById(R.id.edtSdt);
        Button btnHuy = (Button)dialog.findViewById(R.id.btnHuy);
        Button btnUpdate = (Button)dialog.findViewById(R.id.btnUpdate);

        if(nhanVien != null){
            edtTaiKhoan.setText(nhanVien.getTenDn());
            edtMk.setText(nhanVien.getMatKhau());
            edtTenNv.setText(nhanVien.getTenNV());
            edtNgaySinh.setText(nhanVien.getNgaySinh());
            edtSdt.setText(nhanVien.getSdt());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDn = edtTaiKhoan.getText().toString().trim();
                String mk = edtMk.getText().toString().trim();
                String tenNv = edtTenNv.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String ngaySinh = edtNgaySinh.getText().toString().trim();

                if (Activity_Login.database.UPDATE_NHANVIEN(String.valueOf(nhanVien.getID()), tenDn, mk, tenNv, sdt, ngaySinh)) {
                    Toast.makeText(Main4Activity.this, "Update Thanh Cong!", Toast.LENGTH_SHORT).show();
                    getDataNv();
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(Main4Activity.this, "Update That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean deleteThongTinNv(int position) {
        int id = dsNhanVien.get(position).getID();
        boolean res = Activity_Login.database.DELETE_NHANVIEN(String.valueOf(id));
        if(res)
            Toast.makeText(this, "Xoa thanh cong!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Xoa that bai!", Toast.LENGTH_SHORT).show();
        return  res;
    }
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }
}
