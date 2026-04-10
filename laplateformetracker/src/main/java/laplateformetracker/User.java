package laplateformetracker;

public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isManager;

    public User(String id, 
                String email,
                String firstName,
                String lastName,
                Boolean isManager
                ){
                    this.id = id;
                    this.email = email;
                    this.firstName = firstName;
                    this.lastName = lastName;
                }

    public String getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public Boolean getIsManager(){
        return this.isManager;
    }
}
