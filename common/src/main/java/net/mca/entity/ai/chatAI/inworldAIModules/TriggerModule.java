package net.mca.entity.ai.chatAI.inworldAIModules;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.mca.entity.VillagerEntityMCA;
import net.mca.entity.ai.MoveState;
import net.mca.entity.ai.chatAI.inworldAIModules.api.Interaction;
import net.mca.entity.ai.chatAI.inworldAIModules.api.TriggerEvent;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Class to manage command triggers (wear armor, follow me, etc.)
 */
public class TriggerModule {

    public static final List<TriggerCommandInfo> triggerCommands = ImmutableList.of(
            new TriggerCommandInfo("follow-player", "Follow the player talking to you", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.FOLLOW, p)),
            new TriggerCommandInfo("stay-here", "Stay put", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.STAY, p)),
            new TriggerCommandInfo("move-freely", "Move freely", (p, v) -> v.getVillagerBrain().setMoveState(MoveState.MOVE, p)),
            new TriggerCommandInfo("wear-armor", "Equip any armor you have", (p, v) -> v.getVillagerBrain().setArmorWear(true)),
            new TriggerCommandInfo("remove-armor", "Remove all the armor currently equipped", (p, v) -> v.getVillagerBrain().setArmorWear(false)),
            new TriggerCommandInfo("try-go-home", "Try to go to your home in the village if possible", (p, v) -> v.getResidency().goHome(p)),
            new TriggerCommandInfo("open-trade-window", "Lets the player trade with you", (p, v) -> v.tryBeginTradeWith(p))
    );

    /** Map for trigger name => actions */
    private static final Map<String, BiConsumer<ServerPlayerEntity, VillagerEntityMCA>> triggerActions =
            triggerCommands.stream().collect(Collectors.toMap(
                    i -> i.command,
                    i -> i.call
            ));

    public static Optional<TriggerCommandInfo> findCommand(String command) {
        for (TriggerCommandInfo commandInfo : triggerCommands) {
            if (command.equals(commandInfo.command)) {
                return Optional.of(commandInfo);
            }
        }
        return Optional.empty();
    }

    /**
     * Looks for outgoing triggers from the last interaction
     * and executes {@link #triggerActions specific actions} associated with different trigger names
     * @param interaction Interaction object from a SendText request
     * @param player Player in the conversation
     * @param villager Villager in the conversation
     */
    public void processTriggers(Interaction interaction, ServerPlayerEntity player, VillagerEntityMCA villager) {
        // Get triggers sent from server
        TriggerEvent[] triggerEvents = interaction.outgoingTriggers();
        for (TriggerEvent event : triggerEvents) {
            // Get the action for the trigger
            BiConsumer<ServerPlayerEntity, VillagerEntityMCA> action = triggerActions.get(event.trigger());

            // Execute the action if it exists
            if (action != null) {
                action.accept(player, villager);
            }
        }
    }

}
