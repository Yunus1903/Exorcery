package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMana
{
    private final float mana;
    private final float maximumMana;
    private final int regenerationRate;

    public PacketSyncMana(float mana, float maximumMana, int regenerationRate)
    {
        this.mana = mana;
        this.maximumMana = maximumMana;
        this.regenerationRate = regenerationRate;
    }

    public static void encode(PacketSyncMana pkt, PacketBuffer buf)
    {
        buf.writeFloat(pkt.mana);
        buf.writeFloat(pkt.maximumMana);
        buf.writeVarInt(pkt.regenerationRate);
    }

    public static PacketSyncMana decode(PacketBuffer buf)
    {
        return new PacketSyncMana(buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    public static class Handler
    {
        public static void handle(PacketSyncMana msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    Minecraft mc = Minecraft.getInstance();
                    IMana mana = mc.player.getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());
                    mana.set(msg.mana);
                    mana.setMax(msg.maximumMana);
                    mana.setRegenerationRate(msg.regenerationRate);
                });
                ctx.get().setPacketHandled(true);
            }
        }
    }
}
