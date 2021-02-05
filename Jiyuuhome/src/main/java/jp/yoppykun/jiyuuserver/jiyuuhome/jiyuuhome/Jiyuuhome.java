package jp.yoppykun.jiyuuserver.jiyuuhome.jiyuuhome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Jiyuuhome extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*FileConfiguration setting = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"setting.yml"));
        getLogger().info(setting..toString());*/
        FileConfiguration config = this.getConfig();
        Player player = (Player)sender;
        if (command.getName().equals("sethome")) {
                Location loc = player.getLocation();
                config.set("Homes.Homedate." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
                config.set("Homes.Homedate." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
                config.set("Homes.Homedate." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
                config.set("Homes.Homedate." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
                saveConfig();
                reloadConfig();
                //セットメッセージ
                player.sendMessage("§6homeをセットしました!");
        } else if (command.getName().equals("home")) {
            if (!config.contains("Homes.Homedate."+player.getUniqueId().toString()+".X")){
                //見つからないときのメッセージ
                player.sendMessage("§4homeが見つかりませんでした");
            } else{
                Location loc=new Location(
                        Bukkit.getWorld(config.getString("Homes.Homedate." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName())),
                        config.getDouble("Homes.Homedate." + player.getUniqueId().toString() + ".X"),
                        config.getDouble("Homes.Homedate." + player.getUniqueId().toString() + ".Y"),
                        config.getDouble("Homes.Homedate." + player.getUniqueId().toString() + ".Z"),
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch()
                );
                player.teleport(loc);
                //音声
                if (false){
                    player.playSound(loc,Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }
                //tpメッセージ
                player.sendMessage("§6Homeにテレポートしました!");
                }
        }
        return true;
    }
    /*@Override
    public void onDisable() {
        // Plugin shutdown logic
    }*/
}
