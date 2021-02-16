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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Jiyuuhome extends JavaPlugin {
    String homesetmsg="ホーム地点を設定しました。";
    String nohomemsg="ホーム地点が設定されていません。";
    String hometpmsg="ホーム地点にテレポートしました。";
    String homezyougen="ホーム地点の上限です。";
    String delhomenoname="ホームの名前を指定してください";
    String delhomenothome="ホームが見つかりませんでした。";
    String delhomeok="ホームを消去しました";
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = this.getConfig();
        Player player = (Player) sender;
        if (command.getName().equals("sethome")) {
            if (args.length == 0) {
                config.set("Homes." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
                config.set("Homes." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
                config.set("Homes." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
                config.set("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
                saveConfig();
                reloadConfig();
                player.sendMessage(homesetmsg);
            } else {
                if(!config.contains("Homes." + player.getUniqueId().toString() + ".homesets")){
                    config.set("Homes." + player.getUniqueId().toString() + ".homesets",0);
                };
                if (config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") == 4) {
                    player.sendMessage(homezyougen);
                } else {
                    if(config.contains("Homes." + player.getUniqueId().toString() + "."+args[0])){
                        player.sendMessage("名前が重複しています");
                    }else{
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".X", player.getLocation().getX());
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Y", player.getLocation().getY());
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Z", player.getLocation().getZ());
                    int homen=config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1;
                    //if(config.contains("Homes."+player.getUniqueId()+".homedate."+homen)){homen=homen+100;}
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".World", player.getLocation().getWorld().getName());
                    config.set("Homes." + player.getUniqueId().toString() + ".homesets",homen);
                    //config.set("Homes." + player.getUniqueId().toString() + ".homedate." +homen, args[0]);
                    //config.set("Homes." + player.getUniqueId().toString() + "."+args[0] + ".homen",config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1);
                    saveConfig();
                    reloadConfig();
                    player.sendMessage(homesetmsg);
                }
                }
            }
        } else if (command.getName().equals("home")) {
            if (args.length == 0) {
                if (!config.contains("Homes." + player.getUniqueId().toString() + ".X")) {
                    player.sendMessage(nohomemsg);
                } else {
                    Location loc = new Location(
                            Bukkit.getWorld(config.getString("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName())),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".X"),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".Y"),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".Z"),
                            player.getLocation().getYaw(),
                            player.getLocation().getPitch()
                    );
                    player.teleport(loc);
                    if (false) {
                        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                    player.sendMessage(hometpmsg);
                }
            } else {
            if (!config.contains("Homes." + player.getUniqueId().toString() +"."+args[0] +".X")) {
                player.sendMessage(nohomemsg);
            } else {
                Location loc = new Location(
                        Bukkit.getWorld(config.getString("Homes." + player.getUniqueId().toString() +"."+ args[0] + ".World", player.getLocation().getWorld().getName())),
                        config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] +".X"),
                        config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Y"),
                        config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] +".Z"),
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch()
                );
                player.teleport(loc);
                if (false) {
                    player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }
                player.sendMessage(hometpmsg);
            }

        }
      } else if(command.getName().equals("delhome")){
        if(args.length==0){
            player.sendMessage(delhomenoname);
        }else{
            if (!config.contains("Homes." + player.getUniqueId().toString() + "." + args[0] +".X")) {
                player.sendMessage(delhomenothome);
            }else{
                config.set("Homes." + player.getUniqueId().toString() + "."+args[0],null);
                config.set("Homes." + player.getUniqueId().toString() + ".homesets",config.getInt("Homes." + player.getUniqueId().toString() + ".homesets")-1);
                //int homen=config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1;
                //config.set("Homes." + player.getUniqueId().toString() + ".homedate." +homen,null);
                saveConfig();
                reloadConfig();
                player.sendMessage(delhomeok);
            }
        }
        }else if(command.getName().equals("homelist")){
            sender.sendMessage("登録しているホーム:");
            for (String key : config.getConfigurationSection("Homes."+player.getUniqueId().toString()).getKeys(false)) {
                //sender.sendMessage("key:"+key);
                if(config.contains("Homes."+player.getUniqueId().toString()+"."+key+".X")){
                    sender.sendMessage(key);
                }
            }
            /*player.sendMessage("あなたが登録しているホーム:");
            String num="1";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="2";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="3";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="4";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
        */}
        return true;
    }
}
