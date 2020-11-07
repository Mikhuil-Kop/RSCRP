package com.company;
import java.util.ArrayList;

public class StudentController {
    private ArrayList<Student> students = new ArrayList<>();
    private int studentCount = 0;

    public String getStudents() {
        String response = "[";
        for(Student student : students) {
            if(student.equals(students.get(students.size()-1))){
                response += "{" +
                        "\"ID\": \"" + student.getID() + "\"," +
                        "\"name\": \"" + student.getName() + "\"," +
                        "\"lastName\": \"" + student.getLastName() + "\"," +
                        "\"middleName\": \"" + student.getMiddleName() + "\"," +
                        "\"course\": \"" + student.getСourse() + "\"" +
                        "}";
            }
            else{
                response += "{" +
                        "\"ID\": \"" + student.getID() + "\"," +
                        "\"name\": \"" + student.getName() + "\"," +
                        "\"lastName\": \"" + student.getLastName() + "\"," +
                        "\"middleName\": \"" + student.getMiddleName() + "\"," +
                        "\"course\": \"" + student.getСourse() + "\"" +
                        "},";
            }

        }
        response += "]";
        return response;
    }

    public String addStudent(String name, String lastName, String middleName, String course) {
        students.add(new Student(studentCount, name, lastName, middleName, course));
        studentCount++;
        return "{\"Message\": \"Student Added\"}";
    }

    public String updateStudent(int ID, String name, String lastName, String middleName, String course) {
        for(Student student : students) {
            if(student.getID() == ID) {
                if(!name.equals("")){
                    student.setName(name);
                }
                if(!lastName.equals("")){
                    student.setLastName(lastName);
                }
                if(!middleName.equals("")){
                    student.setMiddleName(middleName);
                }
                if(!course.equals("")){
                    student.setСourse(course);
                }
            }
        }
        return "{\"Message\": \"Student with ID=" + ID + " Updated\"}";
    }

    public String deleteStudent(int ID) {

        students.removeIf(student -> student.getID() == ID);

        return "{\"Message\": \"Student with ID=" + ID + " Deleted\"}";
    }
}
