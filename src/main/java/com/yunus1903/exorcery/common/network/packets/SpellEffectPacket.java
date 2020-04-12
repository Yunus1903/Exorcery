package com.yunus1903.exorcery.common.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellEffectPacket
{
    private final EffectType type;
    private final BlockPos pos;

    public SpellEffectPacket(EffectType type, BlockPos pos)
    {
        this.type = type;
        this.pos = pos;
    }

    public static void encode(SpellEffectPacket pkt, PacketBuffer buf)
    {
        buf.writeByte(pkt.type.ordinal());
        buf.writeBlockPos(pkt.pos);
    }

    public static SpellEffectPacket decode(PacketBuffer buf)
    {
        return new SpellEffectPacket(EffectType.values()[buf.readByte()], buf.readBlockPos());
    }

    public static class Handler
    {
        public static void handle(SpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx)
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
