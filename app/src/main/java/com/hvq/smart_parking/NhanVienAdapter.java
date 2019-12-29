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

class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NhanVien> nhanVienList;

    public NhanVienAdapter(Context context, int layout, List<NhanVien> nhanVienList) {
        this.context = context;
        this.layout = layout;
        this.nhanVienList = nhanVienList;
    }

    @Override
    public int getCount() {
        return nhanVienList.size();
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
        TextView txtTenNv, txtNgaySinh, txtSdt, txtTaiKhoan, txtMatKhau;
        ImageView imgAvatar;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NhanVienAdapter.ViewHolder holder;

        if(convertView == null){
            holder = new NhanVienAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgAvatar= (ImageView) convertView.findViewById(R.id.imgAvatar);
            holder.txtTenNv = (TextView) convertView.findViewById(R.id.tvTenNv);
            holder.txtNgaySinh = (TextView) convertView.findViewById(R.id.tvNgaySinh);
            holder.txtSdt = (TextView) convertView.findViewById(R.id.tvSdt);
            holder.txtTaiKhoan = (TextView) convertView.findViewById(R.id.tvTenDn);
            holder.txtMatKhau = (TextView) convertView.findViewById(R.id.tvMk);

            convertView.setTag(holder);
        }else {
            holder = (NhanVienAdapter.ViewHolder) convertView.getTag();
        }
        NhanVien thongTinNhanVien = nhanVienList.get(position);

        holder.txtTenNv.setText(thongTinNhanVien.getTenNV());
        holder.txtNgaySinh.setText(thongTinNhanVien.getNgaySinh());
        holder.txtSdt.setText(thongTinNhanVien.getSdt());
        holder.txtTaiKhoan.setText(thongTinNhanVien.getTenDn());
        holder.txtMatKhau.setText(thongTinNhanVien.getMatKhau());

        return convertView;
    }
}

