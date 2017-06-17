package com.android.hackernewsreaderapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class PostModel {

    public Long id;
    public String by;
    public Long time;
    public long[] kids;
    public String url;
    public String title;
    public String text;
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

    public PostModel() { }
}
