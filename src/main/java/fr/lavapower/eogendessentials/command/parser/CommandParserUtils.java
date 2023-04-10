package fr.lavapower.eogendessentials.command.parser;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandParserUtils
{
    public static Result<World> GetWorldFromString(String world)
    {
        World w = Bukkit.getWorld(world);
        return new Result<>(w, w != null);
    }

    public static Result<Player> GetPlayerFromString(String player, Location from)
    {
        Player p = null;
        if(player.equals("@p"))
        {
            for(Player pla : Bukkit.getOnlinePlayers())
            {
                if(pla.getWorld() != from.getWorld())
                    continue;

                if(p == null || (p.getWorld() == pla.getWorld() && from.distance(pla.getLocation()) <= from.distance(p.getLocation())))
                    p = pla;
            }
        }
        else
            p = Bukkit.getPlayer(player);
        return new Result<>(p, p != null);
    }

    public static Result<Double> GetDoubleFromString(String integer)
    {
        return GetDoubleFromString(integer, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public static Result<Double> GetDoubleFromString(String integer, double min)
    {
        return GetDoubleFromString(integer, min, Double.MAX_VALUE);
    }

    public static Result<Double> GetDoubleFromString(String integer, double min, double max)
    {
        try
        {
            double nb = Double.parseDouble(integer);
            if(nb < min)
                return new Result<>(nb, false);
            if(nb > max)
                return new Result<>(nb, false);
            return new Result<>(nb, true);
        }
        catch(NumberFormatException ignored)
        {
            return new Result<>(0., false);
        }
    }

    public static Result<Float> GetFloatFromString(String integer)
    {
        return GetFloatFromString(integer, -Float.MAX_VALUE, Float.MAX_VALUE);
    }

    public static Result<Float> GetFloatFromString(String integer, float min)
    {
        return GetFloatFromString(integer, min, Float.MAX_VALUE);
    }

    public static Result<Float> GetFloatFromString(String integer, float min, float max)
    {
        try
        {
            float nb = Float.parseFloat(integer);
            if(nb < min)
                return new Result<>(nb, false);
            if(nb > max)
                return new Result<>(nb, false);
            return new Result<>(nb, true);
        }
        catch(NumberFormatException ignored)
        {
            return new Result<>(0f, false);
        }
    }

    public static Result<Integer> GetIntegerFromString(String integer)
    {
        return GetIntegerFromString(integer, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static Result<Integer> GetIntegerFromString(String integer, int min)
    {
        return GetIntegerFromString(integer, min, Integer.MAX_VALUE);
    }

    public static Result<Integer> GetIntegerFromString(String integer, int min, int max)
    {
        try
        {
            int nb = Integer.parseInt(integer);
            if(nb < min)
                return new Result<>(nb, false);
            if(nb > max)
                return new Result<>(nb, false);
            return new Result<>(nb, true);
        }
        catch(NumberFormatException ignored)
        {
            return new Result<>(0, false);
        }
    }

    public static Result<Color> GetColorFromString(String arg)
    {
        return new Result<Color>(Color.fromRGB(ChatColor.of(arg).getColor().getRGB()), true);
    }
}
