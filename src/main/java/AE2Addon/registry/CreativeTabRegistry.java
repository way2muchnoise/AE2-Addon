package AE2Addon.registry;

import AE2Addon.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabRegistry
{
    public static CreativeTab AE2AddonTab = new CreativeTab(Reference.ID);

    public static class CreativeTab extends CreativeTabs
    {
        private ItemStack displayStack;

        public CreativeTab(String label)
        {
            super(label);
        }

        public void bindIcon(Block block)
        {
            this.displayStack = new ItemStack(block);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return this.displayStack == null ?  new ItemStack(Blocks.diamond_block) : displayStack;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem()
        {
            return getIconItemStack().getItem();
        }
    }
}
