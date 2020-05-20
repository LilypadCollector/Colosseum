package dev.dannychoi.colosseum;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandSetCharge implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can run this command."));
            return CommandResult.success();
        }

        Player p = (Player) src;
        PlayerProfile pp = Colosseum.getPlayerProfileOf(p);

        int arg = args.<Integer>getOne("charge").get();

        if (pp == null) {
            src.sendMessage(Text.builder("Select a species first.").color(TextColors.RED).build());
            return CommandResult.success();
        }

        pp.setCharge(arg);
        src.sendMessage(Text.builder("Successfully set charge.").color(TextColors.GREEN).build());
        return CommandResult.success();
    }
}
