/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

/**
 *
 * @author faroqal-tam
 */
public interface ImageModel<C,T> {
    
    public int numberOfChannels() throws ImageException;
    public C getChannel(int index) throws ImageException;
    public  T get(int index, int r, int c, T border) throws ImageException;
    public T get(int index, int r, int c) throws ImageException;
    public T get(int r, int c, T border) throws ImageException;
    public T get(int r, int c) throws ImageException;
    public void set(int index, int r, int c, T value) throws ImageException;
    public void set(int r, int c, T value) throws ImageException;
    public C []getChannels();
    public void setChannels(C[] channels);
    public void setChannel(int index, C channel) throws ImageException;
    public int numRows();
    public int numCols();
    
    // ENUMS and constants
    
   public static final int OBJECT = 255;
   public static final int BACKGROUND = 0;
   
   
    
    
     
}
