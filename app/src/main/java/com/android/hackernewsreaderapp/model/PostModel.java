package com.android.hackernewsreaderapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class PostModel {

    private Long id;
    private String by;
    private Long time;
    private long[] kids;
    private String url;
    private String title;
    private String text;
    @SerializedName("type")
    public PostType postType;

    public enum PostType {
        @SerializedName("story")
        STORY("story"),
        @SerializedName("ask")
        ASK("ask"),
        @SerializedName("job")
        JOB("job"),
        @SerializedName("comment")
        COMMENT("comment");

        private String string;

        PostType(String string) {
            this.string = string;
        }

        public static PostType fromString(String string) {
            if (string != null) {
                for (PostType postType : PostType.values()) {
                    if (string.equalsIgnoreCase(postType.string)) return postType;
                }
            }
            return null;
        }
    }

    public PostModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public long[] getKids() {
        return kids;
    }

    public void setKids(long[] kids) {
        this.kids = kids;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
