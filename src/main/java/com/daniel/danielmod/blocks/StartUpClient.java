package com.daniel.danielmod.blocks;

import com.daniel.danielmod.blocks.tile.Containers.Screens.BasicContainerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartUpClient {
        // register the factory that is used on the client to generate a ContainerScreen corresponding to our Container
        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            ScreenManager.registerFactory(StartUpCommon.containerTypeContainerBasic, BasicContainerScreen::new);
        }
}
