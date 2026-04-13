package laplateformetracker;
import laplateformetracker.models.DataBase;

public class User {
    private DataBase database;
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isManager;

    public User(DataBase database,
                Integer id,
                String email,
                String firstName,
                String lastName,
                Boolean isManager
                ){
                    this.database = database;
                    this.id = id;
                    this.email = email;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.isManager = isManager;
                }

    public DataBase getDatabase(){
        return this.database;
    }

    public Integer getId(){
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
