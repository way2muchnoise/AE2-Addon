package AE2Addon.ae;

import appeng.api.exceptions.FailedConnection;
import appeng.api.networking.*;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import appeng.me.GridConnection;
import appeng.me.GridNode;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class MultiChannelGridNode extends GridNode implements IMultiChannelGridNode
{
    private IMultiChannelGridBlock proxyBlock;
    private List<IGridConnection> internalConnections;
    private List<GridNode> internalNodes;
    private GhostGridBlock internalBlock;
    private boolean connected;

    public MultiChannelGridNode(IMultiChannelGridBlock proxy)
    {
        super(proxy);
        this.proxyBlock = proxy;
        this.internalBlock = new GhostGridBlock();
        this.internalNodes = new LinkedList<GridNode>();
        this.internalConnections = new LinkedList<IGridConnection>();
        this.connected = false;
    }

    private void initBaseChannels()
    {
        if (getFreeChannels() < this.proxyBlock.getAmountBaseChannels())
            return;

        for (int i = 0; i < this.proxyBlock.getAmountBaseChannels(); i++)
        {
            GridNode ghostNode = new GridNode(this.internalBlock);
            ghostNode.updateState();
            GridConnection connection;
            try
            {
                connection = new GridConnection(this, ghostNode, ForgeDirection.UNKNOWN);
            } catch (FailedConnection failedConnection)
            {
                failedConnection.printStackTrace();
                ghostNode.destroy();
                disconnectAll();
                return;
            }
            this.internalNodes.add(ghostNode);
            this.internalConnections.add(connection);
        }
        this.connected = true;
    }

    @Override
    public boolean addConnection()
    {
        if (getFreeChannels() <= 0)
            return false;

        GridNode ghostNode = new GridNode(this.internalBlock);
        ghostNode.updateState();
        GridConnection connection;
        try
        {
            connection = new GridConnection(this, ghostNode, ForgeDirection.UNKNOWN);
        } catch (FailedConnection failedConnection)
        {
            failedConnection.printStackTrace();
            ghostNode.destroy();
            return false;
        }
        this.internalNodes.add(ghostNode);
        this.internalConnections.add(connection);
        return true;
    }

    @Override
    public void removeConnection()
    {
        if (this.internalConnections.size() <= this.proxyBlock.getAmountBaseChannels())
            return;

        this.internalConnections.get(0).destroy();
        this.internalConnections.remove(0);
        this.internalNodes.get(0).destroy();
        this.internalNodes.remove(0);
    }

    @Override
    public void disconnectAll()
    {
        for (IGridConnection connection : this.internalConnections)
            connection.destroy();
        this.internalConnections.clear();
        for (GridNode node : this.internalNodes)
            node.destroy();
        this.internalNodes.clear();
        this.connected = false;
    }

    public int getFreeChannels()
    {
        return this.getMaxChannels() - this.usedChannels();
    }

    @Override
    public void updateState()
    {
        super.updateState();
        if (!this.connected)
            initBaseChannels();
    }

    @Override
    public void destroy()
    {
        disconnectAll();
        super.destroy();
    }

    @Override
    public boolean meetsChannelRequirements()
    {
        return this.usedChannels() > proxyBlock.getAmountBaseChannels();
    }

    private class GhostGridBlock implements IGridBlock
    {
        @Override
        public double getIdlePowerUsage()
        {
            return MultiChannelGridNode.this.proxyBlock.getIdleSubChannelPowerUsage();
        }

        @Override
        public EnumSet<GridFlags> getFlags()
        {
            return EnumSet.of(GridFlags.REQUIRE_CHANNEL);
        }

        @Override
        public boolean isWorldAccessible()
        {
            return false;
        }

        @Override
        public DimensionalCoord getLocation()
        {
            return MultiChannelGridNode.this.proxyBlock.getLocation();
        }

        @Override
        public AEColor getGridColor()
        {
            return MultiChannelGridNode.this.proxyBlock.getGridColor();
        }

        @Override
        public void onGridNotification(GridNotification gridNotification)
        {
            MultiChannelGridNode.this.proxyBlock.onGridNotification(gridNotification);
        }

        @Override
        public void setNetworkStatus(IGrid iGrid, int i)
        {
            MultiChannelGridNode.this.proxyBlock.setNetworkStatus(iGrid, i);
        }

        @Override
        public EnumSet<ForgeDirection> getConnectableSides()
        {
            return EnumSet.of(ForgeDirection.UNKNOWN);
        }

        @Override
        public IGridHost getMachine()
        {
            return MultiChannelGridNode.this.proxyBlock.getMachine();
        }

        @Override
        public void gridChanged()
        {
            MultiChannelGridNode.this.proxyBlock.gridChanged();
        }

        @Override
        public ItemStack getMachineRepresentation()
        {
            return MultiChannelGridNode.this.proxyBlock.getSubChannelMachineRepresentation();
        }
    }
}
