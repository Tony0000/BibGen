package ufal.ic.entities;

import java.util.Date;

/**
 * Created by manoel on 30/04/2017.
 */
public class User extends Person {

    public User(String name, Date birthDate, String address, String phone, String email) {
        super(name, birthDate, address, phone, email);
    }

}
