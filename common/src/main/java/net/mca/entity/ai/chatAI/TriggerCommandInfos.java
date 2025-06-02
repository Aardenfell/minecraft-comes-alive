package net.mca.entity.ai.chatAI;

import com.google.common.collect.ImmutableList;
import net.mca.entity.VillagerEntityMCA;
import net.mca.entity.ai.MoveState;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Optional;

public class TriggerCommandInfos {
    public static final List<TriggerCommandInfo> triggerCommands = ImmutableList.of(
            new TriggerCommandInfo("follow-player", "Follow the player talking to you", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.FOLLOW, p)),
            new TriggerCommandInfo("stay-here", "Stay put", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.STAY, p)),
            new TriggerCommandInfo("move-freely", "Move freely", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.MOVE, p)),
            new TriggerCommandInfo("wear-armor", "Equip any armor you have", (p, v) -> v.getVillagerBrain().setArmorWear(true)),
            new TriggerCommandInfo("remove-armor", "Remove all the armor currently equipped", (p, v) -> v.getVillagerBrain().setArmorWear(false)),
            new TriggerCommandInfo("try-go-home", "Try to go to your home in the village if possible", (p, v) -> v.getResidency().goHome(p)),
            new TriggerCommandInfo("open-trade-window", "Lets the player trade with you. Open whenever the player is interested in trading, wants to check your prices or your inventory.", (p, v) -> v.beginTradeWith(p), (p, v) -> v.hasTradeOffers())
    );

    public static Optional<TriggerCommandInfo> findCommand(String command, ServerPlayerEntity player, VillagerEntityMCA villagerEntityMCA) {
        for (TriggerCommandInfo commandInfo : TriggerCommandInfos.triggerCommands) {
            if (command.equals(commandInfo.command)) {
                if (commandInfo.isActive != null && !commandInfo.isActive.test(player, villagerEntityMCA)) {
                    // not active now
                    continue;
                }
                return Optional.of(commandInfo);
            }
        }
        return Optional.empty();
    }
}
