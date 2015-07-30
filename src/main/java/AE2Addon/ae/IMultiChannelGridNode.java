package AE2Addon.ae;

import appeng.api.networking.IGridNode;

public interface IMultiChannelGridNode extends IGridNode
{
    /**
     * Disconnect all channels including the base ones
     */
    void disconnectAll();

    /**
     * Add and extra connection
     *
     * @return true if successful
     */
    boolean addConnection();

    /**
     * Remove a connection
     * Base connections can't be removed
     */
    void removeConnection();
}
