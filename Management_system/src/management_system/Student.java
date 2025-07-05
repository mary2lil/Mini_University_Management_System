/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management_system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

 enum Major {
   COMPUTER_SCIENCE,
    ENGINEERING,
    MEDICINE,
    BUSINESS 
}

public class Student {
    
    private String name;
    private String id;
    private Major major;
    private List<Course>courses= new ArrayList<>();

    
    //Constructors
    public Student(String name, String id, Major major) {
        this.name = name;
        this.id = id;
        this.major = major ;
    }
    
    
    public Student(String id) {
        this.id = id;
    }
    
    
        //Getters&Settters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMajor(Major major){
        this.major=major;
    }
    
    public Major getMajor(){
        return major;
    }
    
    //private methods
     private boolean addCourse(Course course){
        if(getTotalHours()+course.getHours()>18){
            return false;
        }
       for(Course c :courses){
           if(c.getCode().equals(course.getCode())){
               return false;
           }
       }
        courses.add(course);
        return true;
    }
     
     private boolean removeCourse(Course course){
        if(courses == null || courses.isEmpty()){
            return false;
        }
         Iterator<Course> iterator = courses.iterator();
         while(iterator.hasNext()){
             Course courseRemoved = iterator.next();
             if(courseRemoved.getCode().equals(course.getCode())){
                 iterator.remove();
                 return true;
             }
         }
         return false;
    }
     
    public int getTotalHours(){
    int sum=0;
        for(Course course:courses){
            sum+=course.getHours();
        }
        return sum;
    }
    
    public boolean enrollInCourse(Course course){
       return addCourse(course);
    }
    
     public boolean dropCourse(Course course){
       return removeCourse(course);
    }
    
    public void displayInfoStudent(){
        System.out.println("Name : "+getName()+" ID: "+getId()+" Major : "+getMajor());
    }
    
    public void displayCourses(){
        if(courses.isEmpty()){
            System.out.println("Student "+getName()+" has no courses to display");
            return;
        }
        int count=1;
        for(Course course : courses){
            System.out.println(count+".Code : "+course.getCode()+" | Name : "+course.getName());
            count++;
        }
        System.out.println("Total registered hours: " + getTotalHours());
    }
   
    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", id=" + id + ", major=" + major + '}';
    }
}