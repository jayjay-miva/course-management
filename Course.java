// Holds the details of one course
public class Course {
    private String code;
    private String title;
    private int unit;

    public Course(String code, String title, int unit) {
        this.code = code;
        this.title = title;
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getUnit() {
        return unit;
    }

    public void show() {
        System.out.printf("%-10s%-35s%d%n", code, title, unit);
    }
}
