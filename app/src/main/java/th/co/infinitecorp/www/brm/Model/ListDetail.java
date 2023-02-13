package th.co.infinitecorp.www.brm.Model;

public class ListDetail {
    String StartDate;
    String EndDate;
    String Name;
    String Description;
    String department;
    String email;
    String phone;
    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }





    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public ListDetail(String startDate, String endDate,  String name, String description, String department, String email, String phone) {
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Name = name;
        this.Description = description;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }




}
