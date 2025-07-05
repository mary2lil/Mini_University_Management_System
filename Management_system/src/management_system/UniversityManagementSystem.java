/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author HP
 */
public class UniversityManagementSystem {

    private List<Student>students=new LinkedList<>();
    private List<Course>courses=new LinkedList<>();
    private Stack<Operation> operationHistory = new Stack<>();
    private Stack<Operation> redoStack  = new Stack<>();
    
    private Student findStudentById(String id){
        for(Student s : students){
            if(id.equals(s.getId())){
                return s;
            }
        }
        return null;
    }
    
    private Course findCourseByCode(String code){
        for(Course c : courses){
            if(c.getCode().equals(code)){
                return c ;
            }
        }
        return null;
    }
    
    public boolean addStudent(Student student){
        if(findStudentById(student.getId())!=null){
            return false;
        }
         students.add(student);
        operationHistory.push(new Operation(student, null, OperationType.ADD_STUDENT));
        redoStack.clear();
        return true;
    }
    
    public boolean removeStudent(Student student){
        if(students.isEmpty()){
            return false;
        }
        Iterator<Student>iterator = students.iterator();
        while(iterator.hasNext()){
            Student s = iterator.next();
            if(s.getId().equals(student.getId())){
                iterator.remove();
                operationHistory.push(new Operation(student, null, OperationType.REMOVE_STUDENT));
                redoStack.clear();
                return true;
            }
        }
        return false;
    }
    
     public boolean addCourse(Course course){
        if(findCourseByCode(course.getCode())!=null){
            return false;
        }
        courses.add(course);
        operationHistory.push(new Operation(null, course, OperationType.ADD_COURSE));
        redoStack.clear();
        return true;
    }
    
    public boolean removeCourse(Course course){
        if(courses.isEmpty()){
            return false;
        }
        Iterator<Course>iterator = courses.iterator();
        while(iterator.hasNext()){
            Course c = iterator.next();
            if(c.getCode().equals(course.getCode())){
                iterator.remove();
                operationHistory.push(new Operation(null, course, OperationType.REMOVE_COURSE));
                redoStack.clear();
                return true;
            }
        }
        return false;
    }
    
    public boolean enrollStudentInCourse(Student student , Course course){
        boolean courseSuccess = course.enrollStudent(student);
        
        if(courseSuccess){
           boolean studentSuccess = student.enrollInCourse(course);
        if(studentSuccess){
            System.out.println("Student enrolled successfully.");
            operationHistory.push(new Operation(student, course, OperationType.ADD_ENROLLMENT));
            redoStack.clear();
            return true;
        }
        else{
            course.removeStudentFromCourse(student);
            System.out.println("Enrollment failed: Student has exceeded credit hours or is already enrolled.");
        }
        }
        else{
             System.out.println("Enrollment failed: Course is full or student already enrolled.");
        }
        return false;
    }
    
    public boolean removeStudentFromCourse(Student student , Course course){
        boolean courseSuccess = course.removeStudentFromCourse(student);
        
        if(courseSuccess){
            boolean studentSuccess = student.dropCourse(course);
            
            if(studentSuccess){
              System.out.println(" Student :" + student.getName() + " dropped from course : " + course.getName() + " successfully.");
              operationHistory.push(new Operation(student, course, OperationType.REMOVE_ENROLLMENT));
              redoStack.clear();
            return true;
            }
            else{
                course.enrollStudent(student);
                System.out.println("️ Drop failed on student side. Rolling back enrollment.");
            }
        }
        else{
            System.out.println(" Drop failed: Either course is empty or student not found.");
        }
        return false;
    }
    
    public void displayAllStudents(){
        if(students.isEmpty()){
            System.out.println("There are no students");
            return;
        }
        int count=1;
        for(Student student : students){
            System.out.print(count+". ");
            student.displayInfoStudent();
            count++;
        }
    }
    
     public void displayAllCourses(){
        if(courses.isEmpty()){
            System.out.println("There are no courses");
            return;
        }
         int count=1;
        for(Course course : courses){
             System.out.print(count+". ");
            System.out.println(course.toString());
            count++;
        }
    }
     
     public void displayCourseStudents(Course course){
         course.displayAllStudent();
     }
     
     public void displayStudentCourses(Student student){
         student.displayCourses();
     }
     
     public void undoLastOperation(){
         if(operationHistory.isEmpty()){
             System.out.println("No operations to undo.");
             return;
         }
         
         Operation last = operationHistory.pop();
         
         switch(last.getType()){
             case ADD_STUDENT :{
                 students.remove(last.getStudent());
                  System.out.println("Undo: Removed student " + last.getStudent().getName());
                  redoStack.push(last);
                  break;
             }
             case REMOVE_STUDENT : {
                 students.add(last.getStudent());
                  System.out.println("Undo: Add student " + last.getStudent().getName());
                  redoStack.push(last);
                  break;
             }
             case ADD_COURSE:{
                 courses.remove(last.getCourse());
                 System.out.println("Undo: remove course " + last.getCourse().getCode());
                 redoStack.push(last);
                  break;
             }
             
             case REMOVE_COURSE :{
                 courses.add(last.getCourse());
                 System.out.println("Undo: Add course " + last.getCourse().getCode());
                 redoStack.push(last);
                 break;
             }
             case ADD_ENROLLMENT :{
                 last.getCourse().removeStudentFromCourse(last.getStudent());
                 last.getStudent().dropCourse(last.getCourse());
                  System.out.println("Undo: Dropped student " + last.getStudent().getName() + " from course " + last.getCourse().getName());
                  redoStack.push(last);
                  break;
             }
             
             case REMOVE_ENROLLMENT :{
                 last.getCourse().enrollStudent(last.getStudent());
                 last.getStudent().enrollInCourse(last.getCourse());
                 System.out.println("Undo: enrolled student " + last.getStudent().getName() + " in course " + last.getCourse().getName());
                 redoStack.push(last);
                 break;
             }
         }
     }
     
