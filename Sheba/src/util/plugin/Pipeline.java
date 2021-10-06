/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.plugin;

import java.util.LinkedList;

/**
 * Wrapper for a set of commands to be used by a given filter
 * 
 *
 * @author faroqal-tam
 */
public class Pipeline {

    LinkedList<Task> chain = new LinkedList<Task>();

    public void addBullet(Task task) {
        chain.addLast(task);
    }

    /**
     * Add a command to the pipeline.
     *
     * @param plugin
     * @param payload
     */
    public void addBullet(Plugin plugin, Object... payload) {
        chain.add(new Task(plugin, payload));
    }

    public Pipeline(){
        
    }
    public Pipeline(Pipeline pipeLine){
      for(Task b: pipeLine.chain){
          chain.add(new Task(b));
      } 
    }
    /**
     * Dequeue the next command
     * @return 
     */
    public Task getNextBullet() {
        if (chain.isEmpty()) {
            return null;
        }
        return chain.removeFirst();
    }
}
