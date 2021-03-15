package entity;

public class Student {

    public Student(){
    }

    public Student(String stu_id, String stu_password) {
        this.stu_id = stu_id;
        this.stu_password = stu_password;
    }

    private String stu_id;
    private String stu_password;
    private String stu_name;

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_password() {
        return stu_password;
    }

    public void setStu_password(String stu_password) {
        this.stu_password = stu_password;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }


    @Override
    public String toString() {
        return "entity.User{" +
                "username='" + stu_id + '\'' +
                ", password='" + stu_password + '\'' +
                '}';
    }
}


