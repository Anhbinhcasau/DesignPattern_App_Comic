package edu.huflit.myapp.Model;

public class List_Comment {
    private String nameUser;
    private  String comment;
    private int pq;

    public String getNameUserN() {
        return nameUserN;
    }

    public void setNameUserN(String nameUserN) {
        this.nameUserN = nameUserN;
    }

    private String nameUserN;

    public int getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(int idCmt) {
        this.idCmt = idCmt;
    }

    private  int idCmt;

    public int getPq() {
        return pq;
    }

    public void setPq(int pq) {
        this.pq = pq;
    }


    public List_Comment(String nameUser, String comment, int pq,int idTruyen) {
        this.nameUser = nameUser;
        this.comment = comment;
        this.pq = pq;
        this.idTruyen = idTruyen;
    }

    private String imgAvatar;

    public List_Comment() {

    }


    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    private int idTruyen;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }
}
