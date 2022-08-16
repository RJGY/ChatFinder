package com.RJGY.chatfind;

import com.RJGY.chatfind.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = ChatFind.MOD_ID, version = ChatFind.VERSION, name = ChatFind.NAME, clientSideOnly = true)
public class ChatFind {
    public static final String MOD_ID = "chatfind";
    public static final String VERSION = "1.0";
    public static final String NAME = "ChatFind";
    public static final String SERVER_PROXY_CLASS = "com.RJGY.chatfind.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "com.RJGY.chatfind.proxy.ClientProxy";

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        proxy.registerRenders();
        proxy.registerEvents();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
