/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.plugin;

import channel.ChannelFilter;
import channel.ChannelFilterPlugin;
import image.filter.ImageFilter;

/**
 * Contains a plugin and a list of parameters to run the plugin.
 * @see Plugin
 * @see PluginChain
 * @see ChannelFilterPlugin
 * @see ImageFilter
 * @see ChannelFilter
 * @author faroqal-tam
 */
public class Bullet {
    Plugin plugin;
    Object payload;

    public Bullet(Bullet bullet){
        this.plugin=bullet.getPlugin();
        payload= bullet.getPayload();
    }
    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Bullet(Plugin command, Object payload) {
        this.plugin = command;
        this.payload = payload;
    }
    
    
}
