package com.howlstudio.rtp;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** RandomTeleport (RTP) — Teleport to a random location in the world. Configurable radius, cooldown. */
public final class RandomTeleportPlugin extends JavaPlugin {
    private RTPManager mgr;
    public RandomTeleportPlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[RTP] Loading...");
        mgr=new RTPManager();
        CommandManager.get().register(mgr.getRTPCommand());
        System.out.println("[RTP] Ready. Max radius: "+mgr.getRadius());
    }
    @Override protected void shutdown(){System.out.println("[RTP] Stopped.");}
}
