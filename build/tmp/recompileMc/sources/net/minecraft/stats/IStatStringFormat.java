package net.minecraft.stats;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IStatStringFormat
{
    /**
     * Formats the strings based on 'IStatStringFormat' interface.
     *  
     * @param str The String to format
     */
    String formatString(String str);
}