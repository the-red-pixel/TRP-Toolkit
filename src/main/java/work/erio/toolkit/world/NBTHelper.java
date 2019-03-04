package work.erio.toolkit.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class NBTHelper {
    public static List<TileEntity> readTileEntitiesFromCompound(NBTTagCompound compound) {
        return readTileEntitiesFromCompound(compound, new ArrayList<TileEntity>());
    }

    public static List<TileEntity> readTileEntitiesFromCompound(NBTTagCompound compound, List<TileEntity> tileEntities) {
        NBTTagList tagList = compound.getTagList("TileEntities", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tileEntityCompound = tagList.getCompoundTagAt(i);
            TileEntity tileEntity = readTileEntityFromCompound(tileEntityCompound);
            tileEntities.add(tileEntity);
        }

        return tileEntities;
    }

    public static NBTTagCompound writeTileEntitiesToCompound(List<TileEntity> tileEntities) {
        return writeTileEntitiesToCompound(tileEntities, new NBTTagCompound());
    }

    public static NBTTagCompound writeTileEntitiesToCompound(List<TileEntity> tileEntities, NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (TileEntity tileEntity : tileEntities) {
            NBTTagCompound tileEntityCompound = writeTileEntityToCompound(tileEntity);
            tagList.appendTag(tileEntityCompound);
        }

        compound.setTag("TileEntities", tagList);

        return compound;
    }

    public static TileEntity reloadTileEntity(TileEntity tileEntity) throws NBTConversionException {
        return reloadTileEntity(tileEntity, 0, 0, 0);
    }

    public static TileEntity reloadTileEntity(TileEntity tileEntity, int offsetX, int offsetY, int offsetZ) throws NBTConversionException {
        if (tileEntity == null) {
            return null;
        }

        try {
            NBTTagCompound tileEntityCompound = writeTileEntityToCompound(tileEntity);
            tileEntity = readTileEntityFromCompound(tileEntityCompound);
            BlockPos pos = tileEntity.getPos();
            tileEntity.setPos(pos.add(-offsetX, -offsetY, -offsetZ));
        } catch (Throwable t) {
            throw new NBTConversionException(tileEntity, t);
        }

        return tileEntity;
    }

    public static NBTTagCompound writeTileEntityToCompound(TileEntity tileEntity) {
        NBTTagCompound tileEntityCompound = new NBTTagCompound();
        tileEntity.writeToNBT(tileEntityCompound);
        return tileEntityCompound;
    }

    public static TileEntity readTileEntityFromCompound(NBTTagCompound tileEntityCompound) {
        // TODO: world should NOT be null...
        return TileEntity.create(null, tileEntityCompound);
    }

    public static NBTTagCompound writeEntityToCompound(Entity entity) {
        NBTTagCompound entityCompound = new NBTTagCompound();
        if (entity.writeToNBTOptional(entityCompound)) {
            return entityCompound;
        }

        return null;
    }

    public static Entity readEntityFromCompound(NBTTagCompound nbtTagCompound, World world) {
        return EntityList.createEntityFromNBT(nbtTagCompound, world);
    }
}