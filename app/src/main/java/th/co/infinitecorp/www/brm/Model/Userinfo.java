package th.co.infinitecorp.www.brm.Model;

public class Userinfo {
    String ID;
    String Name;
    String DepartmentName;
    String PhoneNumber;
    String Email;
    String UserName;
    String Password;

    public Userinfo(){
        this.ID = ID;
        this.Name = Name;
        this.DepartmentName = DepartmentName;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.UserName = UserName;
        this.Password = Password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
