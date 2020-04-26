package com.yunus1903.exorcery.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author Yunus1903
 * @since 26/04/2020
 */
public class ClientConfig implements IBaseConfig
{
    public ClientConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Client config").push("client");

        builder.pop();
    }

    @Override
    public void bake()
    {
        
    }
}
