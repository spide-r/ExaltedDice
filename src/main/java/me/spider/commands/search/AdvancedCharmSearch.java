package me.spider.commands.search;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
public class AdvancedCharmSearch  extends SlashCommand {
    // category, subsection
    public AdvancedCharmSearch(){
        this.name = "charm";
        this.help = "Searches for a specified charm tree";
        this.subcommandGroup = new SubcommandGroupData("advanced", "Advanced Search");

        this.options.add(new OptionData(OptionType.STRING, "category", "What category?", true, true));
        this.options.add(new OptionData(OptionType.STRING, "subsection", "What subsection?", true, true));
        this.options.add(new OptionData(OptionType.BOOLEAN, "hide", "Do you wish to hide the search?", false));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        String category = event.getOption("category", "unknown", OptionMapping::getAsString);
        String subsection = event.getOption("subsection", "unknown", OptionMapping::getAsString);
        boolean hide = event.getOption("hide", false, OptionMapping::getAsBoolean);

        try {
            List<String> charms = Main.bookManager.getCharms(category, subsection);
            StringBuilder b = new StringBuilder();
            b.append("## Charm Set for: ").append(category).append(", ").append(subsection).append("\n");
            for (String charm : charms) {
                b.append(charm).append('\n');
            }
            String s = b.toString();
            if(s.length() > 2000){
                if(hide){
                    String t = s.substring(0, 1950) + "**[Description cut to fit Discord's limits]**";
                    event.reply(t).setEphemeral(true).queue();
                } else {
                    int space = Constants.getDelimiter(s.substring(0, 1999));
                    event.reply(s.substring(0, space)).queue(suc -> {
                        int tracker = space;
                        while (tracker < s.length()){
                            int startPoint = tracker;
                            int endPoint = Math.min(s.length() - tracker, 1999);
                            int space2 = Constants.getDelimiter(s.substring(startPoint, endPoint+startPoint));
                            tracker += space2;
                            event.getChannel().sendMessage(s.substring(startPoint, startPoint+space2)).queue();
                        }
                    });

                }
            } else {
                event.reply(s).setEphemeral(hide).queue();

            }
        } catch (SQLException | NullPointerException e) {
            event.reply("Unable to find `" + category + ", " + subsection + "`!").setEphemeral(hide).queue();
        }
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        List<Command.Choice> options = null;
        if (event.getFocusedOption().getName().equals("category")){
            try {
                options = Main.bookManager.getKeys("charmCategories").stream().filter(w -> w.toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
                        .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
                if(options.size() > 25){
                    options = options.subList(0,24);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if(event.getFocusedOption().getName().equals("subsection")){
            try {
                options = Main.bookManager.getKeys("charmSubsections").stream().filter(w -> w.toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
                        .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
                if(options.size() > 25){
                    options = options.subList(0,24);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (options != null) {
            event.replyChoices(options).queue();
        }
    }
}
