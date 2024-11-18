package me.spider.commands.sheets;

import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class Sheet extends Command {
    GetAttribute getAttribute = new GetAttribute();
    HelpAttribute helpAttribute = new HelpAttribute();
    ModifyAttribute modifyAttribute = new ModifyAttribute();
    SetAttribute setAttribute = new SetAttribute();
    ResetEssences resetEssences = new ResetEssences();
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        switch (Objects.requireNonNull(event.getSubcommandName())){
            case "get":
                getAttribute.OnCommand(event);
                break;
            case "modify":
                modifyAttribute.OnCommand(event);
                break;
            case "set":
                setAttribute.OnCommand(event);
                break;
            case "resetessences":
                resetEssences.OnCommand(event);
                break;
            case "help":
                helpAttribute.OnCommand(event);
                break;
        }
    }
}
