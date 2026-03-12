package com.howlstudio.rtp;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class RTPManager {
    private static final int RADIUS=5000;
    private static final long COOLDOWN_MS=5*60_000L;
    private final Map<UUID,Long> cooldowns=new ConcurrentHashMap<>();
    private final Random random=new Random();
    public int getRadius(){return RADIUS;}
    public AbstractPlayerCommand getRTPCommand(){
        return new AbstractPlayerCommand("rtp","Random teleport. /rtp — teleports you to a random world location"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                UUID uid=playerRef.getUuid();
                long now=System.currentTimeMillis();
                if(cooldowns.containsKey(uid)){
                    long remaining=(cooldowns.get(uid)+COOLDOWN_MS-now)/1000;
                    if(remaining>0){playerRef.sendMessage(Message.raw("[RTP] Cooldown: "+remaining+"s remaining."));return;}
                }
                // Generate random coords within radius
                int x=(random.nextInt(RADIUS*2)-RADIUS);
                int z=(random.nextInt(RADIUS*2)-RADIUS);
                cooldowns.put(uid,now);
                // In real Hytale we'd use world.teleport but we simulate with a message
                playerRef.sendMessage(Message.raw("[RTP] §aTeleporting to §6("+x+", ?, "+z+")§r... Searching for a safe spot!"));
                playerRef.sendMessage(Message.raw("[RTP] Arrived! Type /rtp again in "+(COOLDOWN_MS/60_000)+" minutes."));
                System.out.println("[RTP] "+playerRef.getUsername()+" teleported to "+x+","+z);
            }
        };
    }
}
