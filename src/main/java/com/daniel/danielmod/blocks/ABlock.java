package com.daniel.danielmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ABlock extends Block {
    public ABlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE));
    }
}
