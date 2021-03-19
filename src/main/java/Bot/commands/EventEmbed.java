package Bot.commands;

import org.javacord.api.entity.Icon;
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
            Icon eupicture = event.getMessageAuthor().getAvatar();
            String[] emessage = content.split("\\s+");
            //Pattern tpattern = Pattern.compile("title:", Pattern.CASE_INSENSITIVE);
            //Matcher tmatcher = tpattern.matcher(content);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(emessage[1])
                    .setDescription(emessage[2])
                    .setAuthor(euname, "", eupicture)
                    .addField("Date & Time", emessage[3] + " | " + emessage[4])
                    .addInlineField("Crewmates", "None")
                    .addInlineField("Impostors", "None")
                    .setColor(Color.BLUE)
                    //.setFooter("React to join.")
                    //.setImage(new File("F:/Pictures2/AmongUsIcon.png"))
                    .setThumbnail(new File("F:/Pictures2/AmongUsIcon.png"));

            TextChannel channel = event.getChannel();
            channel.sendMessage(embed)
                    .thenAccept(sentEmbed -> {
                        sentEmbed.addReactionAddListener(revent -> {
                            if (revent.getEmoji().equalsEmoji(":thumbsup:")) {
                                // Then maybe need either embed.updateFields() or revent.editMessage() but not sure.
                            }
                        });
                    });

        }

    }

}
