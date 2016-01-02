package pl.edu.wat.wcy.model.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Checkbox implements Serializable {
    private int id;
    private boolean horizontal;
    private boolean vertical;
    private String path;
    private State state;


    public Checkbox() {
        super();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
