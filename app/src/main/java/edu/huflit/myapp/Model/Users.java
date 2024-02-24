package edu.huflit.myapp.Model;

public class Users {
    private  int mId;
    private String TenTaiKhoan;
    private String matkhau;
    private String email;
    private int phanquyen;

    public Users(int mId, String matkhau) {
        this.mId = mId;
        this.matkhau = matkhau;
    }

    public Users(String tenTaiKhoan, String matkhau, String email, int phanquyen) {
        TenTaiKhoan = tenTaiKhoan;
        this.matkhau = matkhau;
        this.email = email;
        this.phanquyen = phanquyen;
    }

    public Users(String tenTaiKhoan, String email) {
        TenTaiKhoan = tenTaiKhoan;
        this.email = email;
    }

    public Users() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhanquyen() {
        return phanquyen;
    }

    public void setPhanquyen(int phanquyen) {
        this.phanquyen = phanquyen;
    }
}
