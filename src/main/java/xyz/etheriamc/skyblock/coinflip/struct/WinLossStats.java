package xyz.etheriamc.skyblock.coinflip.struct;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WinLossStats {
    private int wins;

    private int dailyWins;

    private long winnings;

    private long dailyWinnings;

    private int losses;

    private int dailyLosses;

    long losings;

    long dailyLosings;

    long taxesPaid;

    public WinLossStats(int wins, int dailyWins, long winnings, long dailyWinnings, int losses, int dailyLosses, long losings, long dailyLosings, long taxesPaid) {
        this.winnings = 0L;
        this.losings = 0L;
        this.wins = wins;
        this.dailyWins = dailyWins;
        this.winnings = winnings;
        this.dailyWinnings = dailyWinnings;
        this.losses = losses;
        this.dailyLosses = dailyLosses;
        this.losings = losings;
        this.dailyLosings = dailyLosings;
        this.taxesPaid = taxesPaid;
    }

    public WinLossStats() {
        this(0, 0, 0L, 0L, 0, 0, 0L, 0L, 0L);
    }

    public void clearDailyStats() {
        this.dailyLosings = 0L;
        this.dailyWinnings = 0L;
        this.dailyWins = 0;
        this.dailyLosses = 0;
    }
}

