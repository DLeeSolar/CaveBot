package Bot.commands;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.List;

public class Colour implements MessageCreateListener {

    /*
     * This command is used to change the user's colour role.
     * Any user can use this command.
     */

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String content = event.getMessage().getContent();
        if (content.startsWith("?colour ")) {
            // You're trying to do some stuff with lists here so that you can match parts to the list,
            // to figure out which role (if any) need to be removed, and then add the list in the command.
            // Ideally, the final form of this command would be "!colour <colour-code>", which removes any
            // previous colour, and adds the desired one.
            //
            //String[] rmessage = event.getMessage().getContent().split("\\s+");
            String[] rmessage = content.split("\\s+");
            String[] rcolours = {"B", "DS", "S", "DO", "O", "DY", "Y", "DPi", "Pi", "DPu", "Pu", "DB", "Bl", "TB", "DG", "G", "DT", "V", "T"};
            List<String> rclist = Arrays.asList(rcolours);

            if (rclist.contains(rmessage[1])) {

                Server server = event.getServer().get();
                User user = event.getMessageAuthor().asUser().get();
                String uname = event.getMessageAuthor().getDisplayName();

                for (String i : rcolours) {
                    Role rolei = server.getRolesByNameIgnoreCase(i).get(0);
                    server.removeRoleFromUser(user, rolei);
                }

                Role newrole = server.getRolesByNameIgnoreCase(rmessage[1]).get(0);
                server.addRoleToUser(user, newrole);

                event.deleteMessage();
                event.getChannel().sendMessage(uname + "'s Roles Changed");
            } else {
                event.getChannel().sendMessage(rmessage[1] + " does not exist");
                event.getChannel().sendMessage("Colour roles are B (Black), DS (Dark Salmon), S (Salmon), DO (Dark Orange), O (Orange), DY (Dark Yellow), Y (Yellow), DPi (Dark Pink), Pi (Pink), DPu (Dark Purple), DB (Dark Blue), Bl (Blue), TB (TurtleEgg Blue, DG (Dark Green), G (Green), DT (Dark Turquoise), T (Turquoise), V (Viridian)");
            }
        }

    }

}
