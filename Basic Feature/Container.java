/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JOJOLands;

/**
 *
 * @author Darwi
 */
import java.io.Serializable;public class Container implements Serializable {

    private final int SIZE = 5;  
    private Object[] item;
    public int length;
    
    public Container(){
        item = new Object[SIZE];
        length = 0;    }

    public void add(Object a){
        if(item[item.length-1]!=null){
            grow();
        } else if (item[(item.length/2)]==null&&item.length>5) {
            shrink();
        }
        for(int i=0;i< item.length;i++){
            if(item[i]==null){
                item[i] = a;
                length++;  
                return; 
            }
        }
    }

    public void grow(){
        Object[] temp = item;
        item = new Object[temp.length*2]; 
        for(int i=0;i< temp.length;i++){
            item[i] = temp[i]; 
        }
    }

    public void shrink(){
        Object[] temp = item;  
        item = new Object[temp.length/2]; 
        for(int i=0;i< temp.length;i++){
            item[i] = temp[i];
        }
    }

    public Object get(int index){
        return item[index];    }

}