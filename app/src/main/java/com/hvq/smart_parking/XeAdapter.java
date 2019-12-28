package com.hvq.smart_parking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class XeAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ThongTinXe> thongTinXeList;

    public XeAdapter(Context context, int layout, List<ThongTinXe> thongTinXeList) {
        this.context = context;
        this.layout = layout;
        this.thongTinXeList = thongTinXeList;
    }

    @Override
    public int getCount() {
        return thongTinXeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtIdNfc, txtGioVao;
        ImageView imgHinh;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtIdNfc = (TextView) convertView.findViewById(R.id.tvIdNfcCustom);
            holder.txtGioVao = (TextView) convertView.findViewById(R.id.tvGioVaoCustom);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.imageViewHinhCustom);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ThongTinXe thongTinXe = thongTinXeList.get(position);

        holder.txtIdNfc.setText(thongTinXe.getIdNfc());
        holder.txtGioVao.setText(thongTinXe.getGioVao());

        //chuyen byte[] -> bitmap
        byte[] hinhAnh = thongTinXe.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);

        holder.imgHinh.setImageBitmap(bitmap);
        return convertView;
    }
}
