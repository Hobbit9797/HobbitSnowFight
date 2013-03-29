package com.github.hobbit9797;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
			Player shooter = (Player) snowball.getShooter();
			if (!spiel.playerTeam.get(event.getEntity()).equals(
					spiel.playerTeam.get(shooter))) {
				event.setDamage(4);

				shooter.playSound(shooter.getLocation(), Sound.NOTE_PLING, 10,
						1);
			}
			else{
				event.setCancelled(true);
			}

		}
		if (event.getCause() == DamageCause.ENTITY_ATTACK
				&& spiel.playerTeam.containsKey((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	public void fall(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player){
		if (event.getCause() == DamageCause.FALL
				&& spiel.playerTeam.containsKey((Player) event.getEntity())) {
			event.setCancelled(true);
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
				ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
				ItemStack lbreast = new ItemStack(Material.LEATHER_CHESTPLATE,
						1);
				ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS,
						1);
				ItemStack lfoot = new ItemStack(Material.LEATHER_BOOTS, 1);
				lfoot.addEnchantment(Enchantment.PROTECTION_FALL, 10);
				LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
				lam.setColor(spiel.red);
				lhelmet.setItemMeta(lam);
				lbreast.setItemMeta(lam);
				lleggings.setItemMeta(lam);
				lfoot.setItemMeta(lam);
				event.getPlayer().getInventory().setHelmet(lhelmet);
				event.getPlayer().getInventory().setChestplate(lbreast);
				event.getPlayer().getInventory().setLeggings(lleggings);
				event.getPlayer().getInventory().setBoots(lfoot);
			} else {
				event.setRespawnLocation(new Location(Bukkit.getWorld(hsf
						.getConfig().getString("world")), hsf.getConfig()
						.getDouble("blue.x"), hsf.getConfig().getDouble(
						"blue.y") + 1, hsf.getConfig().getDouble("blue.z"),
						(float) hsf.getConfig().getDouble("blue.yaw"),
						(float) hsf.getConfig().getDouble("blue.pitch")));
				ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
				ItemStack lbreast = new ItemStack(Material.LEATHER_CHESTPLATE,
						1);
				ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS,
						1);
				ItemStack lfoot = new ItemStack(Material.LEATHER_BOOTS, 1);
				lfoot.addEnchantment(Enchantment.PROTECTION_FALL, 10);
				LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
				lam.setColor(spiel.blue);
				lhelmet.setItemMeta(lam);
				lbreast.setItemMeta(lam);
				lleggings.setItemMeta(lam);
				lfoot.setItemMeta(lam);
				event.getPlayer().getInventory().setHelmet(lhelmet);
				event.getPlayer().getInventory().setChestplate(lbreast);
				event.getPlayer().getInventory().setLeggings(lleggings);
				event.getPlayer().getInventory().setBoots(lfoot);
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
	public void onPlayerQuit(PlayerJoinEvent event) {
		if (spiel.playerTeam.containsKey(event.getPlayer())) {
			spiel.leave(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				&& spiel.playerTeam.containsKey(event.getPlayer())) {

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

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeath(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player){
		if (spiel.playerTeam.containsKey(event.getEntity())) {
			Player killer = event.getEntity().getKiller();
			if (spiel.playerTeam.get(killer).equals("Rot")) {
				hsf.messageTeams(ChatColor.BLUE
						+ ((Player) event.getEntity()).getName()
						+ ChatColor.GRAY + " wurde von " + ChatColor.RED
						+ killer.getName() + ChatColor.GRAY + " getötet!");
			} else {
				hsf.messageTeams(ChatColor.RED
						+ ((Player) event.getEntity()).getName()
						+ ChatColor.GRAY + " wurde von " + ChatColor.BLUE
						+ killer.getName() + ChatColor.GRAY + " getötet!");
			}

			hsf.getConfig().set("kills." + killer.getName(),
					hsf.getConfig().getInt("kills." + killer.getName()) + 1);
			hsf.getConfig().set(
					"deaths." + event.getEntity().getName(),
					hsf.getConfig().getInt(
							"deaths." + event.getEntity().getName()) + 1);
			killer.setFoodLevel(20);
			try {
				killer.setHealth(killer.getHealth() + 10);
			} catch (Exception e) {

				killer.setHealth(20);
			}
			hsf.saveConfig();
			hsf.reloadConfig();
			event.getDrops().clear();
			event.setDeathMessage("");
		}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		if (spiel.playerTeam.containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (spiel.playerTeam.containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}

	}

}
