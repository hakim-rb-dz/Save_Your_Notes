package com.example.saveyournotes;

public class Note {

    private String noteid;
    private String title;
    private String content;
    private String userid;
    private String notetype;


    public Note() {}

    public Note(String title, String content, String userid, String notetype) {
        this.title = title;
        this.content = content;
        this.userid = userid;
        this.notetype = notetype;
    }

// getters and setters
    public String getnoteid()               {
        return noteid; }
    public void setnoteid(String noteid)    {
        this.noteid = noteid; }

    public String getTitle()                {
        return title; }
    public void setTitle(String title)      {
        this.title = title; }

    public String getContent()              {
        return content; }
    public void setContent(String content)  {
        this.content = content; }

    public String getUserId()               {
        return userid; }
    public void setUserId(String userId)    {
        this.userid = userId; }

    public String getnotetype()                 {
        return notetype; }
    public void setType(String notetype)        {
        this.notetype = notetype; }
}