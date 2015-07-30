package AE2Addon.block;

import AE2Addon.reference.Textures;
import AE2Addon.registry.CreativeTabRegistry;
import AE2Addon.tile.AdvancedInterfaceTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AdvancedInterfaceBlock extends BlockContainer
{
    public AdvancedInterfaceBlock()
    {
        super(Material.circuits);
        setCreativeTab(CreativeTabRegistry.AE2AddonTab);
        setBlockName("advancedInterface");
        setBlockTextureName(Textures.IIcon.advancedInterface);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new AdvancedInterfaceTileEntity();
    }
}
