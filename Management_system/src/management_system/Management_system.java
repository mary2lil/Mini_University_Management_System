/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package management_system;

/**
 *
 * @author HP
 */
public class Management_system {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        UniversityManagementSystem university = new UniversityManagementSystem();
        ConsoleUI consoleUI = new ConsoleUI(university);
        
        
        
        consoleUI.start();
        
        
        
    }
    
}
