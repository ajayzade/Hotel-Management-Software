package sample;

public class Model {
    String name,address,contact,dob,jod,profession;

    public Model(String name, String address, String contact, String dob, String jod, String profession) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.jod = jod;
        this.profession = profession;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJod() {
        return jod;
    }

    public void setJod(String jod) {
        this.jod = jod;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
