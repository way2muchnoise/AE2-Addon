package AE2Addon.tile;

import AE2Addon.ae.IMultiChannelGridBlock;
import AE2Addon.ae.IMultiChannelGridNode;
import AE2Addon.ae.MultiChannelGridNode;
import AE2Addon.registry.BlockRegistry;
import appeng.api.networking.*;
import appeng.api.networking.security.IActionHost;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;

public class AdvancedInterfaceTileEntity extends TileEntity implements IGridHost, IActionHost
{

    private class GridBlock implements IMultiChannelGridBlock
    {
        @Override
        public double getIdlePowerUsage()
        {
            return 0;
        }

        @Override
        public EnumSet<GridFlags> getFlags()
        {
            return EnumSet.noneOf(GridFlags.class);
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

        @Override
        public double getIdleSubChannelPowerUsage()
        {
            return 10;
        }

        @Override
        public ItemStack getSubChannelMachineRepresentation()
        {
            return null;
        }

        @Override
        public int getAmountBaseChannels()
        {
            return AdvancedInterfaceTileEntity.baseChannels;
        }
    }

    private GridBlock gridBlock;
    private IMultiChannelGridNode gridNode;
    private boolean isReady;
    private static final int baseChannels = 4;

    public AdvancedInterfaceTileEntity()
    {
        gridBlock = new GridBlock();
    }

    public IGridNode getNode()
    {
        if( this.gridNode == null && FMLCommonHandler.instance().getEffectiveSide().isServer() && this.isReady)
        {
            this.gridNode = new MultiChannelGridNode(this.gridBlock);
            this.gridNode.updateState();
        }

        return this.gridNode;
    }

    public boolean addConnection()
    {
        return this.gridNode != null && this.gridNode.addConnection();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isReady) return;
        this.isReady = true;
        getNode();
    }

    @Override
    public boolean canUpdate()
    {
        return !this.isReady;
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
