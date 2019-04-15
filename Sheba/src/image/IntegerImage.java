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
 * A integer image class.
 *
 * @author faroqal-tam
 */
public class IntegerImage implements ImageModel<IntegerChannel, Integer> {

    IntegerChannel[] channels;

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
    public IntegerChannel getChannel(int index) throws ImageException {
        int numChannels = numberOfChannels();
        if (numChannels < index || index < 0) {
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        return channels[index];
    }

    public IntegerImage(int numChannels, int rows, int cols) {
        channels = new IntegerChannel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            channels[i] = new IntegerChannel('\0', rows, cols);
        }
       
    }

    /**
     * Construct an image from another one, everything is copied to create a
     * totally fresh object.
     *
     * @param intImage
     */
    public IntegerImage(IntegerImage intImage) {
        try {
            int numChannels = intImage.numberOfChannels();
            this.channels = new IntegerChannel[numChannels];
            // copy the channels
            for (int i = 0; i < numChannels; i++) {
                this.channels[i] = new IntegerChannel(intImage.getChannel(i));
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
    public IntegerImage(IntegerChannel... channels) {
        int numChannels = channels.length;
        this.channels = new IntegerChannel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            this.channels[i] = channels[i];
        }

    }
    
    
     public IntegerImage(ChannelModel... channels) {
        int numChannels = channels.length;
        this.channels = new IntegerChannel[numChannels];
        for (int i = 0; i < numChannels; i++) {
            this.channels[i] = (IntegerChannel)channels[i];
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
     * @see #get(int, int, int)
     * @throws ImageException
     */
    @Override
    public Integer get(int index, int r, int c, Integer border) throws ImageException {
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
     * @see #get(int, int, int)
     * @see #get(int, int, int, int)
     * @throws ImageException
     */
    @Override
    public Integer get(int index, int r, int c) throws ImageException {
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
    public Integer get(int r, int c, Integer border) throws ImageException {

        return this.get(0, r, c, border); //  assume only a single-channled image;
    }

    /**
     * Get the value of the single channeled image.
     *
     * @param r the row number
     * @param c the column number
     * @see #get(int, int, int)
     * @throws ImageException
     */
    @Override
    public Integer get(int r, int c) throws ImageException {
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
     * @see #set(int, int, int)
     * @throws ImageException
     */
    @Override
    public void set(int index, int r, int c, Integer value) throws ImageException {
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
     * @see #set(int, int, int, int)
     */
    @Override
    public void set(int r, int c, Integer value) throws ImageException {
        set(0, r, c, value);
    }

    @Override
    public int numRows() {
        return channels[0].numRows();
    }

    @Override
    public int numCols() {
        return channels[0].numCols();
    }

    /**
     * Convert a int image to double image;
     *
     * @return
     * @throws ImageException
     */
    public DoubleImage toDoubleImage() throws ImageException {
        DoubleChannel[] channels = new DoubleChannel[numberOfChannels()];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = this.channels[i].toDoubleChannel();
        }
        DoubleImage di = new DoubleImage(channels);
        return di;
    }

    @Override
    public IntegerChannel[] getChannels() {
        return this.channels;
    }

    @Override
    public void setChannels(IntegerChannel[] channels) {
        this.channels = channels;
    }
    
    @Override
    public void setChannel(int index, IntegerChannel channel) throws ImageException{
        if(index<0 || index>numberOfChannels()){
            throw new ImageException("NO_SUCH_CHANNEL", index);
        }
        channels[index]=channel;
    }
}
