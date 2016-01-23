package uofthacks2016.atychos.network;

/**
 * Created by vitaliy on 2016-01-23.
 */
public class AtychosURLEndPoints {
    public static final String URL = "https://still-tor-86219.herokuapp.com";

    //User end points
    public static final String USER = URL + "/user";
    public static final String USER_GET = USER + "/get";
    public static final String USER_GET_MEMBERS = USER + "/getmembers";
    public static final String USER_SEND_ALERT = USER + "/sendalert";

    //Member end points
    public static final String MEMBER = URL + "/member";
    public static final String MEMBER_ADD = MEMBER + "/add";
}