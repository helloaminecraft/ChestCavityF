package net.tigereye.chestcavity.registration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.util.ChestCavityUtil;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ChestCavity.MODID)
public class CCCommands {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("chestcavity")
                .then(Commands.literal("getscores")
                        .executes(CCCommands::getScoresNoArgs)
                        .then(Commands.argument("entity", EntityArgument.entity())
                                .executes(CCCommands::getScores)))
        );
        dispatcher.register(Commands.literal("chestcavity")
                .then(Commands.literal("resetChestCavity").requires(source -> source.hasPermission(2))
                        .executes(CCCommands::resetChestCavityNoArgs)
                        .then(Commands.argument("entity", EntityArgument.entity())
                                .executes(CCCommands::resetChestCavity)))
        );
    }

    private static int getScoresNoArgs(CommandContext<CommandSource> context) {
        Entity entity;
        try {
            entity = context.getSource().getEntityOrException();
        }
        catch(Exception e){
            context.getSource().sendFailure(new StringTextComponent("getScores failed to get entity"));
            return -1;
        }
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(optional.isPresent()){
            ChestCavityUtil.outputOrganScoresString((string) -> {
                context.getSource().sendSuccess(new StringTextComponent(string),false);
            },optional.get().getChestCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int getScores(CommandContext<CommandSource> context) {
        Entity entity;
        try {
            entity = EntityArgument.getEntity(context, "entity");
        }
        catch(Exception e){
            context.getSource().sendFailure(new StringTextComponent("getScores failed to get entity"));
            return -1;
        }
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(optional.isPresent()){
            ChestCavityUtil.outputOrganScoresString((string) -> {
                context.getSource().sendSuccess(new StringTextComponent(string),false);
            },optional.get().getChestCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int resetChestCavityNoArgs(CommandContext<CommandSource> context) {
        Entity entity;
        try {
            entity = context.getSource().getEntityOrException();
        }
        catch(Exception e){
            context.getSource().sendFailure(new StringTextComponent("resetChestCavity failed to get entity"));
            return -1;
        }
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(optional.isPresent()){
            ChestCavityUtil.generateChestCavityIfOpened(optional.get().getChestCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int resetChestCavity(CommandContext<CommandSource> context) {
        Entity entity;
        try {
            entity = EntityArgument.getEntity(context, "entity");
        }
        catch(Exception e){
            context.getSource().sendFailure(new StringTextComponent("getChestCavity failed to get entity"));
            return -1;
        }
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(optional.isPresent()){
            ChestCavityUtil.generateChestCavityIfOpened(optional.get().getChestCavityInstance());
            return 1;
        }
        return 0;
    }

}