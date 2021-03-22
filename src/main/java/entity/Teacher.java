package entity;

public class Teacher {

    public Teacher(){
    }

    public Teacher(String tec_id, String tec_password) {
        this.tec_id = tec_id;
        this.tec_password = tec_password;
    }

    private String tec_id;
    private String tec_password;
    private String tec_name;
    private String tec_department;

    public String getTec_id() {
        return tec_id;
    }

    public void setTec_id(String tec_id) {
        this.tec_id = tec_id;
    }

    public String getTec_password() {
        return tec_password;
    }

    public void setTec_password(String tec_password) {
        this.tec_password = tec_password;
    }

    public String getTec_name() {
        return tec_name;
    }

    public void setTec_name(String tec_name) {
        this.tec_name = tec_name;
    }

    public String getTec_department() {
        return tec_department;
    }

    public void setTec_department(String tec_department) {
        this.tec_department = tec_department;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tec_id='" + tec_id + '\'' +
                ", tec_password='" + tec_password + '\'' +
                ", tec_name='" + tec_name + '\'' +
                ", tec_department='" + tec_department + '\'' +
                '}';
    }
}


