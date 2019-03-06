/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2019. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.common;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public abstract class TileGeneric extends BlockEntity implements BlockEntityClientSerializable
{
    public TileGeneric( BlockEntityType<? extends TileGeneric> type )
    {
        super( type );
    }

    public void destroy()
    {
    }

    public final void updateBlock()
    {
        markDirty();
        BlockPos pos = getPos();
        BlockState state = getCachedState();
        getWorld().scheduleBlockRender( pos );
        getWorld().updateListeners( pos, state, state, 3 );
    }

    public boolean onActivate( PlayerEntity player, Hand hand, BlockHitResult hit )
    {
        return false;
    }

    public void onNeighbourChange( @Nonnull BlockPos neighbour )
    {
    }

    public void onNeighbourTileEntityChange( @Nonnull BlockPos neighbour )
    {
    }

    protected void blockTick()
    {
    }

    protected double getInteractRange( PlayerEntity player )
    {
        return 8.0;
    }

    public boolean isUsable( PlayerEntity player, boolean ignoreRange )
    {
        if( player == null || !player.isValid() || getWorld().getBlockEntity( getPos() ) != this ) return false;
        if( ignoreRange ) return true;

        double range = getInteractRange( player );
        BlockPos pos = getPos();
        return player.getEntityWorld() == getWorld() &&
            player.squaredDistanceTo( pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5 ) <= range * range;
    }

    protected void writeDescription( @Nonnull CompoundTag nbt )
    {
    }

    protected void readDescription( @Nonnull CompoundTag nbt )
    {
    }

    @Override
    public final CompoundTag toClientTag( CompoundTag tag )
    {
        writeDescription( tag );
        return tag;
    }

    @Override
    public final void fromClientTag( CompoundTag tag )
    {
        readDescription( tag );
    }
}
