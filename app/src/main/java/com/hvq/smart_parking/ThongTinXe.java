package com.hvq.smart_parking;

import java.io.Serializable;

public class ThongTinXe implements Serializable {
    private int Id;
    public String IdNfc;
    public String GioVao;
    public byte[] Hinh;
    public String GioRa;
    public String ThanhTien;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdNfc() {
        return IdNfc;
    }

    public void setIdNfc(String idNfc) {
        IdNfc = idNfc;
    }

    public String getGioVao() {
        return GioVao;
    }

    public void setGioVao(String gioVao) {
        GioVao = gioVao;
    }

    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        Hinh = hinh;
    }

    public String getGioRa() {
        return GioRa;
    }

    public void setGioRa(String gioRa) {
        GioRa = gioRa;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }

    public ThongTinXe(int id, String idNfc, String gioVao, byte[] hinh, String gioRa, String thanhTien) {
        Id = id;
        IdNfc = idNfc;
        GioVao = gioVao;
        Hinh = hinh;
        GioRa = gioRa;
        ThanhTien = thanhTien;
    }
}
