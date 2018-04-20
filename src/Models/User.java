package Models;


public class User implements java.io.Serializable{

    private String name;
    private String address;
    private String username;
    private String password;
    private Boolean admin;

    public User(String name, String address, String username, String password, Boolean admin) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
