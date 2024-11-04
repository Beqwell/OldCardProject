public class OldCard {
    private String id;
    private String thema;
    private String type;
    private String country;
    private int year;
    private String author;
    private String valuable;
    private boolean isSent;
    // Constructor
    public OldCard() {}
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getThema() { return thema; }
    public void setThema(String thema) { this.thema = thema; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getValuable() { return valuable; }
    public void setValuable(String valuable) { this.valuable = valuable; }
    public boolean isSent() { return isSent; }
    public void setIsSent(boolean isSent) { this.isSent = isSent; }
    // toString method for printing OldCard info
    @Override
    public String toString() {
        return "OldCard{" +
                "id='" + id + '\'' +
                ", thema='" + thema + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", valuable='" + valuable + '\'' +
                ", isSent=" + isSent +
                '}';
    }
}
