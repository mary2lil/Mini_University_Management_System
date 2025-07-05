/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management_system;

/**
 *
 * @author HP
 */
enum OperationType{
    ADD_STUDENT , REMOVE_STUDENT , ADD_COURSE , REMOVE_COURSE , ADD_ENROLLMENT, REMOVE_ENROLLMENT ;
}
public class Operation {
  private  Student student ;
  private Course course ;
  private OperationType type;

    public Operation(Student student, Course course, OperationType type) {
        this.student = student;
        this.course = course;
        this.type = type;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public OperationType getType() {
        return type;
    }
  
    @Override
    public String toString() {
        return "Operation{" + "student=" + student + ", course=" + course + ", type=" + type + '}';
    }
  
  
}
