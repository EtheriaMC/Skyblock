//package xyz.etheriamc.skyblock.coinflip;
//
//import com.google.common.collect.Lists;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import lombok.Getter;
//import lombok.Setter;
//import net.minecraft.server.v1_8_R3.NBTTagCompound;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.OfflinePlayer;
//import org.bukkit.command.CommandSender;
//import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import xyz.etheriamc.skyblock.coinflip.events.CustomCosmicPlayerEvent;
//import xyz.etheriamc.skyblock.coinflip.struct.CoinColor;
//import xyz.etheriamc.skyblock.coinflip.struct.WinLossStats;
//import xyz.etheriamc.skyblock.coinflip.utils.ItemBuilder;
//import xyz.etheriamc.skyblock.coinflip.utils.SlotUtils;
//
//public class CoinFlipManager {
//    @Getter
//    private final Set<CoinFlipMatch> coinFlipMatches = new HashSet<>();
//
//    @Getter
//    private Map<UUID, WinLossStats> winLossStats = new HashMap<>();
//
//    private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//    private File flipRecords;
//
//    private final DecimalFormat format = new DecimalFormat("#,###");
//
//    @Getter
//    private final List<UUID> toggledNotifications = Lists.newArrayList();
//
//    private final Map<UUID, UUID> previousOpponent = new HashMap<>();
//
//    @Getter
//    @Setter
//    private int alertAmount = 1000000;
//
//    @Setter
//    @Getter
//    private double taxCollected = 0.0D;
//
//   /* public void loadToggles() {
//        List<String> toggles = CosmicCoinFlip.get().getConfig().getStringList("toggles");
//        for (String string : toggles) {
//            try {
//                this.toggledNotifications.add(UUID.fromString(string));
//            } catch (Exception e) {
//                Bukkit.getLogger().info("unable to load uuid in toggles: " + string);
//                e.printStackTrace();
//            }
//        }
//    }*/
//
//    public void clearStats() throws IOException {
//        this.winLossStats.clear();
//        saveCoinFlipRecords();
//        Bukkit.getLogger().info("Clearing all CoinFlip stats...");
//    }
//
////    public void saveToggles() {
////        List<String> toggles = Lists.newArrayList();
////        this.toggledNotifications.forEach(id -> toggles.add(id.toString()));
////        CosmicCoinFlip.get().getConfig().set("toggles", toggles);
////        CosmicCoinFlip.get().saveConfig();
////    }
//
//    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
//
///*    public Inventory getLobbyInventory(Player player) {
//        Inventory inventory = Bukkit.createInventory(null, SlotUtils.fitSlots((int)this.coinFlipMatches.stream().filter(match -> (match.getMatchState() == CoinFlipMatch.MatchState.WAITING)).count() + 2), "Coin Flip Matches");
//        updateInventory(player, inventory);
//        return inventory;
//    }*/
//
////    public void updateInventory(Player player, Inventory inventory) {
////        inventory.clear();
////        this.coinFlipMatches.forEach(match -> {
////            if (match.getMatchState() != CoinFlipMatch.MatchState.WAITING)
////                return;
////            double balance = CosmicCoinFlip.getEconomy().getBalance((OfflinePlayer)player);
////            WinLossStats stats = CosmicCoinFlip.get().getCoinFlipManager().getWinLossStats().get(match.getStarterPlayer().getUniqueId());
////            String winLoss = (stats == null) ? "0 (0:0)" : (((stats.getLosses() == 0) ? (String)Integer.valueOf(stats.getWins()) : this.decimalFormat.format(Double.valueOf(stats.getWins()).doubleValue() / Double.valueOf(stats.getLosses()).doubleValue())) + " (" + stats.getWins() + ":" + stats.getLosses() + ")");
////            String dailyWinLoss = (stats == null) ? "0 (0:0)" : (((stats.getDailyLosses() == 0) ? (String)Integer.valueOf(stats.getDailyWins()) : this.decimalFormat.format(Double.valueOf(stats.getDailyWins()).doubleValue() / Double.valueOf(stats.getDailyLosses()).doubleValue())) + " (" + stats.getDailyWins() + ":" + stats.getDailyLosses() + ")");
////            ItemStack stack = ItemBuilder.createSkull(match.getStarterPlayer().getName(), FactionUtils.getFactionTag(player, match.getStarterPlayer().getUniqueId()) + ChatColor.BOLD.toString() + match.getStarterPlayer().getName(), "", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Wager", ChatColor.GRAY + " " + ChatColor.GRAY + ChatColor.BOLD + "$" + ChatColor.GRAY + this.format.format(match.getWager()), ChatColor.GRAY + ChatColor.ITALIC.toString() + " -5% Tax " + ChatColor.GRAY + "($" + this.format.format(match.getTax()) + ")", "", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Color Chosen", match.getFirstColor().getName(), "", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Recent Win/Loss Ratio", ChatColor.GRAY + " " + dailyWinLoss,
////                    "", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Total Win/Loss Ratio", ChatColor.GRAY + " " + winLoss, "", (balance >= match.getWager()) ? (ChatColor.GRAY + "Click to accept this coin flip wager") : (ChatColor.GRAY + "You can " + ChatColor.RED + ChatColor.BOLD + "NOT" + ChatColor.GRAY + " afford this wager!"));
////            net.minecraft.server.v1_8_R3.ItemStack item = CraftItemStack.asNMSCopy(stack);
////            NBTTagCompound tag = item.getTag();
////            if (tag == null) {
////                tag = new NBTTagCompound();
////                item.setTag(tag);
////            }
////            tag.setString("wager", match.getStarterPlayer().getName());
////            inventory.addItem(CraftItemStack.asCraftMirror(item));
////        });
////        inventory.setItem(inventory.getSize() - 2, ItemBuilder.buildItem(Material.BOOK, ChatColor.GREEN.toString() + ChatColor.BOLD + "Coin Flip Help", "", ChatColor.YELLOW + "/coinflip", ChatColor.GRAY + "View all active Coin Flip matches.", "", ChatColor.YELLOW + "/coinflip <amount>", ChatColor.GRAY + "Start a Coin Flip match with the given wager.", "", ChatColor.YELLOW + "/coinflip cancel", ChatColor.GRAY + "Cancel the pending Coin Flip match.", "",
////                ChatColor.YELLOW + "/coinflip toggle", ChatColor.GRAY + "Toggle Coin Flip win notifications.", "", ChatColor.YELLOW + "/coinflip stats", ChatColor.GRAY + "View your Coinflip stats with /coinflip stats"));
////        inventory.setItem(inventory.getSize() - 1, ItemBuilder.buildItem(Material.CHEST, ChatColor.GREEN + ChatColor.BOLD.toString() + "Refresh", ChatColor.GRAY + "Click to refresh coinflip matches."));
////    }
////
////    public Inventory getCoinFlipSelectMenu(double wage, CoinColor picked) {
////        Inventory inventory = Bukkit.createInventory(null, SlotUtils.fitSlots((CoinColor.values()).length), "Choose a Color");
////        for (CoinColor color : CoinColor.values()) {
////            if (!color.equals(picked))
////                inventory.addItem(ItemBuilder.buildItem(Material.WOOL, color.getDyeColor().getWoolData(), color
////                        .getName(), ChatColor.GRAY + "Click to select this color", ChatColor.GRAY + "and start the coin clip for", ChatColor.GREEN + ChatColor.BOLD
////
////                        .toString() + "$" + ChatColor.GREEN + this.format.format(wage)));
////        }
////        return inventory;
////    }
//
//    public CoinFlipMatch getMatch(Player player) {
//        for (CoinFlipMatch match : this.coinFlipMatches) {
//            if (match.getStarterPlayer().equals(player) || (match.getSecondPlayer() != null && match.getSecondPlayer().equals(player)))
//                return match;
//        }
//        return null;
//    }
//
//    public String getWinLossRatio(UUID uuid, String delimeter) {
//        WinLossStats stats = this.winLossStats.get(uuid);
//        if (stats == null)
//            return "0" + delimeter + "0";
//        return stats.getDailyWins() + delimeter + stats.getDailyLosses();
//    }
//
//    public void addWin(UUID uuid, UUID loser, double winnings, double tax) {
//        winnings /= 2.0D;
//        WinLossStats stats = this.winLossStats.computeIfAbsent(uuid, k -> new WinLossStats());
//        stats.setWins(stats.getWins() + 1);
//        stats.setWinnings((long)(stats.getWinnings() + winnings));
//        stats.setDailyWinnings((long)(stats.getDailyWinnings() + winnings));
//        stats.setDailyWins(stats.getDailyWins() + 1);
//        stats.setTaxesPaid((long)(stats.getTaxesPaid() + tax));
//        this.taxCollected += tax;
///*        CosmicCoinFlip.get().getConfig().set("taxCollected", Double.valueOf(this.taxCollected));
//        CosmicCoinFlip.get().saveConfig();
//        UUID lastOppon = this.previousOpponent.get(uuid);
//        if (lastOppon != null && lastOppon.equals(loser))
//            return;
//        if (PlayerListener.hasSameIP(uuid, loser)) {
//            Bukkit.getLogger().info("Skipping coinflip WIN contest tracking for " + uuid + " vs " + loser + " due to same IP Address.");
//            return;
//        }
//        this.previousOpponent.put(uuid, loser);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + uuid.toString() + " cfWon 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + uuid + " cfMoneyWon " + Math.round(winnings));*/
//    }
//
//    public void addLoss(UUID uuid, UUID winner, double lost) {
//        WinLossStats stats = this.winLossStats.computeIfAbsent(uuid, k -> new WinLossStats());
//        stats.setLosses(stats.getLosses() + 1);
//        stats.setLosings((long)(stats.getLosings() + lost));
//        stats.setDailyLosings((long)(stats.getDailyLosings() + lost));
//        stats.setDailyLosses(stats.getDailyLosses() + 1);
//        stats.setTaxesPaid((long)(stats.getTaxesPaid() + lost * CoinFlipMatch.TAX));
//        Player loserPlayer = Bukkit.getPlayer(uuid);
//        if (loserPlayer != null) {
//            Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(loserPlayer, "CF_LOSE", lost, winner));
//        } else {
//            Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(uuid, "CF_LOSE", lost, winner));
//        }
///*        UUID lastOppon = this.previousOpponent.get(uuid);
//        if (lastOppon != null && lastOppon.equals(winner))
//            return;
//        if (PlayerListener.hasSameIP(uuid, winner)) {
//            Bukkit.getLogger().info("Skipping coinflip loss contest tracking for " + uuid + " due to same IP Address.");
//            return;
//        }
//        this.previousOpponent.put(uuid, winner);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + uuid.toString() + " cfMoneyLoss " + Math.round(lost));*/
//    }
//
//    /*public void loadCoinFlips() throws IOException {
//        try {
//            this.flipRecords = new File(CosmicCoinFlip.get().getDataFolder(), "flipRecords.json");
//            if (!this.flipRecords.exists())
//                this.flipRecords.createNewFile();
//            StringBuilder jsonString = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new FileReader(this.flipRecords));
//            String line;
//            while ((line = reader.readLine()) != null)
//                jsonString.append(line);
//            this.winLossStats = this.gson.fromJson(jsonString.toString(), (new TypeToken<HashMap<UUID, WinLossStats>>() {}).getType());
//            if (this.winLossStats == null)
//                this.winLossStats = new HashMap<>();
//            this.winLossStats.values().forEach(WinLossStats::clearDailyStats);
//            this.alertAmount = CosmicCoinFlip.get().getConfig().getInt("alertAmount");
//            if (this.alertAmount == 0)
//                this.alertAmount = 1000000;
//            this.taxCollected = CosmicCoinFlip.get().getConfig().getDouble("taxCollected");
//        } catch (Throwable $ex) {
//            throw $ex;
//        }
//    }*/
//
//    public void saveCoinFlipRecords() throws IOException {
//        try {
//            String map = this.gson.toJson(this.winLossStats);
//            FileWriter writer = new FileWriter(this.flipRecords);
//            try {
//                writer.write(map);
//                writer.flush();
//            } finally {
//                if (Collections.singletonList(writer).get(0) != null)
//                    writer.close();
//            }
//        } catch (Throwable $ex) {
//            throw $ex;
//        }
//    }
//}
