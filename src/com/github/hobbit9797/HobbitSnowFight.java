package com.github.hobbit9797;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class HobbitSnowFight extends JavaPlugin {

	public Spiel spiel;
	public Server server = this.getServer();
	public EntityListener el;

	public void onEnable() {

		spiel = new Spiel(this, el);
		el = new EntityListener(this, spiel);

		// register the entity listener we stored...
		getServer().getPluginManager().registerEvents(el, this);

		loadConfiguration();
	}

	public void messageAll(String nachricht) {

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.DARK_AQUA + "[HSF]: " + ChatColor.GRAY
					+ nachricht);
		}

	}

	public void messagePlayer(String nachricht, Player sender) {

		sender.sendMessage(ChatColor.DARK_AQUA + "[HSF]: " + ChatColor.GRAY
				+ nachricht);

	}

	public void messageTeams(String nachricht) {

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (spiel.playerTeam.containsKey(p)) {
				p.sendMessage(ChatColor.DARK_AQUA + "[HSF]: " + ChatColor.GRAY
						+ nachricht);
			}
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("hsf") && sender instanceof Player) {
			if (args == null || args.length == 0) {
				messagePlayer("HobbitSnowFight von Hobbit9797 für Tauncraft",
						(Player) sender);
			} else {

				// short Befehle
				if (args[0].equalsIgnoreCase("j")) {
					Player spieler = (Player) sender;
					spiel.join(spieler);

				}
				if (args[0].equalsIgnoreCase("h")) {
					messagePlayer("/hsf j: Trete HSF bei", (Player) sender);
					messagePlayer("/hsf h: Liste die Hilfe auf",
							(Player) sender);
					messagePlayer("/hsf l: Verlasse das Spiel", (Player) sender);
					messagePlayer("/hsf s: Zeige Statistiken", (Player) sender);
				}

				if (args[0].equalsIgnoreCase("l")) {
					spiel.leave((Player) sender);
				}
				
				if (args[0].equalsIgnoreCase("s")) {
					messagePlayer("Getötete Spieler: " + getConfig().getInt("kills."+sender.getName()), (Player)sender);
					messagePlayer("Tode: " + getConfig().getInt("deaths."+sender.getName()), (Player)sender);
				}
				// admin befehle
				if (args[0].equalsIgnoreCase("setred")
						&& (sender.hasPermission("hsf.admin") || sender.isOp())) {
					Player spieler = (Player) sender;
					getConfig().set("red.x", spieler.getLocation().getX());
					getConfig().set("red.y", spieler.getLocation().getY());
					getConfig().set("red.z", spieler.getLocation().getZ());
					getConfig().set("red.pitch",
							spieler.getLocation().getPitch());
					getConfig().set("red.yaw", spieler.getLocation().getYaw());
					getConfig().set("world",
							spieler.getLocation().getWorld().getName());
					saveConfig();
					reloadConfig();
				}
				if (args[0].equalsIgnoreCase("setblue")
						&& (sender.hasPermission("hsf.admin") || sender.isOp())) {
					Player spieler = (Player) sender;
					getConfig().set("blue.x", spieler.getLocation().getX());
					getConfig().set("blue.y", spieler.getLocation().getY());
					getConfig().set("blue.z", spieler.getLocation().getZ());
					getConfig().set("blue.pitch",
							spieler.getLocation().getPitch());
					getConfig().set("blue.yaw", spieler.getLocation().getYaw());
					getConfig().set("world",
							spieler.getLocation().getWorld().getName());
					saveConfig();
					reloadConfig();
				}

			}
			return (true);
		} else {
			return (false);
		}
	}

	public void loadConfiguration() {
		getConfig().addDefault("red.x", 0);
		getConfig().addDefault("red.y", 0);
		getConfig().addDefault("red.z", 0);
		getConfig().addDefault("red.pitch", 0);
		getConfig().addDefault("red.yaw", 0);
		getConfig().addDefault("blue.x", 0);
		getConfig().addDefault("blue.y", 0);
		getConfig().addDefault("blue.z", 0);
		getConfig().addDefault("blue.pitch", 0);
		getConfig().addDefault("blue.yaw", 0);
		getConfig().addDefault("world", "world");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
