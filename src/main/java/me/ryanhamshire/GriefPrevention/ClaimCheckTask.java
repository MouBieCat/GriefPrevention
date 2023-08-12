package me.ryanhamshire.GriefPrevention;

import me.ryanhamshire.GriefPrevention.events.PlayerJoinClaimEvent;
import me.ryanhamshire.GriefPrevention.events.PlayerLeaveClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class ClaimCheckTask extends BukkitRunnable
{
    private final GriefPrevention plugin;
    private final Map<Player, Claim> playerInClaim = new HashMap<>();

    /**
     * ClaimCheckTimer 建構子
     * private 修飾符，防止外部類別實例化
     */
    public ClaimCheckTask(GriefPrevention plugin)
    {
        this.plugin = plugin;
    }

    /**
     * 開始定時檢查玩家是否在領地內
     */
    public synchronized void start()
    {
        try
        {
            this.runTaskTimer(this.plugin, 0L, 20L);
        }
        catch (final IllegalStateException ignored)
        {
        }
    }

    /**
     * 停止定時檢查玩家是否在領地內
     */
    @Override
    public synchronized void cancel()
    {
        if (this.isCancelled()) return;
        // 關閉定時器
        super.cancel();
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        // 開始檢查玩家是否在領地內
        final DataStore dataStore = GriefPrevention.instance.dataStore;
        Bukkit.getOnlinePlayers().forEach(player -> {
            // 取得玩家所在領地
            final Claim claimAt = dataStore.getClaimAt(player.getLocation(), true, null);

            // 如果 claimAt 不為 null，代表玩家在領地內
            if (claimAt != null)
            {
                // 如果 inClaimPlayers 不包含玩家，代表玩家剛進入領地
                if (!this.playerInClaim.containsKey(player))
                {
                    // 呼叫 PlayerJoinClaimEvent 事件
                    Bukkit.getPluginManager().callEvent(new PlayerJoinClaimEvent(player, claimAt));
                    this.playerInClaim.put(player, claimAt);
                }
            }
            else
            {
                // 如果 inClaimPlayers 包含玩家，代表玩家剛離開領地
                if (this.playerInClaim.containsKey(player))
                {
                    // 取得玩家先前進入的領地，並呼叫離開事件
                    Bukkit.getPluginManager().callEvent(new PlayerLeaveClaimEvent(player, this.playerInClaim.get(player)));
                    this.playerInClaim.remove(player);
                }
            }
        });

        // 清除不在線上的玩家
        this.clearInvalidPlayer();
    }

    /**
     * 清除不在線上的玩家
     */
    private void clearInvalidPlayer()
    {
        for (final Player player : this.playerInClaim.keySet())
        {
            if (player == null || !player.isOnline())
            {
                this.playerInClaim.remove(player);
            }
        }
    }
}
