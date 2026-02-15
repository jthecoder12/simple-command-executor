package jthecoder12.simplecommandexecutor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class CommandExecutionScreen extends Screen {
    CommandExecutionScreen() {
        super(Component.literal("Execute Command"));
    }

    @Override
    protected void init() {
        EditBox commandBox = new EditBox(Minecraft.getInstance().font, 400, 20, Component.literal("Command to run"));
        commandBox.setPosition(width / 2 - commandBox.getWidth() / 2, height / 6);

        Button executeButton = Button.builder(Component.literal("Execute"), _ -> SimpleCommandExecutor.commandToExecute = commandBox.getValue()).bounds(width / 2 - 200 / 2, height / 3, 200, 20).build();

        addRenderableWidget(commandBox);
        addRenderableWidget(executeButton);
    }
}
