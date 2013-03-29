package com.github.hobbit9797;


import java.util.*;
import java.util.Map.Entry;

import me.terradominik.plugins.terraworld.TerraWorld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Spiel {

	public HashMap<Player, String> playerTeam = new HashMap<Player, String>();
	public HobbitSnowFight hsf;
	public Color red = Color.fromRGB(255, 0, 0);
	public Color blue = Color.fromRGB(0, 0, 255);
	
	
	
	ItemStack itemstack = new ItemStack(Material.IRON_SPADE, 1);

	public Spiel(HobbitSnowFight hsf, EntityListener el) {
		this.hsf = hsf;

	}

	public void join(Player player2) {
		Random rnd = new Random();
		if (playerTeam.containsKey(player2)||TerraWorld.containsSpieler(player2)) {
			hsf.messagePlayer("Du bist bereits in einem Spiel!", player2);
		} else {
			player2.getInventory().clear();
			int countRed = 0;
			int countBlue = 0;
			for (Entry<Player, String> entry : playerTeam.entrySet()) {
				  if (entry.getValue().equals("Blau")) {
				    countBlue++;
				  }
				}
			for (Entry<Player, String> entry : playerTeam.entrySet()) {
				  if (entry.getValue().equals("Rot")) {
				    countRed++;
				  }
				}
			if(countBlue>countRed){
				addRed(player2);
			}
			else if(countBlue<countRed){
				addBlue(player2);
			}
			else if(countBlue==countRed){
							
			if (rnd.nextBoolean()) {
				addBlue(player2);
			} else {
				addRed(player2);
			}
			}
			hsf.messagePlayer(
					"Du bist jetzt im Team " + playerTeam.get(player2), player2);
			player2.setGameMode(GameMode.SURVIVAL);
			player2.setHealth(20);
			player2.setFoodLevel(20);
			player2.getInventory().addItem(itemstack);
			
            TerraWorld.addSpieler(player2);
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
			player2.getInventory().setChestplate(null);
			player2.getInventory().setLeggings(null);
			player2.getInventory().setBoots(null);
			player2.getInventory().clear();
			player2.setHealth(20);
			player2.setFoodLevel(20);
			TerraWorld.removeSpieler(player2);
			player2.teleport(Bukkit.getWorld(hsf.getConfig().getString("spawnworld")).getSpawnLocation());
		} else {

			hsf.messagePlayer("Du bist in keinem Team!", player2);
		}
	}
	
	public void addRed(Player player2){
		playerTeam.put(player2, "Rot");
		ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemStack lbreast = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack lfoot = new ItemStack(Material.LEATHER_BOOTS, 1);
		lfoot.addEnchantment(Enchantment.PROTECTION_FALL, 10);
		LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
		lam.setColor(red);
		lhelmet.setItemMeta(lam);
		lbreast.setItemMeta(lam);
		lleggings.setItemMeta(lam);
		lfoot.setItemMeta(lam);
		player2.getInventory().setHelmet(lhelmet);
		player2.getInventory().setChestplate(lbreast);
		player2.getInventory().setLeggings(lleggings);
		player2.getInventory().setBoots(lfoot);
		player2.teleport(new Location(Bukkit.getWorld(hsf.getConfig()
				.getString("world")), hsf.getConfig()
				.getDouble("red.x"),
				hsf.getConfig().getDouble("red.y") + 1, hsf.getConfig()
						.getDouble("red.z"), (float) hsf.getConfig()
						.getDouble("red.yaw"), (float) hsf.getConfig()
						.getDouble("red.pitch")));
		String tabname = ChatColor.GREEN + "HSF " + ChatColor.RED + player2.getName();
        if (tabname.length() > 16) {
            player2.setPlayerListName(tabname.substring(0, 14) + "..");
        } else {
            player2.setPlayerListName(tabname);
        }
	}
	
	public void addBlue(Player player2){
		playerTeam.put(player2, "Blau");
		ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemStack lbreast = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack lfoot = new ItemStack(Material.LEATHER_BOOTS, 1);
		lfoot.addEnchantment(Enchantment.PROTECTION_FALL, 10);
		LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
		lam.setColor(blue);
		lhelmet.setItemMeta(lam);
		lbreast.setItemMeta(lam);
		lleggings.setItemMeta(lam);
		lfoot.setItemMeta(lam);
		player2.getInventory().setHelmet(lhelmet);
		player2.getInventory().setChestplate(lbreast);
		player2.getInventory().setLeggings(lleggings);
		player2.getInventory().setBoots(lfoot);
		player2.teleport(new Location(Bukkit.getWorld(hsf.getConfig()
				.getString("world")), hsf.getConfig().getDouble(
				"blue.x"), hsf.getConfig().getDouble("blue.y") + 1, hsf
				.getConfig().getDouble("blue.z"), (float) hsf
				.getConfig().getDouble("blue.yaw"), (float) hsf
				.getConfig().getDouble("blue.pitch")));
		String tabname = ChatColor.GREEN + "HSF " + ChatColor.BLUE + player2.getName();
        if (tabname.length() > 16) {
            player2.setPlayerListName(tabname.substring(0, 14) + "..");
        } else {
            player2.setPlayerListName(tabname);
        }
	}
}
