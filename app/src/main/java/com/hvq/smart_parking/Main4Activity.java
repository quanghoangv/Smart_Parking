package com.hvq.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    private ListView lvNhanVien;
    private ArrayList<NhanVien> dsNhanVien ;
    private NhanVienAdapter adapter;
    public static Database database;
    private Button btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        lvNhanVien = findViewById(R.id.lvNhanVien);

        dsNhanVien = new ArrayList<>();
        adapter =new NhanVienAdapter(this, R.layout.activity_chi_tiet_tt_nv, dsNhanVien);
        lvNhanVien.setAdapter(adapter);


        //get Data
        getDataNv();
        adapter.notifyDataSetChanged();

        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                alert.setTitle("Xoa");
                alert.setMessage("Xoa thong tin nhan vien?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteThongTinNv(position);
                        dsNhanVien.clear();
                        adapter.notifyDataSetChanged();
                    }
                });
               alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
                return false;
            }
        });

//        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                NhanVien nv = dsNhanVien.get(pos);
//                showEditDialog(nv);
//            }
//        });
    }

    private void getDataNv() {
        Cursor cursor = Activity_Login.database.GetData("SELECT * FROM NhanVien");
        while (cursor.moveToNext()){
            dsNhanVien.add(new NhanVien(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));

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
                    dsNhanVien.clear();
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

    private void deleteThongTinNv(int position) {
        int id = dsNhanVien.get(position).getID();
        String sql = "DELETE FROM ThongTinXe WHERE Id = " + id;
        database.QueryData(sql);
    }
}
