package dev.dannychoi.colosseum;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandSpecies implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!args.hasAny(Text.of("species"))) {
            src.sendMessage(Text.builder("List of species: DOG").color(TextColors.GREEN).build());
            return CommandResult.success();
        }

        // Checks if command sender is NOT a player. Only a player can run this command (Note: They can still run /species without args).
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("You must be a player to use this command."));
            return CommandResult.success();
        }

        String argSpecies = args.<Text>getOne("species").get().toString(); // This variable now has the first argument!

        // This for loop tries to match the user's argument (species) with a Species.SpeciesType.
        for (SpeciesType type : SpeciesType.values()) {
            // If the iterated SpeciesType matches the user's inputted arg.
            PlayerProfile userProfile = Colosseum.getActivePlayers().get((Player) src);
            if (userProfile.getSpeciesType().equals(type.toString())) {
                userProfile.setSpeciesType(type);
                Text successMsg = Text.builder("You are now a ")
                        .append(Text.of(type.toString()))
                        .color(TextColors.GREEN)
                        .build();
                src.sendMessage(successMsg);

                return CommandResult.success();
            }
        }

        src.sendMessage(Text.builder("That species does not exist. Type /species to see all options.").color(TextColors.RED).build());
        return CommandResult.success();
    }
}
