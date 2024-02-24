package edu.huflit.myapp.Model;

public class ThongTin {
    private String tenchuyenmuc;
    private int hinhanh;

    public String getTenchuyenmuc() {
        return tenchuyenmuc;
    }

    public void setTenchuyenmuc(String tenchuyenmuc) {
        this.tenchuyenmuc = tenchuyenmuc;
    }

    public int getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        this.hinhanh = hinhanh;
    }

    public ThongTin(String tenchuyenmuc, int hinhanh) {
        this.tenchuyenmuc = tenchuyenmuc;
        this.hinhanh = hinhanh;
    }
}
