package code.model;

import javax.persistence.*;

@Entity
@Table(name = "adslide")
public class AdSlide {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String link;

    private String info;

    public AdSlide() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
