package com.yunus1903.exorcery.common.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSpellEffect
{
    private final EffectType type;
    private final BlockPos pos;

    public PacketSpellEffect(EffectType type, BlockPos pos)
    {
        this.type = type;
        this.pos = pos;
    }

    public static void encode(PacketSpellEffect pkt, PacketBuffer buf)
    {
        buf.writeByte(pkt.type.ordinal());
        buf.writeBlockPos(pkt.pos);
    }

    public static PacketSpellEffect decode(PacketBuffer buf)
    {
        return new PacketSpellEffect(EffectType.values()[buf.readByte()], buf.readBlockPos());
    }

    public static class Handler
    {
        public static void handle(PacketSpellEffect msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {

                });
                ctx.get().setPacketHandled(true);
            }
        }
    }
}
