package com.github.hobbit9797;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class Spiel {

	public HashMap<Player, String> playerTeam = new HashMap<Player, String>();
	public HobbitSnowFight hsf;
	
	public Spiel(HobbitSnowFight hsf, EntityListener el){
		this.hsf = hsf;
		
	}

	public void join(Player player2) {
		Random rnd = new Random();
		if (playerTeam.containsKey(player2)) {
			hsf.messagePlayer("Du bist bereits in einem Team!", player2);
		} else {
			if (rnd.nextBoolean()){
				playerTeam.put(player2, "Blau");
			player2.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, Short.parseShort("0"), (byte)Short.parseShort("11")));
			}else {
				playerTeam.put(player2, "Rot");
				player2.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, Short.parseShort("0"), (byte)Short.parseShort("14")));
			}
			hsf.messagePlayer(
					"Du bist jetzt im Team " + playerTeam.get(player2), player2);
		}
	}

	public void leave(Player player2) {
		if (playerTeam.containsKey(player2)) {
			playerTeam.remove(player2);
			hsf.messagePlayer("Du hast dein Team verlassen.", player2);
			player2.getInventory().setHelmet(null);
		} else {
			
			hsf.messagePlayer("Du bist in keinem Team!", player2);
		}
	}
}
