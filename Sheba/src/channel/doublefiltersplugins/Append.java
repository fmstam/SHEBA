/*
 * Copyright (C) 2013 faroqal-tam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package channel.doublefiltersplugins;

import channel.ChannelFilterPlugin;
import channel.DoubleChannel;

/**
 * Append a set of channels.
 *
 * @author faroqal-tam
 */
public class Append implements ChannelFilterPlugin<DoubleChannel, Object> {

    public static enum AppendingType {

        Horizontal, Vertical
    };
    AppendingType type;

    public Append(AppendingType type) {
        this.type = type;
    }

    @Override
    public DoubleChannel filter(DoubleChannel inChannel, Object... params) {

        int numRows = inChannel.numRows();
        int numCols = inChannel.numCols();
        DoubleChannel[] channels = (DoubleChannel[]) params;
        int maxRow = numRows;
        int maxCol = numCols;

        // check the appending type to define the dimensions
        if (type == AppendingType.Horizontal) { // extend the width
            for (DoubleChannel channel : channels) {
                maxCol += channel.numCols();
                if (maxRow < channel.numRows()) {
                    maxRow = channel.numRows();
                }
            }
        } else { // extended the height
            for (DoubleChannel channel : channels) {
                maxRow += channel.numRows();
                if (maxCol < channel.numCols()) {
                    maxCol = channel.numCols();
                }
            }
        }

        // create one big Channel
        DoubleChannel result = new DoubleChannel(maxRow, maxCol);

        // insert the first channel in the result channel
        result.insertIntoThis(0, 0, inChannel);

        //check out how to insert the rest of the channels to the result one
        int lastRow = numRows;
        int lastCol = numCols;
        if (type == AppendingType.Horizontal) {
            for (DoubleChannel channel : channels) {
                result.insertIntoThis(0, lastCol, channel);
                lastCol += channel.numCols();
            }

        } else {
            if (type == AppendingType.Vertical) {
                for (DoubleChannel channel : channels) {
                    result.insertIntoThis(lastRow, 0, channel);
                    lastRow += channel.numRows();
                }
            }
        }

        return result;
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
