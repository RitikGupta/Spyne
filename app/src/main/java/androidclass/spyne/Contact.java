package androidclass.spyne;

/**
 * Created by hp on 10/25/2020.
 */

public class Contact {
    //private variables
   int _id;
    String _email;
    String _password;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String email, String password){
        this._id = id;
        this._email = email;
        this._password = password;
    }

    // constructor
    public Contact(String name, String _phone_number){
        this._email = name;
        this._password = _phone_number;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._email;
    }

    // setting name
    public void setName(String email)
    {
        this._email = email;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._password;
    }

    // setting phone number
    public void setPhoneNumber(String password){
        this._password = password;
    }
}
