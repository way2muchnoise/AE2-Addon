package AE2Addon.tile;

import AE2Addon.registry.BlockRegistry;
import AE2Addon.util.AEHelper;
import appeng.api.AEApi;
import appeng.api.networking.*;
import appeng.api.networking.security.IActionHost;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import appeng.me.GridAccessException;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.Iterator;

public class AdvancedInterfaceTileEntity extends TileEntity implements IInventory, IGridHost, IActionHost
{

    private class GridBlock implements IGridBlock
    {
        @Override
        public double getIdlePowerUsage()
        {
            return 10;
        }

        @Override
        public EnumSet<GridFlags> getFlags()
        {
            return EnumSet.of(GridFlags.REQUIRE_CHANNEL);
        }

        @Override
        public boolean isWorldAccessible()
        {
            return true;
        }

        @Override
        public DimensionalCoord getLocation()
        {
            return new DimensionalCoord(AdvancedInterfaceTileEntity.this);
        }

        @Override
        public AEColor getGridColor()
        {
            return AEColor.Transparent;
        }

        @Override
        public void onGridNotification(GridNotification gridNotification)
        {

        }

        @Override
        public void setNetworkStatus(IGrid iGrid, int i)
        {

        }

        @Override
        public EnumSet<ForgeDirection> getConnectableSides()
        {
            return EnumSet.allOf(ForgeDirection.class);
        }

        @Override
        public IGridHost getMachine()
        {
            return AdvancedInterfaceTileEntity.this;
        }

        @Override
        public void gridChanged()
        {

        }

        @Override
        public ItemStack getMachineRepresentation()
        {
            return new ItemStack(BlockRegistry.advancedInterfaceBlock);
        }
    }

    private GridBlock gridBlock;
    private IGridNode gridNode;
    private boolean isReady;

    public AdvancedInterfaceTileEntity()
    {
        gridBlock = new GridBlock();
    }

    public IGridNode getNode()
    {
        if( this.gridNode == null && FMLCommonHandler.instance().getEffectiveSide().isServer() && this.isReady)
        {
            this.gridNode = AEApi.instance().createGridNode(this.gridBlock);
            this.gridNode.updateState();
        }

        return this.gridNode;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.isReady = true;
        getNode();
    }

    @Override
    public boolean canUpdate()
    {
        return !this.isReady;
    }

    @Override
    public int getSizeInventory()
    {
        try
        {
            int size = AEHelper.getStorage(getNode()).getItemInventory().getStorageList().size();
            return size > 0 ? size : 1;
        } catch (GridAccessException e)
        {
            return 0;
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
            Iterator<IAEItemStack> itr = AEHelper.getItrItems(getNode());
            if (itr == null) return null;
            for (int i = 0; itr.hasNext() && i < slot; i++)
                itr.next();
            return itr.next().getItemStack();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
            Iterator<IAEItemStack> itr = AEHelper.getItrItems(getNode());
            if (itr == null) return null;
            for (int i = 0; itr.hasNext() && i < slot; i++)
                itr.next();
            IAEItemStack stack = AEHelper.extract(getNode(), itr.next(), this);
            return stack == null ? null : stack.getItemStack();
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        AEHelper.insert(getNode(), stack, this, false);
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
        return AEHelper.canInsert(getNode(), stack);
    }

    @Override
    public IGridNode getActionableNode()
    {
        return getNode();
    }

    @Override
    public IGridNode getGridNode(ForgeDirection dir)
    {
        return getNode();
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection dir)
    {
        return AECableType.SMART;
    }

    @Override
    public void securityBreak()
    {
        this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, true);
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        if (this.gridNode != null)
        {
            this.gridNode.destroy();
            this.gridNode = null;
        }
    }

    @Override
    public void onChunkUnload()
    {
        super.onChunkUnload();
        if (this.gridNode != null)
        {
            this.gridNode.destroy();
            this.gridNode = null;
        }
    }
}
