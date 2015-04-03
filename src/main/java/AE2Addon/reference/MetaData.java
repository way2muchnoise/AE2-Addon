package AE2Addon.reference;

import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

public class MetaData
{
    /**
     * Setup mod metadata
     *
     * @param metadata
     * @return edited metadata object
     */
    public static ModMetadata init(ModMetadata metadata)
    {
        metadata.modId = Reference.ID;
        metadata.name = Reference.NAME;
        metadata.description = Reference.NAME + " is an addon for AE2 where I try to add usefull things";
        //metadata.url = "";
        //metadata.logoFile = "assets/" + Reference.ID + "/logo.png";
        metadata.version = Reference.V_MAJOR + "." + Reference.V_MINOR;
        metadata.authorList = Arrays.asList("way2muchnoise");
        //metadata.credits = "";
        metadata.autogenerated = false;
        return metadata;
    }
}
