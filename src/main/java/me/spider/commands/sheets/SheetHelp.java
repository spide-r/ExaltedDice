package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;

import java.util.Arrays;

public class SheetHelp extends SlashCommand {
    public SheetHelp(){
        this.name = "help";
        this.help = "How do you use these commands?";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("You can select Attributes from the following list:\n" + Arrays.stream(Constants.ATTRIBUTE_LIST).toList() + "\n" + """
                **/sheet get** - Gets all attributes on a sheet
                **/sheet set** - Overwrites the selected attribute with the chosen value
                **/sheet modify** - Modifies the selected attribute by the chosen value
                **/sheet refresh** - Fully restores your essence, willpower, and limit.
                **/sheet health** - Shows all of your health levels. Set them with **/sethealthlevels**
                
                Use **/essence** to get all your essences.
                """).setEphemeral(true).queue();
    }
}
