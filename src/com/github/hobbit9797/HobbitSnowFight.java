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
			p.sendMessage(ChatColor.DARK_AQUA + "[HSF]: " + ChatColor.GRAY
					+ nachricht);
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("call")) {
			StringBuilder sb = new StringBuilder();
			for (String arg : args)
				sb.append(arg + " ");
			messageAll(sb.toString());
			return true;
		} else if (cmd.getName().equalsIgnoreCase("hsf")
				&& sender instanceof Player) {
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
					// commands.hilfe(spieler);
					messagePlayer("/hsf j: Trete HSF bei", (Player) sender);
					messagePlayer("/hsf h: Liste die Hilfe auf",
							(Player) sender);
					messagePlayer("/hsf l: Verlasse das Spiel", (Player) sender);
					messagePlayer("/hsf s: Zeige Statistiken", (Player) sender);
				}

				if (args[0].equalsIgnoreCase("l")) {
					spiel.leave((Player) sender);
				}
				// admin befehle
				if (args[0].equalsIgnoreCase("setred")) {

				}
				if (args[0].equalsIgnoreCase("setblue")) {

				}

			}
			return (true);
		} else {
			return (false);
		}
	}
}
