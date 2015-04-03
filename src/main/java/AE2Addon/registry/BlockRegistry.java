package AE2Addon.registry;

import AE2Addon.block.AdvancedInterfaceBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry
{
    public static AdvancedInterfaceBlock advancedInterfaceBlock;

    public static void init()
    {
        advancedInterfaceBlock = new AdvancedInterfaceBlock();
    }

    public static void register()
    {
        GameRegistry.registerBlock(advancedInterfaceBlock, advancedInterfaceBlock.getUnlocalizedName());
        CreativeTabRegistry.AE2AddonTab.bindIcon(advancedInterfaceBlock);
    }
}
