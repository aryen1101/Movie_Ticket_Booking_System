package models;

public class Movie {

    private final String id;
    private final String title;
    private final int durationMins;

    public Movie(String id, String title, int durationMins) {
        this.id = id;
        this.title = title;
        this.durationMins = durationMins;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMins() {
        return durationMins;
    }
}
