package edu.huflit.myapp.Model;

public class YeuThich {
    private int idYt, trangThai, idTruyen, idTK;


    public YeuThich(int trangThai, int idTruyen, int idTK) {
        this.trangThai = trangThai;
        this.idTruyen = idTruyen;
        this.idTK = idTK;
    }

    public YeuThich(int idLike) {
        this.idYt = idLike;
    }

    public int getIdYt() {
        return idYt;
    }

    public void setIdYt(int idYt) {
        this.idYt = idYt;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }
}
