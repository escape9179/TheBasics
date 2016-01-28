/*******************************************************************************
 *  Copyright (C) 2016  Levi P. (GoldenDeveloper69)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package io.github.GoldenDeveloper79.TheBasics.Commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.GoldenDeveloper79.TheBasics.BasicUtils;
import io.github.GoldenDeveloper79.TheBasics.Enums.MultiPlayer;
import io.github.GoldenDeveloper79.TheBasics.Modules.CommandModule;

public class TimeCMD extends CommandModule
{
	public TimeCMD() 
	{
		super(new String[] {"time"}, 0, 2, MultiPlayer.OTHER);
	}

	public void performCommand(Player player, String[] args) 
	{
		if(args.length < 1)
		{
			BasicUtils.sendMessage(player, "&6The current time is &7" + formatTime(player.getWorld().getTime()) + "&6.");
		}else if(args.length == 2 && args[0].equalsIgnoreCase("set"))
		{
			if(player.hasPermission("TheBasics.Time.Set"))
			{
				try
				{
					long time = Long.parseLong(args[1]);
					player.getWorld().setTime(time);
					BasicUtils.sendMessage(player, "&6You have changed the time to &7" + formatTime(time) + "&6.");
				}catch(NumberFormatException e)
				{
					if(args[1].equalsIgnoreCase("Night"))
					{
						player.getWorld().setTime(12300);
						BasicUtils.sendMessage(player, "&6You have changed the time to night.");
					}else if(args[1].equalsIgnoreCase("Day"))
					{
						player.getWorld().setTime(0);
						BasicUtils.sendMessage(player, "&6You have set the time to day.");
					}else
					{
						BasicUtils.sendMessage(player, "&cPlease specify a valid time!");
					}
				}
			}else
			{
				BasicUtils.sendMessage(player, "&cYou do not have enough permission to perform this command!");
			}
		}else
		{
			BasicUtils.sendMessage(player, "&cUsage: " + getUsage());
		}
	}

	public void performCommand(ConsoleCommandSender console, String[] args) 
	{
		BasicUtils.sendMessage(console, "cYou must be a player to perform this command!");
	}
	
	private String formatTime(long time)
	{
		long hours = time / 1000 + 6;
		long minutes = (time % 1000) * 60 / 1000; 
		String ampm = "AM";
		
		if(hours >= 12)
		{ 
			hours = hours - 12;
			ampm = "PM"; 
		}
		
		if(hours == 0) hours = 12;
		
		String mm = "0" + minutes; 
		mm = mm.substring(mm.length() - 2, mm.length());
		
		return hours + ":" + mm + ampm;
	}
}
