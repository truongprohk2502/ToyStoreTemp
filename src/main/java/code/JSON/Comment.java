package code.JSON;

import java.sql.Timestamp;

public class Comment {

    private Long ratingStar;

    private String comment;

    private String timeAgo;

    private String nameUser;

    private Long toyId;

    private String username;

    public Comment() {
    }

    public Comment(Long ratingStar, String comment, String timeAgo, String nameUser) {
        this.ratingStar = ratingStar;
        this.comment = comment;
        this.timeAgo = timeAgo;
        this.nameUser = nameUser;
    }

    public Long getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(Long ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Long getToyId() {
        return toyId;
    }

    public void setToyId(Long toyId) {
        this.toyId = toyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
