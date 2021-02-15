package com.daniel.danielmod.blocks;

import com.daniel.danielmod.blocks.tile.BasicContainerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BasicContainerBlock extends ContainerBlock {

    public BasicContainerBlock() {
        super(Block.Properties.create(Material.ROCK));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BasicContainerTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    // Called when the block is right clicked
    // We use it to open the block gui when right clicked by a player
    // Copied from ChestBlock
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (worldIn.isRemote) return ActionResultType.SUCCESS; // on client side, don't do anything

        INamedContainerProvider namedContainerProvider = this.getContainer(state, worldIn, pos);
        if (namedContainerProvider != null) {
            if (!(player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;  // should always be true, but just in case...
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer)->{});
            // (packetBuffer)->{} is just a do-nothing because we have no extra data to send
        }
        return ActionResultType.SUCCESS;
    }

    // This is where you can do something when the block is broken. In this case drop the inventory's contents
    // Code is copied directly from vanilla eg ChestBlock, CampfireBlock
    public void onReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockPos);
            if (tileentity instanceof BasicContainerTileEntity) {
                BasicContainerTileEntity tileEntityInventoryBasic = (BasicContainerTileEntity)tileentity;
                tileEntityInventoryBasic.dropAllContents(world, blockPos);
            } // worldIn.updateComparatorOutputLevel(pos, this);  if the inventory is used to set redstone power for comparators
            super.onReplaced(state, world, blockPos, newState, isMoving);  // call it last, because it removes the TileEntity
        }
    }

    // ---------------------------
    // If you want your container to provide redstone power to a comparator based on its contents, implement these methods
    //  see vanilla for examples

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return 0;
    }

    //---------------------------------------------------------

    // render using a BakedModel (mbe30_inventory_basic.json --> mbe30_inventory_basic_model.json)
    // required because the default (super method) is INVISIBLE for BlockContainers.
    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    // returns the shape of the block:
    //  The image that you see on the screen (when a block is rendered) is determined by the block model (i.e. the model json file).
    //  But Minecraft also uses a number of other "shapes" to control the interaction of the block with its environment and with the player.
    // See  https://greyminecraftcoder.blogspot.com/2020/02/block-shapes-voxelshapes-1144.html
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return CHEST_SHAPE;
    }

    private static final Vector3d CHEST_MIN_CORNER = new Vector3d(0.0, 0.0, 0.0);
    private static final Vector3d CHEST_MAX_CORNER = new Vector3d(16.0, 16.0, 16.0);
    private static final VoxelShape CHEST_SHAPE = Block.makeCuboidShape(
            CHEST_MIN_CORNER.getX(), CHEST_MIN_CORNER.getY(), CHEST_MIN_CORNER.getZ(),
            CHEST_MAX_CORNER.getX(), CHEST_MAX_CORNER.getY(), CHEST_MAX_CORNER.getZ());

}
