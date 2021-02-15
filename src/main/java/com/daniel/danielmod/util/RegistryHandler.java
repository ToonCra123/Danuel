package com.daniel.danielmod.util;

import com.daniel.danielmod.DanielMod;
import com.daniel.danielmod.blocks.ABlock;
import com.daniel.danielmod.blocks.BlockItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    public static void init()  {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Items Locked and Loaded");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DanielMod.mod_id);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DanielMod.mod_id);

    //Blocks
    public static final RegistryObject<Block> A_BLOCK = BLOCKS.register("a_block", ABlock::new);

    //Block Items
    public static final RegistryObject<Item> A_BLOCK_ITEM = ITEMS.register("a_block", () -> new BlockItemBase(A_BLOCK.get()));

    //Items
    public static final RegistryObject<Item> STICKY = ITEMS.register("sticky", () -> new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

}
