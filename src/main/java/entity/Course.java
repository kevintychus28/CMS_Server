package entity;

public class Course {

    public Course(){
    }

    public Course(String cou_name, String cou_teacher) {
        this.cou_name = cou_name;
        this.cou_teacher = cou_teacher;
    }

    private String cou_id;
    private String cou_name;
    private String cou_teacher;
    private String cou_classroom;
    private String cou_weekday;
    private String cou_period;

    public String getCou_id() {
        return cou_id;
    }

    public void setCou_id(String cou_id) {
        this.cou_id = cou_id;
    }

    public String getCou_name() {
        return cou_name;
    }

    public void setCou_name(String cou_name) {
        this.cou_name = cou_name;
    }

    public String getCou_teacher() {
        return cou_teacher;
    }

    public void setCou_teacher(String cou_teacher) {
        this.cou_teacher = cou_teacher;
    }

    public String getCou_classroom() {
        return cou_classroom;
    }

    public void setCou_classroom(String cou_classroom) {
        this.cou_classroom = cou_classroom;
    }

    public String getCou_weekday() {
        return cou_weekday;
    }

    public void setCou_weekday(String cou_weekday) {
        this.cou_weekday = cou_weekday;
    }

    public String getCou_period() {
        return cou_period;
    }

    public void setCou_period(String cou_period) {
        this.cou_period = cou_period;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cou_id='" + cou_id + '\'' +
                ", cou_name='" + cou_name + '\'' +
                ", cou_teacher='" + cou_teacher + '\'' +
                ", cou_classroom='" + cou_classroom + '\'' +
                ", cou_weekday='" + cou_weekday + '\'' +
                ", cou_period='" + cou_period + '\'' +
                '}';
    }
}
