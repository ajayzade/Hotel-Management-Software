package sample;

public class Model2 {

    String name,email,mobile,check_in,check_out;

    public Model2(String name, String email, String mobile, String check_in, String check_out) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.check_in = check_in;
        this.check_out = check_out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
}
