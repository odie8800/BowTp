package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener; 
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BowTp extends JavaPlugin
	implements Listener
{
	@Override
	public void onEnable()
	{
		getLogger().info("Booted.");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		if(!getDataFolder().exists())
			saveDefaultConfig();
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("Disabled.");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTP(ProjectileHitEvent e)
	{
		Projectile proj = e.getEntity();
		if(proj instanceof Arrow)
		{
			Arrow a = (Arrow) proj;
				LivingEntity p = (Player) a.getShooter();
				p.teleport(a);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String args[])
	{
		switch(label.toLowerCase())
		{
			case "bowtp":
				exeCommandBowTp(sender, args);
		}
		return false;
	}

	private void exeCommandBowTp(CommandSender sender, String[] args)
	{
		FileConfiguration config = getConfig();
		if(args.length == 0)
		{
			sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[BowTp] ").append(ChatColor.AQUA).append("/BowTp [True, False]").toString());
		} else if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("t") && sender.hasPermission("bowtp.switch"))
		{
			config.set("bow-teleportation", Boolean.valueOf(true));
			sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[BowTp] ").append(ChatColor.AQUA).append("BowTp has been enabled!").toString());
		} else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("f") && sender.hasPermission("bowtp.switch"))
		{
			config.set("bow-teleportation", Boolean.valueOf(false));
			sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[BowTp] ").append(ChatColor.AQUA).append("BowTp has been disabled!").toString());
		} else
		{
			sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[BowTp] ").append(ChatColor.AQUA).append("/BowTp [True, False]").toString());
		}
	}
}

