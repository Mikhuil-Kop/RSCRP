public class Student {

    private int ID;
    private String name;
    private String lastName;
    private String middleName;
    private String course;

    public Student(int ID,String name, String lastName, String middleName, String course) {
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.course = course;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setСourse(String course) {
        this.course = course;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getСourse() {
        return course;
    }
}
