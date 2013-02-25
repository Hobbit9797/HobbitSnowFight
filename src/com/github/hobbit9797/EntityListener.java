package com.github.hobbit9797;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
public class EntityListener implements Listener{
	
	  private Spiel spiel;
	  private HobbitSnowFight hsf;
	
	  public EntityListener(HobbitSnowFight hsf, Spiel spiel) {
		    this.hsf = hsf;
		    this.spiel = spiel;
		  }
	
	@EventHandler
    public void snowballHit(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
        if (event.getEntity() instanceof Player &&entity instanceof Snowball&&spiel.playerTeam.containsKey((Player)event.getEntity())) {
        	Snowball snowball = (Snowball) entity;
        	if(!spiel.playerTeam.get(event.getEntity()).equals(spiel.playerTeam.get(snowball.getShooter()))){
            event.setDamage(4);
        	}
           
        }
    }
	
	@EventHandler
	public void playerDeath(PlayerRespawnEvent event){
		if(spiel.playerTeam.containsKey(event.getPlayer())){
			
			spiel.playerTeam.remove(event.getPlayer());
			hsf.messagePlayer("Du hast verloren. Um wieder beizutreten benutze /hsf j.", event.getPlayer());
		}
		
	}
	
    @EventHandler
    public void onInventoryOpen(InventoryClickEvent event)
    {
    	if(spiel.playerTeam.containsKey(event.getWhoClicked())){
        event.setCancelled(true);
    	}
    }

}
