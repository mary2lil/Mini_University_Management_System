/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management_system;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class ConsoleUI {
    private UniversityManagementSystem university;
    Scanner in = new Scanner(System.in);

    public ConsoleUI(UniversityManagementSystem university) {
        this.university = university;
    }
    
    private void firstMenu(){
        System.out.println("\n");
        System.out.println("====== University Management System ======");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enrollment Actions");
        System.out.println("4. Undo/Redo Operations");
        System.out.println("0. Exit Program");
        System.out.println("==========================================");
        System.out.println("Enter your choice:");
    }
    
    private void studentMenu(){
        System.out.println("\n");
        System.out.println("====== Student Management ======");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Display All Students");
        System.out.println("4. Display Student's Courses");
        System.out.println("0. Back to Main Menu");
        System.out.println("Enter your choice:");
    }
    
    private void courseMenu(){
        System.out.println("\n");
        System.out.println("====== Course Management ======");
        System.out.println("1. Add course");
        System.out.println("2. Remove course");
        System.out.println("3. Display All course");
        System.out.println("4. Display Course's Students");
        System.out.println("0. Back to Main Menu");
        System.out.println("Enter your choice:");
    }
    
    private void EnrollmentActions(){
        System.out.println("\n");
        System.out.println("====== Enrollment Actions ======");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Drop Student from Course");
        System.out.println("0. Back to Main Menu");
        System.out.println("Enter your choice:");
    }
    
    private void Undo_Redo_Operations(){
        System.out.println("\n");
        System.out.println("====== Undo Redo Operations ======");
        System.out.println("1. Undo Last Operation");
        System.out.println("2. Redo Last Operation");
        System.out.println("0. Back to Main Menu");
        System.out.println("Enter your choice:");
    }
    
    private void addStudent (){
        System.out.println("Enter student name");
        String name = in.nextLine();
        System.out.println("Enter student id");
        String id = in.nextLine();
        System.out.println("Enter student major");
        String major = in.nextLine();
        university.addStudent(new Student(name,id,Major.valueOf(major.toUpperCase())));
    }
    
    private void removeStudent(){
         System.out.println("Enter student id");
        String id = in.nextLine();
        university.removeStudent(new Student(id));
    }
    
    private void displayStudentCourses(){
        System.out.println("Enter student id");
        String id = in.nextLine();
        Student student = university.getStudentById(id); 
        university.displayStudentCourses(student);
    }
    
    private void addCourse(){
        System.out.println("Enter course name");
        String name = in.nextLine();
        System.out.println("Enter course code");
        String code = in.nextLine();
        System.out.println("Enter course hours");
        int hourse = in.nextInt();
        university.addCourse(new Course(code, name, hourse));
    }
    
    private void removeCourse(){
         System.out.println("Enter course code");
        String code = in.nextLine();
       university.removeCourse(new Course(code));
    }
    
    private void displayCourse(){
        System.out.println("Enter course code");
        String code = in.nextLine();
       university.displayCourseStudents(new Course(code));
    }
    
    private void enroll(){
        System.out.println("Enter student id");
        String id = in.nextLine();
        System.out.println("Enter course code");
        String code = in.nextLine();
        Student student = university.getStudentById(id);
        Course course = university.getCourseByCode(code);
        if(course==null||student==null){
        System.out.println("Student or course not found.");
        return;
        }
        university.enrollStudentInCourse(student,course);
    }
    
    private void drop(){
        System.out.println("Enter student id");
        String id = in.nextLine();
        System.out.println("Enter course code");
        String code = in.nextLine();
        Student student = university.getStudentById(id);
        Course course = university.getCourseByCode(code);
        if(course==null||student==null){
        System.out.println("Student or course not found.");
        return;
        }
        university.removeStudentFromCourse(student,course);
    }
    
    public void start(){
        university.readCourse();
        university.readStudent();
        university.readWatinglist();
        university.readEnrollments();
        while(true){
            try{
            firstMenu();
            int Choice = in.nextInt();
            in.nextLine();
            if(Choice==0){
       university.writeCourse();
       university.writeStudent();
       university.writeWatinglist();
       university.writeEnrollments();
                return;
            }
            
            switch(Choice){
                case 1 :{
                    studentMenu();
                    int studentChoice=in.nextInt();
                    in.nextLine();
                    
                 if(studentChoice==0){
                  break;
                        }
                    switch(studentChoice){
                        case 1 :{
                            addStudent();
                            break;
                        }
                        case 2 :{
                            removeStudent();
                            break;
                        }
                        case 3 :{
                            university.displayAllStudents();
                            break;
                        }
                        case 4 :{
                            displayStudentCourses();
                                    break;
                        }
                        default:
                            System.out.println("Please try again");
                    }
                    break;
                }
                case 2 : {
                    courseMenu();
                    int courseChoice=in.nextInt();
                    in.nextLine();
                     if(courseChoice==0){
                  break;
                        }
                    switch(courseChoice){
                         case 1 :{
                             addCourse();
                     break;
                }
                         case 2 :{
                             removeCourse();
                             break;
                         }
                         case 3 :{
                             university.displayAllCourses();
                             break;
                         }
                         case 4 :{
                             displayStudentCourses();
                             break;
                         }
                         default:
                            System.out.println("Please try again");
                    }
                    break;
                    
                }
                 case 3 :{
                    EnrollmentActions();
                     int enrollment = in.nextInt();
                     in.nextLine();
                    if(enrollment==0){
                        break;
                    }
                    switch(enrollment){
                        case 1 :{
                            enroll();
                            break;
                        }
                        case 2 :{
                            drop();
                            break;
                        }
                        default:
                            System.out.println("Please try again");
                    }
                  break;  
                }
                case 4 :{
                    Undo_Redo_Operations();
                    in.nextLine();
                    int operationChoice = in.nextInt();
                    if(operationChoice==0){
                        break;
                    }
                    switch(operationChoice){
                        case 1 :{
                            university.undoLastOperation();
                            break;
                        }
                        case 2 :{
                            university.redoLastOperation();
                            break;
                        }
                        default:
                            System.out.println("Please try again");
                    }
                    break;
                }
            }
            }catch(InputMismatchException e){
                System.out.println("Wrong enter please try again");
                in.nextLine();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
