package com.RJGY.chatfind.events;

import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ModClientEvents {
    private ArrayList<String> stringRegexList = new ArrayList<>();
    private ArrayList<String> messageList = new ArrayList<>();
    private static final Logger log = LogManager.getLogger();



    public void loadRegex() throws IOException {
        File configFile = new File(Loader.instance().getConfigDir(), "chatfind.cfg");
        Configuration config = new Configuration(configFile);
        config.load();
        Property update = config.get("ChatFind", "regex", "hello");
        stringRegexList.addAll(Arrays.asList(update.getString().split(",")));
        log.info("Loaded ModClientEvents");
    }

    @SubscribeEvent
    public void clearMessageList(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_P))
            messageList.clear();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onChatEvent(ServerChatEvent event) {
        for (String regex : stringRegexList) {
            if (event.message.contains(regex)) {
                messageList.add(event.username + ": " + event.message);
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRender(TickEvent.RenderTickEvent event) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        int counter = 0;
        for (String message : messageList) {
            fontRenderer.drawString(EnumChatFormatting.WHITE + message, 5, counter + 5, 0);
            counter += 10;
        }
    }
}
