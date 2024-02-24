package edu.huflit.myapp.Model;

public class TheLoaiTruyen {
    private String tenTL;
    private Integer IdTruyen;
    private Integer idTl;

    public Integer getIdTruyen() {
        return IdTruyen;
    }

    public void setIdTruyen(Integer idTruyen) {
        IdTruyen = idTruyen;
    }
    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public TheLoaiTruyen() {
    }

    public TheLoaiTruyen(String tenTL, Integer idTruyen, Integer idTL) {
        this.tenTL = tenTL;
        IdTruyen = idTruyen;
        this.idTl = idTL;
    }

    public Integer getIdTl() {
        return idTl;
    }

    public void setIdTl(Integer idTl) {
        this.idTl = idTl;
    }
}
