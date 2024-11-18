package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Arrays;

public class SheetHelp extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        event.reply("You can select Attributes from the following list:\n" + Arrays.stream(Constants.ATTRIBUTE_LIST).toList() + "\n" + """
                **/sheet get** - Gets the selected attribute
                **/sheet set** - Overwrites the selected attribute with the chosen value
                **/sheet modify** - Modifies the selected attribute by the chosen value
                **/sheet refresh** - Resets your essences back to their max value
                
                Use **/sheet get essences** to get all your essences.
                """).queue();
    }
}
