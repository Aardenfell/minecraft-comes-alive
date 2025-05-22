package net.mca.entity.ai.chatAI.inworldAIModules;

import net.mca.entity.VillagerEntityMCA;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.BiConsumer;

// TODO: you probably want a record...
public class TriggerCommandInfo {
    public String command;
    public String description;
    public BiConsumer<ServerPlayerEntity, VillagerEntityMCA> call;

    public TriggerCommandInfo(String command, String description, BiConsumer<ServerPlayerEntity, VillagerEntityMCA> call) {
        this.command = command;
        this.description = description;
        this.call = call;
    }
}
