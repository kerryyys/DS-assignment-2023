/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JOJOLands;

/**
 *
 * @author Darwi
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
public class SaveLoad {

    public Container content;
    public Container outputContent;
    
    public SaveLoad(){
        content = new Container();
        outputContent = new Container();
    }

    public void addContentToSave(Object a){
        content.add(a); 
    }

    public void save(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Where to save:");
        String filePath = scanner.nextLine();
        try {
            FileOutputStream file = new FileOutputStream(filePath + "content.bin"); 
            ObjectOutputStream saving = new ObjectOutputStream(file);
            saving.writeObject(content);
            file.close();
            saving.close();        }
        catch (Exception e){

        }
    }

    public Container load(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Where to load:");
        String filePath = scanner.nextLine();
        try {
            FileInputStream file = new FileInputStream(filePath + "content.bin");
            ObjectInputStream loading = new ObjectInputStream(file);
            outputContent = (Container) loading.readObject(); 
            file.close();
            loading.close();
            return outputContent;  
        } catch (Exception e){
            e.printStackTrace();        }
        return null;
    }
}