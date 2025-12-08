/*
 * Copyright (c) 2025 Jandin12 - All Rights Reserved.
 * See the LICENSE.txt file in the root directory for usage exceptions.
 */
package com.easycycle;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TradeCyclePlugin extends JavaPlugin implements Listener, CommandExecutor {

    // MiniMessage handles modern color codes easily (<green>, <red>, etc.)
    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        // 1. Create the config file if it doesn't exist
        saveDefaultConfig();

        // 2. Register Events and Commands
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("easycycle").setExecutor(this);
        
        getLogger().info("EasyCycle loaded successfully!");
    }

    // --- COMMAND HANDLING ---
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("easycycle.admin")) {
            sender.sendMessage(mm.deserialize("<red>You do not have permission to use this command."));
            return true;
        }

        if (args.length == 0) return false; // Show usage

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                reloadConfig();
                sender.sendMessage(mm.deserialize("<green>EasyCycle config reloaded!"));
                getLogger().info("Config reloaded by " + sender.getName());
            }
            case "version" -> {
                String version = getDescription().getVersion();
                sender.sendMessage(mm.deserialize("<b_blue>EasyCycle</b_blue> is running version <yellow>" + version));
            }
            default -> sender.sendMessage(mm.deserialize("<red>Unknown subcommand. Use /ec reload or /ec version"));
        }
        return true;
    }

    // --- TRIGGER 1: SHIFT + RIGHT CLICK ---
    @EventHandler
    public void onShiftClick(PlayerInteractEntityEvent event) {
        if (!getConfig().getString("trigger-mode", "SHIFT_CLICK").equalsIgnoreCase("SHIFT_CLICK")) return;
        
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getRightClicked().getType() == EntityType.VILLAGER && event.getPlayer().isSneaking()) {
            if (event.getPlayer().hasPermission("easycycle.use")) {
                cycleVillager(event.getPlayer(), (Villager) event.getRightClicked());
            }
        }
    }

    // --- TRIGGER 2: PRESS 'F' (SWAP HANDS) ---
    @EventHandler
    public void onSwapKey(PlayerSwapHandItemsEvent event) {
        if (!getConfig().getString("trigger-mode", "SHIFT_CLICK").equalsIgnoreCase("SWAP_KEY")) return;

        Player player = event.getPlayer();
        
        // Raytrace: Get the entity the player is looking at (Range: 5 blocks)
        var target = player.getTargetEntity(5);

        if (target != null && target.getType() == EntityType.VILLAGER) {
            if (player.hasPermission("easycycle.use")) {
                // Cancel the actual swap so items don't move
                event.setCancelled(true);
                cycleVillager(player, (Villager) target);
            }
        }
    }

    // --- CORE LOGIC ---
    private void cycleVillager(Player player, Villager villager) {
        // Safety Check: XP > 0 means they have locked trades
        if (villager.getVillagerExperience() > 0) {
            player.sendMessage(mm.deserialize(getConfig().getString("msg-cannot-cycle")));
            return;
        }

        Villager.Profession currentJob = villager.getProfession();
        if (currentJob == Villager.Profession.NONE) {
            player.sendMessage(mm.deserialize(getConfig().getString("msg-no-job")));
            return;
        }

        // The Cycle: Fire and Rehire
        villager.setProfession(Villager.Profession.NONE);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            villager.setProfession(currentJob);
            villager.getWorld().playSound(villager.getLocation(), Sound.ENTITY_VILLAGER_WORK_LIBRARIAN, 1f, 1f);
            player.sendMessage(mm.deserialize(getConfig().getString("msg-cycle-success")));
            villager.shakeHead();
        }, 1L);
    }

}
