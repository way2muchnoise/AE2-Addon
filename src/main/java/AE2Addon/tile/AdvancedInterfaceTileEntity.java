package AE2Addon.tile;

import appeng.api.networking.GridFlags;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.GridAccessException;
import appeng.tile.grid.AENetworkTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

public class AdvancedInterfaceTileEntity extends AENetworkTile implements IInventory
{
    public AdvancedInterfaceTileEntity()
    {
        this.gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
    }

    @Override
    public int getSizeInventory()
    {
        try
        {
            return gridProxy.getStorage().getItemInventory().getStorageList().size();
        } catch (GridAccessException e)
        {
            return 0;
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        try
        {
            Iterator<IAEItemStack> itr = gridProxy.getStorage().getItemInventory().getStorageList().iterator();
            for (int i = 0; i < slot; i++)
                itr.next();
            return itr.next().getItemStack();
        } catch (GridAccessException e)
        {
            return null;
        } catch (NullPointerException e)
        {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {

    }

    @Override
    public String getInventoryName()
    {
        return "advancedInterface";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return false;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return false;
    }
}
