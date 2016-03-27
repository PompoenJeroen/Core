package me.winterguardian.core;

import me.winterguardian.core.json.JsonUtil;
import me.winterguardian.core.util.TabUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.UUID;

/**
 *
 * Created by Alexander Winter on 2016-01-23.
 */
public class CreativeListener implements Listener
{
	private String TAB_HEADER = JsonUtil.toJson("§d§lCréatif §fde §f§lSekai§6§lMC"), TAB_FOOTER = JsonUtil.toJson("§e§l/hub §fpour retourner au hub");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		TabUtil.sendInfos(event.getPlayer(), TAB_HEADER, TAB_FOOTER);
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event)
	{
		if(!(event.getEntity().getShooter() instanceof Player) || !((Player)event.getEntity().getShooter()).isOp())
			event.setCancelled(true);
	}

	@EventHandler
	public void whenSku4lyDoesBullshit(BlockPlaceEvent event)
	{
		if(event.getPlayer().getUniqueId().equals(UUID.fromString("a0983fd0-a655-4578-86b7-11be904d348c"))
		&& (event.getBlockPlaced().getType() == Material.STANDING_BANNER || event.getBlockPlaced().getType() == Material.WALL_BANNER))
		{
			event.setCancelled(true);
			event.getPlayer().setHealth(0);
		}
	}

	@EventHandler
	public void onRainStart(WeatherChangeEvent event)
	{
		if(event.toWeatherState())
			event.setCancelled(true);
	}
}
