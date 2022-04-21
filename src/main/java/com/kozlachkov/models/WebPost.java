package com.kozlachkov.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


public class WebPost {
    private int id_note;
    @NotEmpty(message = "Title should not be empty")
    @Max(value = 100, message = "max length = 100 symbols" )
    private String title;
    @NotEmpty(message = "Text should not be empty")
    private String text;
    private Date data_pub;

    public WebPost(){}

    public WebPost(int id_note, String title, String text, Date data_pub) {
        this.id_note = id_note;
        this.title = title;
        this.text = text;
        this.data_pub = data_pub;
    }

    public int getId_note() { return id_note;  }
    public void setId_note(int id_note) { this.id_note = id_note;   }
    public String getTitle() { return title;    }
    public void setTitle(String title) {  this.title = title;    }
    public String getText() {return text; }
    public void setText(String text) {  this.text = text;  }
    public Date getData_pub() {  return data_pub;  }
    public void setData_pub(Date data_pub) {  this.data_pub = data_pub; }

}
