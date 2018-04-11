import Models.User;

import java.util.ArrayList;

public class DummyData {

    ArrayList<User> users = new ArrayList<>();


    public void createNewUser(String name, String username, String password, Boolean admin){
        users.add(new User(name,username,password,admin));
    }

    public void initializeDummyData(){

        createNewUser("Juha Mieto", "Mämmi666", "1234", false);
        createNewUser("Jari Mentula", "Bull", "maitorahka", true);
        createNewUser("Alvin J", "Ellis", "dolor", false);
        createNewUser("Beverly Z", "Lucas", "Nullam", false);
        createNewUser("Odysseus I", "Russo", "bibendum", false);
        createNewUser("Carlos U", "Meyers", "Aliquam", false);
        createNewUser("Kevin M", "Monroe", "ante", false);
        createNewUser("Clare W", "Hammond", "Quisque", false);
        createNewUser("Sonya A", "Hopper", "mollis", false);

    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
