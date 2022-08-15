package com.RJGY.chatfind.proxy;

import com.RJGY.chatfind.events.ModClientEvents;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {

    }

    @Override
    public void registerEvents() throws IOException {
        super.registerEvents();
        ModClientEvents chatregex = new ModClientEvents();
        MinecraftForge.EVENT_BUS.register(chatregex);
        chatregex.loadRegex();
    }
}
