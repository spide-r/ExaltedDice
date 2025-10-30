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
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.sql.SQLException;
    import java.util.List;
    import java.util.stream.Collectors;

    public class GenericSearch extends SlashCommand {
        Logger LOG = LoggerFactory.getLogger(GenericSearch.class);

        public GenericSearch(String toSearch){
            this.name = toSearch;
            this.help = "Searches for a specified " + toSearch;
            this.options.add(new OptionData(OptionType.STRING, "name", "What are you trying to search?", true, true));
            this.options.add(new OptionData(OptionType.BOOLEAN, "hide", "Do you wish to hide the search?", false));
        }
        @Override
        protected void execute(SlashCommandEvent event) {
            String toSearch = event.getOption("name", "unknown", OptionMapping::getAsString);
            boolean hide = event.getOption("hide", false, OptionMapping::getAsBoolean);

            try {
                String s = Main.bookManager.getPage(this.name, toSearch).getFancyText().trim();
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
                                String toSend = s.substring(startPoint, startPoint+space2);
                                if(!toSend.isEmpty()){
                                    event.getChannel().sendMessage(toSend).queue(m -> {
                                        LOG.info("Sent message: {}", m.getId());
                                    }, e -> {
                                        LOG.error("Issue sending message.", e);
                                    });

                                }
                            }
                        });

                    }
                } else {
                    event.reply(s).setEphemeral(hide).queue();

                }
            } catch (SQLException | NullPointerException e) {
                event.reply("Unable to find `" + toSearch + "`!").setEphemeral(hide).queue();
            }
        }

        @Override
        public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
            if(event.getFocusedOption().getValue().length() < 2){
                return;
            }
            List<Command.Choice> options = null;
            try {
                options = Main.bookManager.getKeys(this.name).stream().filter(w -> w.toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
                        .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
                if(options.size() > 25){
                    options = options.subList(0,24);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            event.replyChoices(options).queue();
        }
    }
