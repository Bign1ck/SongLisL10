package sg.edu.rp.c346.id22005813.songlist;

public class Task {
    private int id;
    private String title;
    private String singer;
    private String year;
    private int rating;

    public Task(String title, String singer, String year, int rating) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.year = year;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }

    public String getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nSinger: " + singer + "\nYear: " + year + "\nRating: " + rating;
    }
}


