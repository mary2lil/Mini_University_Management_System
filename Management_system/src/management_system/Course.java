/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author HP
 */
public class Course {

    private String code ;
    private String name;
    private int hours ;
    private int maxStudent = 30;
    private List<Student>students=new ArrayList<>();
    private Queue<Student> waitingList  = new LinkedList<>();


    //Constructors
    public Course(String code) {
        this.code = code;
    }

    public Course(String code, String name, int hours) {
        this.code = code;
        this.name = name;
        this.hours = hours;
    }

    //Getters&Settters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

     //private methods
    private boolean addStudent(Student student){
        if(students.size()>=maxStudent){
            for(Student s : waitingList){
                if(s.getId().equals(student.getId())){
                    return false;
                }
            }
             waitingList.add(student);
              return false;
        }
        for(Student student1 : students){
            if(student1.getId().equals(student.getId())){
                return false;
            }
        }
        students.add(student);
        return true;
    }

     private boolean removeStudent(Student student){
        if(students.isEmpty()){
            return false;
        }
       Iterator<Student>iterator=students.iterator();
       while(iterator.hasNext()){
           Student s = iterator.next();
           if(s.getId().equals(student.getId())){
               iterator.remove();
               if(!waitingList.isEmpty()){
                   Student student1 = waitingList.poll();
                   students.add(student1);
               }
               return true;
           }
        }
           return false;
    }

     public boolean enrollStudent(Student student){
         return addStudent(student);
     }

     public boolean removeStudentFromCourse(Student student){
         return removeStudent(student);
     }

     public int getNumberOfStudents(){
         return students.size();
     }

    public void displayAllStudent(){
        if(students.isEmpty()){
            System.out.println("There are no students for this course");
            return;
        }
            System.out.println("List of students enrolled in course \"" + name + "\":");
        for(Student student : students){
           student.displayInfoStudent();
        }
    }

    public void displayWaitingList(){
        if(waitingList.isEmpty()){
             System.out.println("No students in waiting list for this course.");
             return;
        }
        int count=1;
        for(Student s : waitingList){
            System.out.print(count+". ");
            s.displayInfoStudent();
            count++;
        }
    }

    public boolean isFull(){
        return students.size()==maxStudent;
    }

    @Override
    public String toString() {
        return "Course{" + "code=" + code + ", name=" + name + ", hours=" + hours + ", maxStudent=" + maxStudent + '}';
    }

     public void writeWatingStudent(){
         if(waitingList.isEmpty()){
                 return;
             }
         try(BufferedWriter write = new BufferedWriter(new FileWriter("Waiting_" + code + ".txt"))){
             for(Student s : waitingList){
                 write.write(s.getName()+","+s.getId()+","+s.getMajor());
                 write.newLine();
             }
         }catch(IOException e ){
             System.out.println(e.getMessage());
         }
     }

     public void readWatingStudent(){
         File file = new File("Waiting_" + code + ".txt");
        if (!file.exists()) {
        return; 
         }
         try(BufferedReader read = new BufferedReader(new FileReader("Waiting_" + code + ".txt"))){
             String line ;
             while((line=read.readLine())!=null){
                 String parts[] = line.split(",");
                 if(parts.length>2){
                 String name = parts[0];
                 String id = parts[1];
                 String major = parts[2];
                 waitingList.add(new Student(name, id, Major.valueOf(major)));
                 }
             }
         }catch(FileNotFoundException e ){
             System.out.println(e.getMessage());
         }catch(IOException e ){
             System.out.println(e.getMessage());
         }
     }
     
     public List<Student> getStudent(){
         return students;
     }
}
