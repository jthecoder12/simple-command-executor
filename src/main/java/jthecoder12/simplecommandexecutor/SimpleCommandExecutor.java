package jthecoder12.simplecommandexecutor;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class SimpleCommandExecutor implements ClientModInitializer {
	public static final String MOD_ID = "simple-command-executor";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static KeyMapping commandKeyMapping;
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("simplecommandexecutor", "keys"));

    public static String commandToExecute = null;

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

        commandKeyMapping = KeyMappingHelper.registerKeyMapping(new KeyMapping("jthecoder12.simplecommandexecutor.commandkey", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(_ -> {
            while(commandKeyMapping.consumeClick()) {
                Minecraft.getInstance().setScreen(new CommandExecutionScreen());
            }

            if(commandToExecute != null) {
                assert Minecraft.getInstance().player != null;
                if(commandToExecute.startsWith("say") || commandToExecute.startsWith("tellraw")) Minecraft.getInstance().player.sendSystemMessage(Component.literal("You cannot use the say/tellraw command in Simple Command Executor, use the chat instead"));
                else Minecraft.getInstance().player.connection.sendCommand(commandToExecute);
                commandToExecute = null;
            }
        });
	}
}