package work.erio.toolkit.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.tile.TileEntityRemoteSwitch;
import work.erio.toolkit.util.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public class ItemRemoteController extends Item {
    public ItemRemoteController() {
        setRegistryName("remote_controller_item");
        setUnlocalizedName(Toolkit.MODID + ".remote_controller_item");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //TODO
        if (!(worldIn.getTileEntity(pos) instanceof TileEntityRemoteSwitch)) {
            return EnumActionResult.FAIL;
        }
        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound root = new NBTTagCompound();
        if (stack.hasTagCompound()) {
            root = stack.getTagCompound();
        }
        if (root.hasKey("posList")) {
            NBTTagList posListTag = root.getTagList("posList", Constants.NBT.TAG_COMPOUND);
            NBTTagCompound posCompound = new NBTTagCompound();
            posCompound.setInteger("x", pos.getX());
            posCompound.setInteger("y", pos.getY());
            posCompound.setInteger("z", pos.getZ());
            List<NBTBase> posList = new ArrayList<>();
            posListTag.iterator().forEachRemaining(posList::add);
            boolean existed = posList.stream().map(n -> (NBTTagCompound) n).anyMatch(posCompound::equals);
            if (existed) {
                TextUtils.printTranslation(player, "remote_existed", TextFormatting.GRAY);
                return EnumActionResult.PASS;
            } else {
                posListTag.appendTag(posCompound);
                stack.getTagCompound().setTag("posList", posListTag);
            }
        } else  {
            NBTTagList posListTag = new NBTTagList();
            NBTTagCompound posCompound = new NBTTagCompound();
            posCompound.setInteger("x", pos.getX());
            posCompound.setInteger("y", pos.getY());
            posCompound.setInteger("z", pos.getZ());
            posListTag.appendTag(posCompound);
            root.setTag("posList", posListTag);
            stack.setTagCompound(root);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        System.out.println(playerIn.getHeldItem(handIn).getTagCompound());
        ItemStack stack = playerIn.getHeldItem(handIn);
        NBTTagCompound root = playerIn.getHeldItem(handIn).getTagCompound();
        if (root.hasKey("posList")) {
            NBTTagList posListTag = root.getTagList("posList", Constants.NBT.TAG_COMPOUND);
            if (!posListTag.hasNoTags()) {
                NBTTagCompound posCompound = posListTag.getCompoundTagAt(0);
                BlockPos pos = new BlockPos(posCompound.getInteger("x"), posCompound.getInteger("y"), posCompound.getInteger("z"));
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof TileEntityRemoteSwitch) {
                    TileEntityRemoteSwitch te = (TileEntityRemoteSwitch) tileEntity;

                    te.setPowered(!te.isPowered());
                    System.out.println(te.isPowered());
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


}