     public boolean redoLastOperation(){
         if(redoStack.isEmpty()){
              System.out.println("No operation to redo.");
              return false;
         }
         
         Operation last = redoStack.pop();
         boolean success  = false;
         
         switch(last.getType()){
             case ADD_STUDENT :{
                 success=addStudent(last.getStudent());
                 break;
             }
             
             case REMOVE_STUDENT :{
                 success=removeStudent(last.getStudent());
                 break;
             }
             
             case ADD_COURSE :{
                 success=addCourse(last.getCourse());
                 break;
             }
             
             case REMOVE_COURSE :{
                 success=removeCourse(last.getCourse());
                 break;
             }
             
             case ADD_ENROLLMENT :{
                 success=enrollStudentInCourse(last.getStudent(), last.getCourse());
                 break;
             }
             
             case REMOVE_ENROLLMENT :{
                 success=removeStudentFromCourse(last.getStudent(), last.getCourse());
                 break;
             }
         }
         if(success){
             operationHistory.push(last);
             System.out.println("Redo operation done: " + last.getType());
         }
         else{
              System.out.println("Redo failed.");
         }
         return success;
     }
     
     public Student getStudentById (String id){
         for(Student student:students){
             if(student.getId().equals(id)){
                 return student;
             }
         }
         return null;
     }
     
     public Course getCourseByCode(String code){
         for(Course course : courses){
             if(course.getCode().equals(code)){
                 return course;
             }
         }
         return null;
     }
     
     public void writeStudent(){
         if(students.isEmpty()){
             return;
         }
         try(BufferedWriter save = new BufferedWriter(new FileWriter ("FileStudent.txt") );){
         for(Student s : students){
             save.write(s.getName()+","+s.getId()+","+s.getMajor());
             save.newLine();
         }
     }catch(IOException e){
             System.out.println(e.getMessage());
     }
     }
     
     public void writeCourse(){
         if(courses.isEmpty()){
             return;
         }
         try(BufferedWriter save = new BufferedWriter(new FileWriter ("FileCourse.txt") );){
         for(Course c : courses){
             save.write(c.getCode()+","+c.getName()+","+c.getHours());
             save.newLine();
         }
     }catch(IOException e){
             System.out.println(e.getMessage());
     }
     }
     
     public void readStudent(){
         try(BufferedReader read = new BufferedReader(new FileReader("FileStudent.txt")) ;){
            String line ;
            while((line=read.readLine())!=null){
                String parts[] = line.split(",");
                if(parts.length>2){
                    String name = parts[0];
                    String id = parts[1];
                    String major = parts[2].toUpperCase();
                   students.add(new Student(name, id, Major.valueOf(major)));
                }
            }
         }catch(FileNotFoundException e){
             System.out.println(e.getMessage());
         }catch(IOException e){
             System.out.println(e.getMessage());
         }
     }
     
     public void readCourse(){
         try(BufferedReader read = new BufferedReader(new FileReader("FileCourse.txt")) ;){
             String line;
             while((line=read.readLine())!=null){
                 String parts[] = line.split(",");
                 if(parts.length>2){
                     String code = parts[0];
                     String name = parts[1];
                     int hourse = Integer.parseInt(parts[2]);
                     courses.add(new Course(code, name, hourse));
                 }
             }
         }catch(FileNotFoundException e){
             System.out.println(e.getMessage());
         }catch(IOException e){
             System.out.println(e.getMessage());
         }
     }
     
    public void writeWatinglist(){
        for(Course c : courses){
            c.writeWatingStudent();
        }
    }
    
    public void readWatinglist(){
        for(Course c : courses){
            c.readWatingStudent();
        }
    }
    
    public void writeEnrollments(){
        try(BufferedWriter write = new BufferedWriter(new FileWriter("Enrollments.txt"))){
            for(Student student : students){
            for(Course course : courses){
                if(course.getStudent().contains(student)){
                    write.write(student.getId()+","+course.getCode());
                }
            }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void readEnrollments(){
        try(BufferedReader read = new BufferedReader(new FileReader("Enrollments.txt"))){
            String line;
            while((line=read.readLine())!=null){
                String parts [] = line.split(",");
                if(parts.length>1){
               String studentId = parts[0];
               String courseCode = parts[1];
               
               Student student = findStudentById(studentId);
               Course course = findCourseByCode(courseCode);
               
               if(student !=null&&course!=null){
                   course.enrollStudent(student);
                   student.enrollInCourse(course);
               }
                }
            }
        }catch(FileNotFoundException e){
             System.out.println(e.getMessage());
         }catch(IOException e){
             System.out.println(e.getMessage());
         }
    }
}
