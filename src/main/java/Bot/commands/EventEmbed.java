package Bot.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;

public class EventEmbed implements MessageCreateListener {

    /*
     * This command is used to have the bot say "Pong!" in response to a message of "Ping!".
     */

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        //if (event.getMessage().getContent().equalsIgnoreCase("?event")) {
        String content = event.getMessage().getContent();
        if (content.startsWith("?event ")) {
            String euname = event.getMessageAuthor().getDisplayName();
            String[] emessage = content.split("\\s+");
            //Pattern tpattern = Pattern.compile("title:", Pattern.CASE_INSENSITIVE);
            //Matcher tmatcher = tpattern.matcher(content);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(emessage[1])
                    .setDescription(emessage[2])
                    .setAuthor(euname, "", "https://cdn.discordapp.com/embed/avatars/0.png")
                    //.addInlineField("Date", emessage[3])
                    //.addInlineField("Time", emessage[4])
                    .addField("Date & Time", emessage[3] + " | " + emessage[4])
                    //.addField("","")
                    .addInlineField("Crewmates", "None")
                    .addInlineField("Impostors", "None")
                    .setColor(Color.BLUE)
                    //.setFooter("React to join.")
                    //.setImage(new File("F:/Pictures2/AmongUsIcon.png"))
                    .setThumbnail(new File("F:/Pictures2/AmongUsIcon.png"));

            TextChannel channel = event.getChannel();
            channel.sendMessage(embed);

        }

    }

}
