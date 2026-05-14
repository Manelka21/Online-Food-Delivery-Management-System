package com.fooddelivery.model;

public abstract class Person {

    private String personId;
    private String name;
    private String email;
    private String password;
    private String phone;

    public Person() {}

    public Person(String personId, String name, String email, String password, String phone) {
        this.personId = personId;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
    }

    public abstract String getRole();

    public String getPersonId()              { return personId; }
    public void   setPersonId(String id)     { this.personId = id; }

    public String getName()                  { return name; }
    public void   setName(String n)          { this.name = n; }

    public String getEmail()                 { return email; }
    public void   setEmail(String e)         { this.email = e; }

    public String getPassword()              { return password; }
    public void   setPassword(String p)      { this.password = p; }

    public String getPhone()                 { return phone; }
    public void   setPhone(String ph)        { this.phone = ph; }
}