package edu.huflit.myapp.Model;

public class TapTruyen {
    private Integer tenTap;
    private Integer Id;
    private Integer IdTruyen;

    public TapTruyen(Integer tenTap, Integer id, Integer idTruyen) {
        this.tenTap = tenTap;
        Id = id;
        IdTruyen = idTruyen;
    }


    public Integer getIdTruyen() {
        return IdTruyen;
    }

    public void setIdTruyen(Integer idTruyen) {
        IdTruyen = idTruyen;
    }

    public TapTruyen() {
    }

    public Integer getTenTap() {
        return tenTap;
    }

    public void setTenTap(Integer tenTap) {
        this.tenTap = tenTap;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
