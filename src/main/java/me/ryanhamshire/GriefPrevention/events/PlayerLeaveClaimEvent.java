package me.ryanhamshire.GriefPrevention.events;

import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerLeaveClaimEvent extends PlayerEvent
{
    private static final HandlerList handlers = new HandlerList();

    private final Claim claim;

    public PlayerLeaveClaimEvent(@NotNull Player who, @NotNull Claim claim) {
        super(who);
        this.claim = claim;
    }

    /**
     * 取得玩家加入的領地
     *
     * @return 玩家加入的領地
     */
    public @NotNull Claim getClaim() {
        return claim;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
