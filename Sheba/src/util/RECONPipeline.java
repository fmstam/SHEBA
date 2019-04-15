/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.LinkedList;

/**
 * A pipeline class for processing a set of images, channels, etc with a series of commands
 * @author faroqal-tam
 */
public abstract class RECONPipeline {
    Object srouceData;
    LinkedList<Object> commands= new LinkedList<Object>();

    public RECONPipeline(Object srouceData) {
        this.srouceData = srouceData;
    }

    public Object getSrouceData() {
        return srouceData;
    }

    public void setSrouceData(Object srouceData) {
        this.srouceData = srouceData;
    }

    public LinkedList<Object> getCommands() {
        return commands;
    }

    public void setCommands(LinkedList<Object> commands) {
        this.commands = commands;
    }
    
    public void addCommand(Object command){
        this.commands.addLast(command);
    }
    
    public Object run(){
        Object result=srouceData;
        while(!commands.isEmpty()){ // dequeue commands and apply them one by one.
            
          
        }
        return result;
    }
    
    
   // we will need a function to check the data type for the input/out mismatch detection
    
    
}
