package models;

import java.util.List;

public class Pet {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

    public static class Category {
        public long id;
        public String name;
    }

    public static class Tag {
        public long id;
        public String name;
    }
}
