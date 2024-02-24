package edu.huflit.myapp.Model;

public class TruyenTranh {
    private String tenTruyen;
    private String LinkAnh;
    private String noiDungTruyen;
    private String Cate;
    private Integer idLike;

    public TruyenTranh(String tenTruyen, String linkAnh) {
        this.tenTruyen = tenTruyen;
        LinkAnh = linkAnh;
    }

    public TruyenTranh(String tieuDe, String noiDung, String img, String tacGia, int id, int idLike, String cate) {
        this.tenTruyen = tieuDe;
        this.LinkAnh = img;
        this.tacGia = tacGia;
        this.idTruyen = id;
        this.noiDungTruyen = noiDung;
        this.Cate = cate;
        this.idLike = idLike;
    }

    public Integer getIdLike() {
        return idLike;
    }

    public void setIdLike(Integer idLike) {
        this.idLike = idLike;
    }

    public String getCate() {
        return Cate;
    }

    public void setCate(String cate) {
        Cate = cate;
    }

    public String getNoiDungTruyen() {
        return noiDungTruyen;
    }

    public void setNoiDungTruyen(String noiDungTruyen) {
        this.noiDungTruyen = noiDungTruyen;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    private String tacGia;

    public int getIdTruyen() {
        return idTruyen;
    }




    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    private int idTruyen;

    public TruyenTranh() {

    }
    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }


    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }
}
