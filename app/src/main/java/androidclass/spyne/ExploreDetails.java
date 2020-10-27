package androidclass.spyne;

/**
 * Created by hp on 10/25/2020.
 */

public class ExploreDetails {

    String username;
    String userCompany;
    String msg;


    public ExploreDetails(String username, String userCompany, String msg) {
        this.username = username;
        this.userCompany = userCompany;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
