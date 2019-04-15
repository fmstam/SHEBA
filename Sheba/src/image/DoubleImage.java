/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import channel.ChannelModel;
import channel.DoubleChannel;
import channel.IntegerChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Image of double data type class.
 *
 * @author faroqal-tam
 */
public class DoubleImage implements ImageModel<DoubleChannel,Double>{

    private DoubleChannel[] channels;

    @Override
    public int numberOfChannels() throws ImageException {
        if (channels == null) {
            throw new ImageException("NO_CHANNELS");

        }

        return channels.length;
    }

    /**
     * Return the channel of a specified index
     *
     * @param index the channel index
     * @return
     * @throws ImageException
     */
    @Override
    public DoubleChannel getChannel(int index) throws ImageException {
        int numChannels = numberOfChannels();
        if (numChannels < index || index < 0) {
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        return channels[index];
    }

    public DoubleImage(int numChannels, int height, int width) {
        channels = new DoubleChannel[numChannels];
        for (DoubleChannel channel : channels) {
            channel = new DoubleChannel('\0', height, width);
        }
    }

    /**
     * Construct an image from another one, everything is copied to create a
     * totally fresh object.
     *
     * @param doubleImage
     */
    public DoubleImage(DoubleImage doubleImage) {
        try {
            int numChannels = doubleImage.numberOfChannels();
            this.channels = new DoubleChannel[numChannels];
            // copy the channels
            for (int i = 0; i < numChannels; i++) {
                this.channels[i] = new DoubleChannel(doubleImage.getChannel(i));
            }

        } catch (ImageException ex) {
            Logger.getLogger(DoubleImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public DoubleImage(ImageModel doubleImage) {
        try {
            int numChannels = doubleImage.numberOfChannels();
            this.channels = new DoubleChannel[numChannels];
            // copy the channels
            for (int i = 0; i < numChannels; i++) {
                this.channels[i] = new DoubleChannel((ChannelModel)doubleImage.getChannel(i));
            }

        } catch (ImageException ex) {
            Logger.getLogger(DoubleImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create an image given any number of channels
     *
     * @param channels any number of channels
     */
    public DoubleImage(DoubleChannel... channels) {
        int numChannels = channels.length;
        this.channels = new DoubleChannel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            this.channels[i] = channels[i];
        }

    }
    
    /**
     * Constructor from channel models.
     * @param channels 
     */
    public DoubleImage(ChannelModel... channels) {
        int numChannels = channels.length;
        this.channels = new DoubleChannel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            this.channels[i] = (DoubleChannel)channels[i];
        }

    }
    
    
    /**
     * Constructor from channel models.
     * @param channels 
     */
    public void replaceChannels(DoubleChannel... channels) throws ImageException {
        int numChannels = this.channels.length;
        if (channels.length!=numChannels)
            throw new ImageException("CHANNEL_SIZE_ERROR");
        
        for (int i = 0; i < numChannels; i++) {
            this.channels[i].set(channels[i].copy());
        }

    }
    

    /**
     * Get the value of the image of the channel specified by the index at the
     * row (r) and column (c)
     *
     * @param index the index of the channel
     * @param r the row of the channel
     * @param c the column of the channel
     * @param border if =-1 then return the nearest border when r and/or c
     * overpass the channel bounds, otherwise return this value.
     * @see #get(int, int, double)
     * @throws ImageException
     */
    @Override
    public Double get(int index, int r, int c, Double border) throws ImageException {
        int numChannels = numberOfChannels();
        if (numChannels < index || index < 0) {
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        return this.channels[index].get(r, c, border);
    }

    /**
     * Get the value specified by the channel index, row, and column.
     *
     * @param index the channel index
     * @param r the row number
     * @param c the column number
     * @see #get(int, int, double)
     * @see #get(int, int, int, double)
     * @throws ImageException
     */
    
    @Override
    public Double get(int index, int r, int c) throws ImageException {
        int numChannels = numberOfChannels();
        if (numChannels < index || index < 0) {
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        return this.channels[index].get(r, c);
    }

    /**
     * Get the value of the image of the channel specified by the index (0) at
     * the row (r) and column (c)
     *
     * @param r the row of the channel
     * @param c the column of the channel
     * @param border if =-1 then return the nearest border when r and/or c
     * overpass the channel bounds, otherwise return this value.
     * @see #get(int, int, int, int)
     * @throws ImageException
     */
    @Override
    public Double get(int r, int c, Double border) throws ImageException {

        return this.get(0, r, c, border); //  assume only a single-channled image;
    }

    /**
     * Get the value of the single channeled image.
     *
     * @param r the row number
     * @param c the column number
     * @see #get(int, int, double)
     * @throws ImageException
     */
    @Override
    public Double get(int r, int c) throws ImageException {
        if (channels == null) {
            throw new ImageException("NO_CHANNELS");
        }
        return this.channels[0].get(r, c); //  assume only a single-channled image;
    }

    /**
     * Set the value at row r and column c of the channel indexed by index.
     *
     * @param index the channel index
     * @param r
     * @param c
     * @param value
     * @see #set(int, int, double)
     * @throws ImageException
     */
    @Override
    public void set(int index, int r, int c, Double value) throws ImageException {
        int numChannels = numberOfChannels();
        if (numChannels < index || index < 0) {
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }

        this.channels[index].set(r, c, value);
    }

    /**
     * Set the value of "this" single-channeled image at position (r,c)
     *
     * @param r
     * @param c
     * @param value
     * @throws ImageException
     * @see #set(int, int, int, double)
     */
    @Override
    public void set(int r, int c, Double value) throws ImageException {
        set(0, r, c, value);
    }
    
    @Override
    public int numRows(){
        return channels[0].numRows();
    }
    
    @Override
    public int numCols(){
        return channels[0].numCols();
    }

    @Override
    public DoubleChannel[] getChannels() {
        return channels;
    }

    @Override
    public void setChannels(DoubleChannel[] channels) {
        this.channels = channels;
    }

    @Override
    public void setChannel(int index, DoubleChannel channel) throws ImageException{
        if(index<0 || index>numberOfChannels()){
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        channels[index]=channel;
    }
    
    
    
    public IntegerImage toIntegerImage() throws ImageException {
        IntegerChannel[] channels = new IntegerChannel[numberOfChannels()];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = this.channels[i].toIntegerChannel();
        }
        IntegerImage si = new IntegerImage(channels);
        return si;
    }
    
}
