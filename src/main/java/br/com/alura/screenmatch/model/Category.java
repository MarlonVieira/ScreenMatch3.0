package br.com.alura.screenmatch.model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    THRILLER("Thriller");

    private String categoryOMDB;

    Category(String categoryOMDB) {
        this.categoryOMDB = categoryOMDB;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOMDB.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for the given string: " + text);
    }
}