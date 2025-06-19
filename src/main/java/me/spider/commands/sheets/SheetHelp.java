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
                **/sheet get** - Gets the selected attribute
                **/sheet set** - Overwrites the selected attribute with the chosen value
                **/sheet modify** - Modifies the selected attribute by the chosen value
                **/sheet refresh** - Resets your essences back to their max value
                
                Use **/sheet get essences** to get all your essences.
                """).queue();
    }
}
