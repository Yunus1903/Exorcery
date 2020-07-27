package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.misc.SoundHandler;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 14/04/2020
 */
public class SyncCastingPacket
{
    private final boolean isCasting;
    @Nullable
    private final Spell spell;

    public SyncCastingPacket(boolean isCasting, @Nullable Spell spell)
    {
        this.isCasting = isCasting;
        this.spell = spell;
    }

    public static void encode(SyncCastingPacket pkt, PacketBuffer buf)
    {
        buf.writeBoolean(pkt.isCasting);
        buf.writeVarInt(Spell.getIdFromSpell(pkt.spell));
    }

    public static SyncCastingPacket decode(PacketBuffer buf)
    {
        return new SyncCastingPacket(buf.readBoolean(), Spell.getSpellById(buf.readVarInt()));
    }

    public static class Handler
    {
        public static void handle(SyncCastingPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isServer())
            {
                ctx.get().getSender().getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                {
                    Spell spell = msg.spell;

                    if (msg.isCasting) casting.startCasting(spell);
                    else
                    {
                        casting.stopCasting();
                        SoundHandler.stopChanting(ctx.get().getSender());
                    }
                });
            }
            else if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    AtomicReference<PlayerEntity> player = new AtomicReference<>();

                    DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                    {
                        player.set(Minecraft.getInstance().player);
                        if (msg.isCasting)
                        {
                            Minecraft.getInstance().ingameGUI.setOverlayMessage(
                                    new TranslationTextComponent("gui.exorcery.actionbar.casting")
                                            .appendString(": " + msg.spell.getType().getColor())
                                            .append(msg.spell.getName()),
                                    false
                            );
                        }
                    });

                    player.get().getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                    {
                        Spell spell = msg.spell;

                        if (msg.isCasting) casting.startCasting(spell);
                        else casting.stopCasting();
                    });
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
