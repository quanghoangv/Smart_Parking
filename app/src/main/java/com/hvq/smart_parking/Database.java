package com.hvq.smart_parking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void INSERT_THONGTINXE(String idnfc, String giovao, byte[] hinh, String giora, String thanhtien){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ThongTinXe VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, idnfc);
        statement.bindString(2, giovao);
        statement.bindBlob(3, hinh);
        if(giora != null){
            statement.bindString(4, giora);
        }
        if(thanhtien != null){
            statement.bindString(5, thanhtien);
        }

            statement.executeInsert();
    }

    public boolean INSERT_NHANVIEN(String idnhanvien, String tendn, String mk, String ten, String sdt, String ngaysinh){

       try {
           SQLiteDatabase database = getWritableDatabase();

            String sql = "INSERT INTO NhanVien VALUES(null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

           statement.bindString(1, String.valueOf(tendn));
           statement.bindString(2, String.valueOf(mk));
           statement.bindString(3, String.valueOf(ten));
           statement.bindString(4, String.valueOf(sdt));
           statement.bindString(5, String.valueOf(ngaysinh));


           statement.executeInsert();
            return true;
       }catch (Exception e){
           Log.e("ninh", e.toString());
           return false;
       }
    }

    //CREATE TABLE IF NOT EXISTS NhanVien
    // (Id INTEGER PRIMARY KEY AUTOINCREMENT,
    // TenDN VARCHAR(150),
    // MatKhau VARCHAR(150),
    // Ten VARCHAR(150),
    // Sdt VARCHAR(150),
    // NgaySinh VARCHAR(150))");
    //
    public boolean UPDATE_NHANVIEN(String idnhanvien, String tendn, String mk,
                                   String ten, String sdt, String ngaysinh){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues args = new ContentValues();
//        args.put("Id", idnhanvien);
        args.put("TenDN", tendn);
        args.put("MatKhau", mk);
        args.put("Ten", ten);
        args.put("Sdt", sdt);
        args.put("NgaySinh", ngaysinh);
        int i =  database.update("NhanVien", args,  " Id = " + idnhanvien, null);
        return i > 0;
    }



    public void CAPNHAT(String GioRa, String ID){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE ThongTinXe SET GioRa ='" + GioRa +"' WHERE IdNfc = '" + ID + "'";
        database.execSQL(sql);
    }


    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
