package com.github.hobbit9797;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class Spiel {

	public HashMap<Player, String> playerTeam = new HashMap<Player, String>();
	public HobbitSnowFight hsf;
	ItemStack itemstack = new ItemStack(Material.IRON_SPADE, 1);

	public Spiel(HobbitSnowFight hsf, EntityListener el) {
		this.hsf = hsf;

	}

	public void join(Player player2) {
		Random rnd = new Random();
		if (playerTeam.containsKey(player2)) {
			hsf.messagePlayer("Du bist bereits in einem Team!", player2);
		} else {
			player2.getInventory().clear();
			if (rnd.nextBoolean()) {
				playerTeam.put(player2, "Blau");
				player2.getInventory().setHelmet(
						new ItemStack(Material.WOOL, 1, Short.parseShort("0"),
								(byte) Short.parseShort("11")));
				player2.teleport(new Location(Bukkit.getWorld(hsf.getConfig()
						.getString("world")), hsf.getConfig().getDouble(
						"blue.x"), hsf.getConfig().getDouble("blue.y") + 1, hsf
						.getConfig().getDouble("blue.z"), (float) hsf
						.getConfig().getDouble("blue.yaw"), (float) hsf
						.getConfig().getDouble("blue.pitch")));
			} else {
				playerTeam.put(player2, "Rot");
				player2.getInventory().setHelmet(
						new ItemStack(Material.WOOL, 1, Short.parseShort("0"),
								(byte) Short.parseShort("14")));
				player2.teleport(new Location(Bukkit.getWorld(hsf.getConfig()
						.getString("world")), hsf.getConfig()
						.getDouble("red.x"),
						hsf.getConfig().getDouble("red.y") + 1, hsf.getConfig()
								.getDouble("red.z"), (float) hsf.getConfig()
								.getDouble("red.yaw"), (float) hsf.getConfig()
								.getDouble("red.pitch")));
			}
			hsf.messagePlayer(
					"Du bist jetzt im Team " + playerTeam.get(player2), player2);
			player2.setGameMode(GameMode.SURVIVAL);
			player2.getInventory().addItem(itemstack);
			hsf.messageTeams(player2.getName() + " hat das Team " + playerTeam.get(player2) + " betreten!");
			if(!hsf.getConfig().isSet("kills."+player2.getName())&&!hsf.getConfig().isSet("deaths."+player2.getName())){
				hsf.getConfig().set("kills."+player2.getName(), 0);
				hsf.getConfig().set("deaths."+player2.getName(), 0);
			}
			hsf.saveConfig();
			hsf.reloadConfig();
		}
	}

	public void leave(Player player2) {
		if (playerTeam.containsKey(player2)) {
			playerTeam.remove(player2);
			hsf.messagePlayer("Du hast dein Team verlassen.", player2);
			player2.getInventory().setHelmet(null);
			player2.getInventory().clear();
			player2.teleport(player2.getWorld().getSpawnLocation());
		} else {

			hsf.messagePlayer("Du bist in keinem Team!", player2);
		}
	}
}
