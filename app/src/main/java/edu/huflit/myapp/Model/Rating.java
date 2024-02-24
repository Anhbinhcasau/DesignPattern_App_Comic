package edu.huflit.myapp.Model;

public class Rating {
    private int idRating;
    private float rating;
    private int userId;
    private int comicId;

    public Rating(float rating, int userId, int comicId) {
        this.rating = rating;
        this.userId = userId;
        this.comicId = comicId;
    }



    public Rating(int idRating, float ratingbar) {
        this.idRating = idRating;
        this.rating = ratingbar;
    }

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }
}
