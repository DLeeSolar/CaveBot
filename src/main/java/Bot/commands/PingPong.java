package Bot.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PingPong implements MessageCreateListener {

    /*
     * This command is used to have the bot say "Pong!" in response to a message of "Ping!".
     */

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (event.getMessage().getContent().equalsIgnoreCase("Ping!")) {
            event.getChannel().sendMessage("Pong!");
        }

    }

}
