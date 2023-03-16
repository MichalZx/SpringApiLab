package com.example.testing;
import java.util.Comparator;

public class Album {
    private Integer userId;
    private Integer id;
    private String title;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId=userId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id=id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    /*
    public static Comparator<Albums> getCompByName()
    {
        Comparator<Albums> comp = new Comparator<Albums>(){
            @Override
            public int compare(Albums s1, Albums s2)
            {
                return s1.title.compareTo(s2.title);
            }
        };
        return comp;
    }
   */

}
