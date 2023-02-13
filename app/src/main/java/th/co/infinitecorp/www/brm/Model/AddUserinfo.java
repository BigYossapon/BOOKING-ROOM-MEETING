package th.co.infinitecorp.www.brm.Model;

public class AddUserinfo {

    String ID;
    String Name;
    int Dept_ID;
    String Phone;
    String Email;
    String User_Name;
    String Password;

    public AddUserinfo(){
        this.ID = ID;
        this.Name = Name;
        this.Dept_ID = Dept_ID;
        this.Phone = Phone;
        this.Email = Email;
        this.User_Name = User_Name;
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

    public int getDept_ID() {
        return Dept_ID;
    }

    public void setDept_ID(int dept_ID) {
        Dept_ID = dept_ID;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
