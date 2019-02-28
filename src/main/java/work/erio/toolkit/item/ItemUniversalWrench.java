package work.erio.toolkit.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.module.ModuleManager;
import work.erio.toolkit.util.TextUtils;

public class ItemUniversalWrench extends Item {

    private ModuleManager moduleManager;

    public ItemUniversalWrench() {
        super();
        setCreativeTab(Toolkit.TRP_TOOLKIT);
        setMaxStackSize(1);
        setUnlocalizedName(Toolkit.MODID + ".universal_wrench");
        setRegistryName("universal_wrench");
        this.moduleManager = ModuleManager.getInstance();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (moduleManager.getCurrentModule() == null) {
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        if (worldIn.isRemote) {
            moduleManager.getCurrentModule().onUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return EnumActionResult.SUCCESS;
        } else {
            moduleManager.getCurrentModule().onServerUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return EnumActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        if (playerIn.isSneaking()) {
            moduleManager.prevModule();
        } else {
            moduleManager.nextModule();
        }
        if (moduleManager.getCurrentModule() != null) {
            TextUtils.printMessage(playerIn, String.format("Current module: %s", moduleManager.getCurrentModule().getTitle()), TextFormatting.WHITE);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
