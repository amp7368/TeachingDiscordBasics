package example.apple.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DiscordBot extends ListenerAdapter {
    // a variable for the prefix of the bot
    // (what you say before your messagein discord to talk to the bot)
    public static final String PREFIX = "m!";

    public static String discordToken; // my bot token
    public static JDA client;

    // This is all just to get the discord bot token from a file instead of having it in code
    // IGNORE THE NEXT BLOCK OF CODE
    static {
        List<String> list = Arrays.asList(DiscordMain.class.getProtectionDomain().getCodeSource().getLocation().getPath().split("/"));
        String botTokenFilePath = String.join("/", list.subList(0, list.size() - 1)) + "/config/discordToken.txt";

        File botTokenFile = new File(botTokenFilePath);
        if (!botTokenFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                botTokenFile.getParentFile().mkdirs();
                //noinspection ResultOfMethodCallIgnored
                botTokenFile.createNewFile();
            } catch (IOException ignored) {
            }
            System.err.println("Please fill in the token for the discord bot in '" + botTokenFilePath + "'");
            System.exit(1);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(botTokenFile));
            discordToken = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.err.println("Please fill in the token for the discord bot in '" + botTokenFilePath + "'");
            System.exit(1);
        }

    }


    /**
     * The purpose of this "constructor" is to have the bot "login" to discord
     * and ask that JDA (the discord library we're using to interact with discord)
     * calls #onMessageReactionAdd
     *
     * @see #onMessageReactionAdd
     */
    // This is what gets called when you do "new DiscordBot()"
    public DiscordBot() throws LoginException {
        // say that the discord bot is starting
        System.out.println("DiscordBot starting");

        // create a new JDABuilder, so that we can add settings if we need to
        JDABuilder builder = JDABuilder.createDefault(discordToken);
        // tell JDA to tell us when it gets messages and stuff
        builder.addEventListeners(this);

        // say that the discord bot is going to start logging in soon
        System.out.println("DiscordBot logging in");

        // start logging in
        client = builder.build();

        // say that the bot has started even though we're technically not necessarily fully logged in
        System.out.println("DiscordBot started");
    }

    /**
     * this gets called whenever the bot recieves a message
     *
     * @param event the event object that JDA created to let us know all the info about the message
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // if the author of the message is a bot, we should ignore this message
        if (event.getAuthor().isBot()) {
            return;
        }
        // the author is not a bot now that we're here

        // if the message starts with ping, respond with pong
        if (event.getMessage().getContentRaw().startsWith("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }
    }

    /**
     * this gets called whenever someone joins a server you're in
     *
     * @param event the event object that JDA created to let us know all the info about the event
     */
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
    }

    /**
     * this gets called whenever someone joins a server you're in
     *
     * @param event the event object that JDA created to let us know all the info about the event
     */
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
    }
}
