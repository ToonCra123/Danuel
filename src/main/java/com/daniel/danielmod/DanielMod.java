package com.daniel.danielmod;

import com.daniel.danielmod.blocks.StartUpClient;
import com.daniel.danielmod.blocks.StartUpCommon;
import com.daniel.danielmod.util.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.FurnaceBlock;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("danielmod")
public class DanielMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String mod_id = "danielmod";

    public static IEventBus MOD_EVENT_BUS;

    public DanielMod() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        RegistryHandler.init();

        registerCommonEvents();
        registerClientEvents();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static void registerCommonEvents() {
        MOD_EVENT_BUS.register(StartUpCommon.class);
    }
    public static void registerClientEvents() {
        MOD_EVENT_BUS.register(StartUpClient.class);
    }

    private void setup(final FMLCommonSetupEvent event) { }

    private void doClientStuff(final FMLClientSetupEvent event) { }

}
