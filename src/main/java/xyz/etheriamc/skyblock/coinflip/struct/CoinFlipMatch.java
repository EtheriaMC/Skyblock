//package xyz.etheriamc.skyblock.coinflip.struct;
//
//import com.google.common.collect.Lists;
//import lombok.Getter;
//import lombok.Setter;
//import net.milkbowl.vault.economy.EconomyResponse;
//import org.bukkit.*;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.plugin.Plugin;
//import org.bukkit.scheduler.BukkitRunnable;
//import xyz.etheriamc.skyblock.EtheriaSkyblock;
//import xyz.etheriamc.skyblock.coinflip.CoinFlipManager;
//import xyz.etheriamc.skyblock.coinflip.utils.ItemBuilder;
//
//import java.text.DecimalFormat;
//import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Getter
//@Setter
//public class CoinFlipMatch {
//    @Getter
//    private Player starterPlayer;
//
//    @Getter
//    private Player secondPlayer;
//
//    @Setter
//    @Getter
//    private CoinColor firstColor;
//
//    @Setter
//    @Getter
//    private CoinColor secondColor;
//
//    @Setter
//    @Getter
//    private CoinColor winningColor;
//
//    @Setter
//    @Getter
//    private MatchState matchState = MatchState.WAITING;
//
//    @Getter
//    private double wager = 0.0D;
//
//    public static double TAX = 0.05D;
//
//    private Inventory coinFlipMenu;
//
//    private boolean gavePrize = false;
//
//    private String playerName;
//
//    private String player2Name;
//
//    private UUID playerUUID;
//
//    private UUID player2UUID;
//
//    private static Map<UUID, ItemStack> defaultHeadStacks = new HashMap<>();
//
//    private CoinColor previousColor;
//
//    private DecimalFormat format;
//
//    public double getTax() {
//        return TAX * this.wager;
//    }
//
//    public ItemStack getSkull(Player player, Player other) {
//        ItemStack defaultSkill = defaultHeadStacks.computeIfAbsent(player.getUniqueId(), k -> ItemBuilder.createSkull(player.getName(), null, null));
//        ItemStack skull = defaultSkill.clone();
//        ItemMeta im = skull.getItemMeta();
//        im.setDisplayName( ChatColor.BOLD + player.getName());
//        im.setLore(Lists.newArrayList(Arrays.toString(new String[] { ChatColor.GREEN + "Balance: " + ChatColor.GREEN + ChatColor.BOLD + "$" + ChatColor.GREEN + this.format
//                .format(EtheriaSkyblock.instance.getEconomy().getBalance(player)) })));
//        skull.setItemMeta(im);
//        return skull;
//    }
//
//    public CoinFlipMatch(Player starter, double wager, CoinColor firstColor) {
//        this.previousColor = CoinColor.BLUE;
//        this.format = new DecimalFormat("#,###");
//        this.wager = wager;
//        this.starterPlayer = starter;
//        this.previousColor = firstColor;
//        this.playerUUID = starter.getUniqueId();
//        this.playerName = starter.getName();
//        this.firstColor = firstColor;
//        EtheriaSkyblock.instance.getEconomy().withdrawPlayer(starter, this.wager);
//        starter.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "- $" + ChatColor.RED + this.format.format(this.wager));
//    }
//
//    public void buildFlipMenu() {
//        String title = "Coin Flip Match - $" + this.format.format(this.wager * 2.0D);
//        if (title.length() >= 32)
//            title = title.substring(0, 31);
//        this.coinFlipMenu = Bukkit.createInventory(null, 9, title);
//        this.coinFlipMenu.setItem(0, getSkull(getStarterPlayer(), getSecondPlayer()));
//        this.coinFlipMenu.setItem(1, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, getFirstColor().getDyeColor().getWoolData(), getFirstColor().getChatColor() + getStarterPlayer().getName() + "'s Color", Lists.newArrayList()));
//        this.coinFlipMenu.setItem(7, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, getSecondColor().getDyeColor().getWoolData(), getSecondColor().getChatColor() + getSecondPlayer().getName() + "'s Color", Lists.newArrayList()));
//        this.coinFlipMenu.setItem(8, getSkull(getSecondPlayer(), getStarterPlayer()));
//        this.matchState = MatchState.STARTING;
//        this.coinFlipMenu.setItem(4, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 5, DyeColor.LIME.getWoolData(), ChatColor.GREEN + ChatColor.BOLD.toString() + '\005', ChatColor.GRAY + "Rolling in " + '\005' + "s"));
//        (new BukkitRunnable() {
//            int timer = 0;
//
//            public void run() {
//                this.timer++;
//                if (this.timer >= 5) {
//                    cancel();
//                    CoinFlipMatch.this.startRoll();
//                    return;
//                }
//                for (Player pl : CoinFlipMatch.this.getPlayers()) {
//                    if (pl == null || !pl.isOnline()) {
//                        cancel();
//                        return;
//                    }
//                    pl.playSound(pl.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.4F);
//                }
//                CoinFlipMatch.this.coinFlipMenu.setItem(4, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 5 - this.timer, (this.timer <= 2) ? DyeColor.LIME.getWoolData() : ((this.timer <= 4) ? DyeColor.YELLOW.getWoolData() : DyeColor.RED.getWoolData()), ChatColor.GREEN + ChatColor.BOLD.toString() + (5 - this.timer), ChatColor.GRAY + "Rolling in " + (5 - this.timer) + "s"));
//            }
//        }).runTaskTimer(EtheriaSkyblock.instance, 20L, 20L);
//    }
//
//    public void startRoll() {
//        this.matchState = MatchState.FLIPPING;
//        (new BukkitRunnable() {
//            int ticks = 0;
//
//            final int maxTicks = 70 + ThreadLocalRandom.current().nextInt(2) + ThreadLocalRandom.current().nextInt(2) + ThreadLocalRandom.current().nextInt(2);
//
//            public void run() {
//                if (CoinFlipMatch.this.gavePrize) {
//                    cancel();
//                    return;
//                }
//                if (this.ticks >= this.maxTicks) {
//                    cancel();
//                    ItemStack winner = CoinFlipMatch.this.coinFlipMenu.getItem(4);
//                    if (winner != null && winner.getType() == Material.WOOL) {
//                        CoinFlipMatch.this.winningColor = CoinColor.getFromDyeColor(winner.getDurability());
//                        CoinFlipMatch.this.endCoinFlip(CoinFlipMatch.RemoveReason.WON, (CoinFlipMatch.this.getFirstColor() == CoinFlipMatch.this.winningColor) ? CoinFlipMatch.this.getStarterPlayer() : CoinFlipMatch.this.getSecondPlayer());
//                        UUID loser = (CoinFlipMatch.this.getFirstColor() == CoinFlipMatch.this.winningColor) ? CoinFlipMatch.this.getSecondPlayer().getUniqueId() : CoinFlipMatch.this.getStarterPlayer().getUniqueId();
///*
//                        CosmicCoinFlip.get().getCoinFlipManager().addLoss(loser, CoinFlipMatch.this.playerUUID.equals(loser) ? CoinFlipMatch.this.player2UUID : CoinFlipMatch.this.playerUUID, CoinFlipMatch.this.wager);
//*/
//                    }
//                    return;
//                }
//                this.ticks++;
//                boolean lastTick = (this.ticks == this.maxTicks);
//                if (!lastTick && this.ticks >= 20 && ((this.ticks >= 50 && this.ticks % 2 == 0) || (this.ticks >= 40 && this.ticks % 3 == 0) || (this.ticks >= 30 && this.ticks % 4 == 0)))
//                    return;
//                for (Player player : CoinFlipMatch.this.getPlayers()) {
//                    if (player == null || !player.isOnline()) {
//                        cancel();
//                        return;
//                    }
//                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.1F);
//                }
//                CoinColor color = !lastTick ? ((CoinFlipMatch.this.previousColor == CoinFlipMatch.this.getFirstColor()) ? CoinFlipMatch.this.getSecondColor() : CoinFlipMatch.this.getFirstColor()) : CoinFlipMatch.this.getWinnerColor();
//                String winnerName = (lastTick && color.equals(CoinFlipMatch.this.getFirstColor())) ? (CoinFlipMatch.this.getFirstColor().getChatColor() + ChatColor.BOLD.toString() + CoinFlipMatch.this.getStarterPlayer().getName()) : ((lastTick && color.equals(CoinFlipMatch.this.getSecondColor())) ? (CoinFlipMatch.this.getSecondColor().getChatColor() + ChatColor.BOLD.toString() + CoinFlipMatch.this.getSecondPlayer().getName()) : null);
//                CoinFlipMatch.this.previousColor = color;
//                CoinFlipMatch.this.coinFlipMenu.setItem(4, ItemBuilder.buildItem(Material.WOOL, color.getDyeColor().getWoolData(), (winnerName != null) ? winnerName : color.getName(), Lists.newArrayList((winnerName != null) ? (winnerName + ChatColor.GRAY + " has won the Coin Flip!") : (ChatColor.GRAY + "Rolling..."))));
//                for (Player pl : CoinFlipMatch.this.getPlayers()) {
//                    if (pl != null)
//                        pl.updateInventory();
//                }
//            }
//        }).runTaskTimer(EtheriaSkyblock.instance, 20L, 2L);
//    }
//
//    public CoinColor getWinnerColor() {
//        if (ThreadLocalRandom.current().nextBoolean())
//            return getFirstColor();
//        return getSecondColor();
//    }
//
//    public void endCoinFlip(RemoveReason reason, Player forceWinner) {
//        if (this.matchState == MatchState.DONE)
//            return;
//        this.matchState = MatchState.DONE;
//        CosmicCoinFlip.get().getCoinFlipManager().getCoinFlipMatches().remove(this);
//        if (reason == RemoveReason.LEAVE) {
//            Player winner = ThreadLocalRandom.current().nextBoolean() ? this.starterPlayer : this.secondPlayer;
//            if (winner != null) {
//                System.out.println("Winning " + winner.getName() + " due to RNG.");
//                givePrize(winner);
//            }
//            Player loser = (winner != null && winner.equals(this.starterPlayer)) ? this.secondPlayer : this.starterPlayer;
//            if (loser != null && winner != null)
//                CosmicCoinFlip.get().getCoinFlipManager().addLoss(loser.getUniqueId(), winner.getUniqueId(), getWager());
//        } else if (reason == RemoveReason.WON && forceWinner != null) {
//            for (Player player : getPlayers()) {
//                if (player != null)
//                    player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + forceWinner.getName() + " has won the Coin Flip with " + this.winningColor.getName() + ChatColor.GREEN + "!");
//            }
//            givePrize(forceWinner);
//        }
//    }
//
//    public CoinColor getCoinColor(Player player) {
//        if (this.starterPlayer.equals(player))
//            return getFirstColor();
//        return getSecondColor();
//    }
//
//    public void onAcceptMatch(Player player) {
//        this.secondPlayer = player;
//        this.player2Name = player.getName();
//        this.player2UUID = player.getUniqueId();
//        CosmicCoinFlip.getEconomy().withdrawPlayer((OfflinePlayer)player, this.wager);
//        buildFlipMenu();
//        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "- $" + ChatColor.RED + this.format.format(this.wager));
//        for (Player pl : getPlayers()) {
//            pl.playSound(pl.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.1F);
//            pl.sendMessage(ChatColor.RED + player.getName() + " has accepted the Coin Flip Wager!");
//            pl.openInventory(this.coinFlipMenu);
//        }
//        this.coinFlipMenu.setItem(0, getSkull(getStarterPlayer(), getSecondPlayer()));
//        this.coinFlipMenu.setItem(8, getSkull(getSecondPlayer(), getStarterPlayer()));
//    }
//
//    public void givePrize(Player player) {
//        if (this.gavePrize)
//            return;
//        this.gavePrize = true;
//        UUID loser = player.getUniqueId().equals(this.playerUUID) ? this.player2UUID : this.playerUUID;
//        double totalWines = this.wager * 2.0D;
//        double taxRate = TAX;
//        double win = totalWines * (1.0D - taxRate);
//        CoinFlipManager manager = CosmicCoinFlip.get().getCoinFlipManager();
//        manager.addWin(player.getUniqueId(), loser, win, taxRate * this.wager);
//        EconomyResponse response = CosmicCoinFlip.getEconomy().depositPlayer((OfflinePlayer)player, win);
//        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "+ $" + this.format.format(win));
//        if (win >= manager.getAlertAmount())
//            for (Player pl : PlayerUtil.getOnlinePlayers()) {
//                if (CosmicCoinFlip.get().getCoinFlipManager().getToggledNotifications().contains(pl.getUniqueId()))
//                    continue;
//                pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.BOLD + "** " +
//                        getColorChosen(player.getUniqueId()).getChatColor() + FactionUtils.getFactionTag(pl, player.getUniqueId()) + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " (" + manager.getWinLossRatio(player.getUniqueId(), "-") + ") has defeated " + ChatColor.RED +
//                        getColorChosen(loser).getChatColor() + FactionUtils.getFactionTag(pl, loser) + ChatColor.RED + (player.getName().equals(this.playerName) ? this.player2Name : this.playerName) + ChatColor.GRAY + " (" + manager
//                        .getWinLossRatio(loser, "-") + ") in a " + ChatColor.GRAY + ChatColor.UNDERLINE + "$" + this.format.format(totalWines) + ChatColor.GRAY + " /coinflip");
//            }
//        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "CF_WIN", new Object[] { Double.valueOf(win), Double.valueOf(this.wager), loser }));
//        Bukkit.getLogger().info("Giving " + player.getName() + " $" + win + " for beating " + (this.playerName.equals(player.getName()) ? this.player2Name : this.playerName) + " in a Coin Flip! (transactionSuccess: " + response.transactionSuccess() + ")");
//    }
//
//    public CoinColor getColorChosen(UUID uuid) {
//        if (this.playerUUID.equals(uuid))
//            return getFirstColor();
//        return getSecondColor();
//    }
//
//    public ArrayList<Player> getPlayers() {
//        return Lists.newArrayList(this.starterPlayer, this.secondPlayer);
//    }
//
//    public void onPlayerLogout(Player player) {
//        if (this.matchState == MatchState.FLIPPING || this.matchState == MatchState.STARTING) {
//            endCoinFlip(RemoveReason.LEAVE, null);
//        } else if (this.matchState == MatchState.WAITING && getSecondPlayer() == null) {
//            this.matchState = MatchState.DONE;
//            CosmicCoinFlip.getEconomy().depositPlayer((OfflinePlayer)player, this.wager);
//            Bukkit.getLogger().info("Returning wager of " + this.wager + " to " + player.getName());
//            CosmicCoinFlip.get().getCoinFlipManager().getCoinFlipMatches().remove(this);
//        }
//    }
//
//    public enum RemoveReason {
//        LEAVE, WON
//    }
//
//    public enum MatchState {
//        WAITING, STARTING, FLIPPING, DONE
//    }
//}
//
