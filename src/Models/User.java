package Models;

public class User {

    private String name;
    private String username;
    private String password;
    private Boolean admin;

    public User(String name, String username, String password, Boolean admin) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Boolean isAdmin(){
        return admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
