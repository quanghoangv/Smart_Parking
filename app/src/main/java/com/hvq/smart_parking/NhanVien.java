package com.hvq.smart_parking;

public class NhanVien {
    private String IdNhanVien;
    private String MatKhau;
    private String TenNV;

    public String getIdNhanVien() {
        return IdNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        IdNhanVien = idNhanVien;
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

    public NhanVien(String idNhanVien, String matKhau, String tenNV, String ngaySinh, String sdt) {
        IdNhanVien = idNhanVien;
        MatKhau = matKhau;
        TenNV = tenNV;
        NgaySinh = ngaySinh;
        Sdt = sdt;
    }

    private String NgaySinh;
    private String Sdt;

}
