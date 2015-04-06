package AE2Addon.tile;

import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.GridAccessException;
import appeng.tile.grid.AENetworkTile;
import appeng.util.item.AEItemStack;
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
            int size = gridProxy.getStorage().getItemInventory().getStorageList().size();
            return size > 0 ? size : 1;
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
            for (int i = 0; itr.hasNext() && i < slot; i++)
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
        try
        {
            Iterator<IAEItemStack> itr = gridProxy.getStorage().getItemInventory().getStorageList().iterator();
            for (int i = 0; itr.hasNext() && i < slot; i++)
                itr.next();
            ItemStack theStack = itr.next().getItemStack();
            ItemStack newStack = theStack.copy();
            newStack.stackSize = Math.min(amount, theStack.stackSize);
            this.gridProxy.getStorage().getItemInventory().extractItems(AEItemStack.create(newStack), Actionable.MODULATE, new MachineSource(this));
            return newStack;
        } catch (GridAccessException e)
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        try
        {
            gridProxy.getStorage().getItemInventory().injectItems(AEItemStack.create(stack), Actionable.MODULATE, new MachineSource(this));
        } catch (GridAccessException e)
        {
            // can't access grid this should never happen since it is dealt with the isItemValidForSlot function
        }
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
    public boolean isUseableByPlayer(EntityPlayer player)
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
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        try
        {
            return this.gridProxy.getStorage().getItemInventory().canAccept(AEItemStack.create(stack));
        } catch (GridAccessException e)
        {
            return false;
        }
    }
}
