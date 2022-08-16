package com.RJGY.chatfind.events;

import javafx.util.Pair;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
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
import java.util.HashMap;
import java.util.Map;

public class ModClientEvents {
    private ArrayList<String> stringRegexList = new ArrayList<>();
    private HashMap<Long, String> dictionary = new HashMap<>();
    private static final Logger log = LogManager.getLogger();
    private int delay = 0;

    public void loadRegex() {
        File configFile = new File(Loader.instance().getConfigDir(), "chatfind.cfg");
        Configuration config = new Configuration(configFile);
        config.load();
        Property update = config.get("ChatFind", "regex", "hello");
        Property delayTime = config.get("ChatFind", "delay", "5000");
        config.save();
        stringRegexList.addAll(Arrays.asList(update.getString().split(",")));
        delay = delayTime.getInt();
    }

    @SubscribeEvent
    public void clearMessageList(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_P))
            dictionary.clear();
    }

    @SubscribeEvent
    public void onExit(FMLServerStoppingEvent event) {
        dictionary.clear();
    }

    @SubscribeEvent
    public void reloadConfig(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_O))
            loadRegex();
    }

    @SubscribeEvent
    public void onChatEvent(ClientChatReceivedEvent event) {
        for (String regex : stringRegexList) {
            if (event.message.toString().contains(regex)) {
                dictionary.put(System.currentTimeMillis() + delay, event.message.getUnformattedText());
                event.setCanceled(true);
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRender(TickEvent.RenderTickEvent event) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        int counter = 0;
        dictionary.entrySet().removeIf(
                entry -> (System.currentTimeMillis() > entry.getKey())
        );
        Long[] array = dictionary.keySet().toArray(new Long[dictionary.size()]);
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            fontRenderer.drawString(EnumChatFormatting.WHITE + dictionary.get(array[i]), 5, counter + 5, 0);
            counter += 10;
        }
    }
}
