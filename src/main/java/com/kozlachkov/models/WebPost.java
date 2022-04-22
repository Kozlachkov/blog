package com.kozlachkov.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WebPost {
    private int id;
    private int id_note;
    //@NotEmpty(message = "Title should not be empty")
    //@Size(min = 1, max=50, message = "not less 2 and not more 50 symbols")
    private String title;
    //@NotEmpty(message = "Text should not be empty")
    private String text;
    private Date data_pub;

    public WebPost(){
        /*SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        formatForDateNow.format(this.data_pub);*/
    }

    public WebPost(int id, int id_note, String title, String text, Date data_pub) {
        this.id = id;
        this.id_note = id_note;
        this.title = title;
        this.text = text;
        this.data_pub =  data_pub;
    }

    public int getId() { return id;  }
    public void setId(int id) { this.id = id;   }
    public int getId_note() { return id_note;  }
    public void setId_note(int id_note) { this.id_note = id_note;   }
    public String getTitle() { return title;    }
    public void setTitle(String title) {  this.title = title;    }
    public String getText() {return text; }
    public void setText(String text) {  this.text = text;  }
    public Date getData_pub() {  return data_pub;  }
    public void setData_pub(Date data_pub) {  this.data_pub = data_pub;}

}
