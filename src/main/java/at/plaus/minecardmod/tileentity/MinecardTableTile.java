package at.plaus.minecardmod.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MinecardTableTile extends TileEntity {


    public MinecardTableTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MinecardTableTile() {
        this(ModTileEntities.minecard_TABLE_TILE.get());
    }

}
