package pl.edu.wat.wcy.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class State implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String date;
    private String path;
    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    private List<Checkbox> checkboxList = new ArrayList<>();

    public State() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Checkbox> getCheckboxList() {
        return checkboxList;
    }

    public void setCheckboxList(List<Checkbox> checkboxList) {
        this.checkboxList = checkboxList;
    }

    @PrePersist
    protected void onCreate() {
        date = (new Date()).toString();
        System.out.println("date: " + date);
        System.out.println();
    }
}
