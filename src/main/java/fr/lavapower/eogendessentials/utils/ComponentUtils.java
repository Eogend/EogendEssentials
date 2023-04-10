package fr.lavapower.eogendessentials.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentUtils
{
    public static TextComponent createTextComponent(String text) {
        return createTextComponent(text, ChatColor.RESET, null, null);
    }

    public static TextComponent createTextComponent(String text, ChatColor color) {
        return createTextComponent(text, color, null, null);
    }

    public static TextComponent createTextComponent(String text, HoverEvent hoverEvent) {
        return createTextComponent(text, ChatColor.RESET, hoverEvent, null);
    }

    public static TextComponent createTextComponent(String text, ClickEvent clickEvent) {
        return createTextComponent(text, ChatColor.RESET, null, clickEvent);
    }

    public static TextComponent createTextComponent(String text, ChatColor color, ClickEvent clickEvent) {
        return createTextComponent(text, color, null, clickEvent);
    }

    public static TextComponent createTextComponent(String text, ChatColor color, HoverEvent hoverEvent) {
        return createTextComponent(text, color, hoverEvent, null);
    }

    public static TextComponent createTextComponent(String text, HoverEvent hoverEvent, ClickEvent clickEvent) {
        return createTextComponent(text, ChatColor.RESET, hoverEvent, clickEvent);
    }

    public static TextComponent createTextComponent(String text, ChatColor color, HoverEvent hoverEvent, ClickEvent clickEvent) {
        TextComponent component = new TextComponent(text);
        component.setColor(color);
        component.setHoverEvent(hoverEvent);
        component.setClickEvent(clickEvent);
        return component;
    }
}
