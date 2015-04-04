package AE2Addon.tile;

import AE2Addon.registry.BlockRegistry;
import appeng.api.AEApi;
import appeng.api.networking.*;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;

public class AdvancedInterfaceTileEntity extends TileEntity implements IGridHost, IGridBlock
{
    @Override
    public double getIdlePowerUsage()
    {
        return 1;
    }

    @Override
    public EnumSet<GridFlags> getFlags()
    {
        return EnumSet.of(GridFlags.REQUIRE_CHANNEL, GridFlags.DENSE_CAPACITY);
    }

    @Override
    public boolean isWorldAccessible()
    {
        return true;
    }

    @Override
    public DimensionalCoord getLocation()
    {
        return new DimensionalCoord(this);
    }

    @Override
    public AEColor getGridColor()
    {
        return AEColor.Transparent;
    }

    @Override
    public void onGridNotification(GridNotification notification)
    {

    }

    @Override
    public void setNetworkStatus(IGrid grid, int channelsInUse)
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
        return this;
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
    public IGridNode getGridNode(ForgeDirection dir)
    {
        return AEApi.instance().createGridNode(this);
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
}
