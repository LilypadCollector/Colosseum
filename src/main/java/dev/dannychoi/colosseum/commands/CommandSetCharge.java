package dev.dannychoi.colosseum.commands;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.PlayerProfile;
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
        PlayerProfile pp = Colosseum.getGameManager().getPlayerProfileOf(p);

        int arg = args.<Integer>getOne("charge").get();

        if (pp == null) {
            src.sendMessage(Text.builder("Select a species first.").color(TextColors.RED).build());
            return CommandResult.success();
        }

        if (arg == -1) {
            src.sendMessage(Text.of("You've invoked the -1 debug key. Charge = " + pp.getCharge()));
        }

        pp.setCharge(arg);
        src.sendMessage(Text.builder("Successfully set charge to " + arg + ".").color(TextColors.GREEN).build());
        return CommandResult.success();
    }
}