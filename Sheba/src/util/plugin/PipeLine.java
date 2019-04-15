/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.plugin;

import java.util.LinkedList;

/**
 * Warper for a set of commands to be used by a given filter, calculation
 * function.
 *
 * @author faroqal-tam
 */
public class PipeLine {

    LinkedList<Bullet> chain = new LinkedList<Bullet>();

    public void addBullet(Bullet bullet) {
        chain.addLast(bullet);
    }

    /**
     * Add a command to the pipeline.
     *
     * @param plugin
     * @param payload
     */
    public void addBullet(Plugin plugin, Object... payload) {
        // it has to be fixed such that it match the plug in with the input type and does the possible correction
        chain.add(new Bullet(plugin, payload));
    }

    public PipeLine(){
        
    }
    public PipeLine(PipeLine pipeLine){
      for(Bullet b: pipeLine.chain){
          chain.add(new Bullet(b));
      } 
    }
    /**
     * Dequeue the next command
     * @return 
     */
    public Bullet getNextBullet() {
        if (chain.isEmpty()) {
            return null;
        }
        return chain.removeFirst();
    }
}
