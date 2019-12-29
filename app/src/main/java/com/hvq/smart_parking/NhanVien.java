package com.hvq.smart_parking;

public class NhanVien {
    private int ID;
    private String TenDn;
    private String MatKhau;
    private String TenNV;
    private String NgaySinh;
    private String Sdt;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenDn() {
        return TenDn;
    }

    public void setTenDn(String tenDn) {
        TenDn = tenDn;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public NhanVien(String TenDn, String matKhau, String tenNV, String ngaySinh, String sdt) {
        TenDn = TenDn;
        MatKhau = matKhau;
        TenNV = tenNV;
        NgaySinh = ngaySinh;
        Sdt = sdt;
    }


}
