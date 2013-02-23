package com.github.hobbit9797;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
public class EntityListener implements Listener{
	
	public Spiel spiel;
	
	@EventHandler
    public void snowballHit(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		Snowball snowball= (Snowball) entity;
        if (event.getEntity() instanceof Player &&entity instanceof Snowball&&spiel.playerTeam.containsKey((Player)event.getEntity())) {
        	if(!spiel.playerTeam.get(event.getEntity()).equals(spiel.playerTeam.get(snowball.getShooter()))){
            event.setDamage(4);
        	}
           
        }
    }
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event){
		
		
	}

}
