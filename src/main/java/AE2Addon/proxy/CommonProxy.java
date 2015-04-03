package AE2Addon.proxy;

import AE2Addon.registry.BlockRegistry;
import AE2Addon.tile.AdvancedInterfaceTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void registerTileEntitys()
    {
        GameRegistry.registerTileEntity(AdvancedInterfaceTileEntity.class, "AdvancedInterfaceTileEntity");
    }

    public void registerBlocks()
    {
        BlockRegistry.init();
        BlockRegistry.register();
    }
}
