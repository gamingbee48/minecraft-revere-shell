package com.shell;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellMod implements ModInitializer {
    public static final String MOD_ID = "shell";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Shell Mod loading...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ShellCommand.register(dispatcher);
        });

        LOGGER.info("Shell Mod loaded!");
    }
}