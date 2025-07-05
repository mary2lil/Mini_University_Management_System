# 🎓 University Management System

A simple Java-based console application to manage students, courses, and enrollments in a university system.

---

## 📌 Features

- Add / Remove Students
- Add / Remove Courses
- Enroll students into courses with max capacity (30 students)
- Drop students from courses
- Waiting list support for full courses
- Undo / Redo operations for student/course/enrollment changes
- Save & Load data using text files
- Track total credit hours for each student
- File persistence for:
- Students
- Courses
- Enrollments
- Waiting lists

---

## 🛠 Technologies Used

- Java (OOP + File Handling + Collections)
- Text files for basic data persistence
- Stack for undo/redo functionality
- LinkedList / ArrayList / Queue / Iterator

---

## 📂 Project Structure

📁 UniversityProject
├── Student.java
├── Course.java
├── UniversityManagementSystem.java
├── Operation.java
├── Main.java / UI.java
├── FileStudent.txt
├── FileCourses.txt
├── Enrollments.txt
├── Waiting_<CourseCode>.txt


---

## ▶️ How to Run

1. Compile all `.java` files.
2.Run the main class.
3.Follow the console instructions.

---

💾 File Saving
.The system will save data to files automatically when the program exits:

.FileStudent.txt → Stores all students with name, ID, major, total hours

.FileCourses.txt → Stores all courses with code, name, hours, student count

.Enrollments.txt → Stores the relation between students and their courses

.Waiting_<CourseCode>.txt → Stores waiting list for each course

---

💡 Example Use Case
Register a student: Ahmed, ID: 2023001, Major: COMPUTER_SCIENCE

Add a course: CS101 - Data Structures

Enroll student into the course

If course is full, student is added to the waiting list

---

✅ Improvements
Add search/filter functionality

Use CSV or JSON for better file handling

GUI version using JavaFX or Swing

---

## Note📌
This project is part of my learning journey to master Java and Data Structures.
Feel free to explore, suggest improvements, or fork the repo 💛
