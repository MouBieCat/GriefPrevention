package me.ryanhamshire.GriefPrevention.events;

import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClaimCreatedSuccessEvent extends ClaimEvent
{
    private final @Nullable CommandSender creator;

    /**
     * Construct a new {@code ClaimCreatedEvent}.
     *
     * @param claim the {@link Claim} being created
     * @param creator the {@link CommandSender} causing creation
     */
    public ClaimCreatedSuccessEvent(@NotNull Claim claim, @Nullable CommandSender creator)
    {
        super(claim);
        this.creator = creator;
    }

    /**
     * Get the {@link CommandSender} creating the {@link Claim}.
     *
     * @return the actor causing creation
     */
    public @Nullable CommandSender getCreator()
    {
        return creator;
    }

    // Listenable event requirements
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers()
    {
        return HANDLERS;
    }
}
