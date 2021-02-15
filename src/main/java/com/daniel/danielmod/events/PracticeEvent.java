package com.daniel.danielmod.events;

import com.daniel.danielmod.DanielMod;
import com.daniel.danielmod.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = DanielMod.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PracticeEvent {

    @SubscribeEvent
    public static void functionToDoStuff(AttackEntityEvent event) {
        if (event.getEntityLiving().getHeldItemMainhand().getItem() == RegistryHandler.A_BLOCK_ITEM.get()) {
            if (event.getTarget().isAlive()) {
                LivingEntity target = (LivingEntity) event.getTarget();
                target.addPotionEffect(new EffectInstance(Effects.POISON, 10*20));
            }
        }

    }
    @SubscribeEvent
    public static void spawnAnvilOnHeadSheep(AttackEntityEvent event) {
        LivingEntity target = (LivingEntity) event.getTarget();
        LivingEntity player = event.getEntityLiving();
        if (target instanceof SheepEntity) {
            World world = player.getEntityWorld();
            world.setBlockState(player.getPosition().add(0, 10, 0), Blocks.ANVIL.getDefaultState());
        }
    }
    @SubscribeEvent
    public static void validateMultiblock(PlayerInteractEvent.RightClickBlock event) {
        LivingEntity player = event.getEntityLiving();
        World world = player.getEntityWorld();
        BlockPos blockPos = event.getHitVec().getPos();



        if(player.getHeldItemMainhand().getItem() == RegistryHandler.STICKY.get()) {
            if(world.getBlockState(blockPos).getBlock() == RegistryHandler.A_BLOCK.get()) {
                if(world.getBlockState(blockPos.add(-1,0,0)).getBlock() == Blocks.IRON_BLOCK || world.getBlockState(blockPos.add(1,0,0)).getBlock() == Blocks.IRON_BLOCK || world.getBlockState(blockPos.add(0,0,-1)).getBlock() == Blocks.IRON_BLOCK || world.getBlockState(blockPos.add(0,0,-1)).getBlock() == Blocks.IRON_BLOCK ) {
                    world.setBlockState(blockPos.add(0, -1, 0), Blocks.IRON_BLOCK.getDefaultState());
                }
            }
        }

    }

}
