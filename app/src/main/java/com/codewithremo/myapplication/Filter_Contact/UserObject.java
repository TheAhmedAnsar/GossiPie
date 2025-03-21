package com.codewithremo.myapplication.Filter_Contact;

public class UserObject {

    private  String name , phone;

    public UserObject(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public  String getPhone(){return  phone ;}

    public String getName(){ return  name; }

    public void setName(String name) {
        if (name==null)
            return;
//        this.name.add(name.trim());
        this.name = name.trim();
    }
}
