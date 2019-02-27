package work.erio.toolkit.module;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import work.erio.toolkit.util.TextUtils;

public class ModuleBlockData extends AbstractModule implements IModule {
    public ModuleBlockData() {
        super(true);
    }

    @Override
    public String getTitle() {
        return "BlockData";
    }

    @Override
    public void onUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    }

    @Override
    public void onServerUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null) {
            NBTTagCompound nbttagcompound = te.writeToNBT(new NBTTagCompound());
            te.readFromNBT(nbttagcompound);
            te.markDirty();
            TextUtils.printMessage(player, nbttagcompound.toString(), TextFormatting.GREEN);
        }
    }
}
