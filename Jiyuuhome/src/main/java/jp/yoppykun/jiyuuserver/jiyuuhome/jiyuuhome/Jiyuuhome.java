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
    String homesetmsg="ホーム地点を設定しました。";
    String nohomemsg="ホーム地点が設定されていません。";
    String hometpmsg="ホーム地点にテレポートしました。";
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = this.getConfig();
        Player player = (Player)sender;
        if (command.getName().equals("sethome")) {
                config.set("Homes." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
                config.set("Homes." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
                config.set("Homes." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
                config.set("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
                saveConfig();
                reloadConfig();
                player.sendMessage(homesetmsg);
        } else if (command.getName().equals("home")) {
            if (!config.contains("Homes."+player.getUniqueId().toString()+".X")){
                player.sendMessage(nohomemsg);
            } else{
                Location loc=new Location(
                        Bukkit.getWorld(config.getString("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName())),
                        config.getDouble("Homes." + player.getUniqueId().toString() + ".X"),
                        config.getDouble("Homes." + player.getUniqueId().toString() + ".Y"),
                        config.getDouble("Homes." + player.getUniqueId().toString() + ".Z"),
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch()
                );
                player.teleport(loc);
                if (false){
                    player.playSound(loc,Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }
                player.sendMessage(hometpmsg);
                }
        }
        return true;
    }
}
