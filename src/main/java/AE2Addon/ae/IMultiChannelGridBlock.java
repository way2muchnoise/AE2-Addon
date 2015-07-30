package AE2Addon.ae;

import appeng.api.networking.IGridBlock;
import net.minecraft.item.ItemStack;

public interface IMultiChannelGridBlock extends IGridBlock
{
    /**
     * Power usage per sub channel
     *
     * @return power usage per sub channel
     */
    double getIdleSubChannelPowerUsage();

    /**
     * The representation for the sub Channels
     *
     * @return an {@link ItemStack} or null for no display
     */
    ItemStack getSubChannelMachineRepresentation();

    /**
     * Amount of channels the have to be used at all time
     *
     * @return base channels needed to connect
     */
    int getAmountBaseChannels();
}
