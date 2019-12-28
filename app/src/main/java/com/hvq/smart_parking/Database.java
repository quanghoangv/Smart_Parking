package com.hvq.smart_parking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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

    public void INSERT_NHANVIEN(String idnhanvien, String ten, int sdt, Date ngaysinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO NhanVien VALUES(null, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

       statement.bindString(1, ten);
       statement.bindString(2, String.valueOf(sdt));
       statement.bindString(3, String.valueOf(ngaysinh));

       statement.executeInsert();
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
