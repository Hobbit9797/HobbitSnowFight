package com.github.hobbit9797;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {

	private Spiel spiel;
	private HobbitSnowFight hsf;

	public EntityListener(HobbitSnowFight hsf, Spiel spiel) {
		this.hsf = hsf;
		this.spiel = spiel;
	}

	@EventHandler
	public void snowballHit(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player && entity instanceof Snowball
				&& spiel.playerTeam.containsKey((Player) event.getEntity())) {
			Snowball snowball = (Snowball) entity;
			if (!spiel.playerTeam.get(event.getEntity()).equals(
					spiel.playerTeam.get(snowball.getShooter()))) {
				event.setDamage(4);
			}

		}
	}

	@EventHandler
	public void playerDeath(PlayerRespawnEvent event) {
		if (spiel.playerTeam.containsKey(event.getPlayer())) {
			if (spiel.playerTeam.get(event.getPlayer()).equalsIgnoreCase("Rot")) {
				event.setRespawnLocation(new Location(Bukkit.getWorld(hsf
						.getConfig().getString("world")), hsf.getConfig()
						.getDouble("red.x"),
						hsf.getConfig().getDouble("red.y") + 1, hsf.getConfig()
								.getDouble("red.z"), (float) hsf.getConfig()
								.getDouble("red.yaw"), (float) hsf.getConfig()
								.getDouble("red.pitch")));
				event.getPlayer()
						.getInventory()
						.setHelmet(
								new ItemStack(Material.WOOL, 1, Short
										.parseShort("0"), (byte) Short
										.parseShort("14")));
			} else {
				event.setRespawnLocation(new Location(Bukkit.getWorld(hsf
						.getConfig().getString("world")), hsf.getConfig()
						.getDouble("blue.x"), hsf.getConfig().getDouble(
						"blue.y") + 1, hsf.getConfig().getDouble("blue.z"),
						(float) hsf.getConfig().getDouble("blue.yaw"),
						(float) hsf.getConfig().getDouble("blue.pitch")));
				event.getPlayer()
						.getInventory()
						.setHelmet(
								new ItemStack(Material.WOOL, 1, Short
										.parseShort("0"), (byte) Short
										.parseShort("11")));
			}
			event.getPlayer().getInventory().addItem(spiel.itemstack);

		}

	}

	@EventHandler
	public void onInventoryOpen(InventoryClickEvent event) {
		if (spiel.playerTeam.containsKey(event.getWhoClicked())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (spiel.playerTeam.containsKey(event.getPlayer())) {
			spiel.leave(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if ((event.getClickedBlock().getType() == Material.SNOW || event
					.getClickedBlock().getType() == Material.SNOW_BLOCK)
					&& event.getPlayer().getItemInHand().getType() == Material.IRON_SPADE) {
				event.getPlayer().getInventory()
						.addItem(new ItemStack(Material.SNOW_BALL, 1));
				event.getPlayer().updateInventory();
			}

		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (spiel.playerTeam.containsKey(event.getPlayer())
				&& !event.getTo().getWorld().getName()
						.equalsIgnoreCase(hsf.getConfig().getString("world"))) {
			spiel.leave(event.getPlayer());
		}
	}

	@EventHandler
	public void onEntityDeath(PlayerDeathEvent event) {
			if (spiel.playerTeam.containsKey((Player) event.getEntity())) {
				Player killer = event.getEntity().getKiller();
				hsf.messageTeams(((Player) event.getEntity()).getName()
						+ " wurde von " + killer.getName() + " getötet!");
				hsf.getConfig().set("kills."+killer.getName(), hsf.getConfig().getInt("kills."+killer.getName())+1);
				hsf.getConfig().set("deaths."+event.getEntity().getName(), hsf.getConfig().getInt("deaths."+event.getEntity().getName())+1);
				hsf.saveConfig();
				hsf.reloadConfig();
				event.getDrops().clear();
				event.setDeathMessage("");
			}
		
	}
}
