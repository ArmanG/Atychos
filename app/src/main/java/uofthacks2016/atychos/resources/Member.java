package uofthacks2016.atychos.resources;

/**
 * Created by vitaliy on 2016-01-23.
 */
public class Member {
    public static long NULL_ID = -1;

    // Instance Variables
    private long id;
    private String number;

    public User(String number, long id) {
        this.id = id;
        this.number = number;
    }

    public User(String number) {
        this.id = NULL_ID;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
