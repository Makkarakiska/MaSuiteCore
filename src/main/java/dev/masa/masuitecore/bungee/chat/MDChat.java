package dev.masa.masuitecore.bungee.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A fast and easy way to convert legacy or B+ formatted chat into MD's ChatComponent API
 * <p>
 * Ported over from https://github.com/BananaPuncher714/BrickBoard/tree/master/io/github/bananapuncher714/brickboard/api/chat
 * <p>
 * Find out more about B+ formatting here: https://bananapuncher714.gitbook.io/brickboard/b+-style-chat-formatting
 *
 * @author BananaPuncher714
 * @author Masa (version for BungeeCord)
 */
public class MDChat {
    private final static char PLACEHOLDER = '\u02A7';
    private static final Pattern URL_PATTERN = Pattern.compile("((?:(?:https?)://)?[\\w-_\\.]{2,})\\.([a-zA-Z]{2,3}(?:/\\S+)?)");

    /**
     * Gets a TextComponent from a message
     *
     * @param message Must be legacy formatted
     * @return The newly created TextComponent
     */
    public static TextComponent getMessageFromString(String message) {
        return getMessageFromString(message, true);
    }

    /**
     * Gets a TextComponent from a message
     *
     * @param message   A legacy or B+ formatted message
     * @param links Whether or not to add links to message
     * @return Newly created TextComponent
     */
    public static TextComponent getMessageFromString(String message, boolean links) {
        TextComponent chatMessage = new TextComponent();

        if (message == null) {
            return chatMessage;
        }

        BaseComponent first = new TextComponent();

        List<TextComponent> actions = new ArrayList<>();

        Queue<String> urls = new LinkedList<>();
        String[] noUrlParts;

        if(links) {
            //Extract urls from message
            Matcher matcher = URL_PATTERN.matcher(message);
            //Put all urls on a Queue to be re-added later
            while (matcher.find())
                urls.add(matcher.group());

            //Split message into parts without urls
            noUrlParts = URL_PATTERN.split(message);
        }
        else{
            //if no links should be parsed, just throw in the message as it is
            noUrlParts = new String[]{message};
        }

        BaseComponent last = new TextComponent();
        // Iterate through no Url message parts
        for(String noUrlPart: noUrlParts) {
            // More efficient conversion using split, as opposed to iterating over every character
            String[] parts = (" " + noUrlPart).split("" + ChatColor.COLOR_CHAR);
            for (String part : parts) {
                if (part.isEmpty()) {
                    continue;
                }

                char colorCharacter = part.charAt(0);
                if (colorCharacter == PLACEHOLDER) {
                    chatMessage.addExtra(actions.remove(0));
                } else {
                    ChatColor color = ChatColor.getByChar(colorCharacter);

                    if (color != null) {
                        if (isColor(color)) {
                            clearFormatting(last);
                            last.setColor(color);
                        } else {
                            if (color == ChatColor.BOLD) {
                                last.setBold(true);
                            }
                            if (color == ChatColor.ITALIC) {
                                last.setItalic(true);
                            }
                            if (color == ChatColor.UNDERLINE) {
                                last.setUnderlined(true);
                            }
                            if (color == ChatColor.MAGIC) {
                                last.setObfuscated(true);
                            }
                            if (color == ChatColor.RESET) {
                                clearFormatting(last);
                            }
                            if (color == ChatColor.STRIKETHROUGH) {
                                last.setStrikethrough(true);
                            }
                        }
                    }
                }
                if (part.length() > 1) {

                    if (last instanceof TextComponent) {
                        ((TextComponent) last).setText(part.substring(1));
                    }
                    first.addExtra(last);

                    last = last.duplicate();
                }
            }
            //If a url is left in the list, it will be added
            if(!urls.isEmpty()) {
                BaseComponent urlComponent = new TextComponent(urls.peek());
                //Continue format of last part
                if(last != null) {
                    urlComponent.setColor(last.getColor());
                    urlComponent.setBold(last.isBold());
                    urlComponent.setItalic(last.isItalic());
                    urlComponent.setFont(last.getFont());
                    urlComponent.setUnderlined(last.isUnderlined());
                    urlComponent.setStrikethrough(last.isStrikethrough());
                    urlComponent.setObfuscated(last.isObfuscated());
                }
                //Add Open Url Event to the url Component
                urlComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urls.remove()));
                //Add url to this message part
                first.addExtra(urlComponent);
            }
        }
        //Add parsed Text Component to the final chat message
        chatMessage.addExtra(first);
        return chatMessage;
    }

    // Simple method to clear all formats, something that's pretty useful but not implemented *cough*
    private static void clearFormatting(BaseComponent component) {
        component.setBold(false);
        component.setItalic(false);
        component.setUnderlined(false);
        component.setObfuscated(false);
        component.setStrikethrough(false);
    }

    // Funnily enough, MD's ChatColor doesn't have this method
    private static boolean isColor(ChatColor color) {
        List<ChatColor> colors = new ArrayList<>(Arrays.asList(ChatColor.values()));
        colors.remove(ChatColor.BOLD);
        colors.remove(ChatColor.ITALIC);
        colors.remove(ChatColor.MAGIC);
        colors.remove(ChatColor.STRIKETHROUGH);
        colors.remove(ChatColor.UNDERLINE);
        return colors.contains(net.md_5.bungee.api.ChatColor.valueOf(color.name()));
    }
}
