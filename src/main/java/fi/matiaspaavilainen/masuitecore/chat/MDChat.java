package fi.matiaspaavilainen.masuitecore.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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

    /**
     * Gets a TextComponent from a message
     *
     * @param message Must be legacy formatted
     * @return The newly created TextComponent
     */
    public static TextComponent getMessageFromString(String message) {
        return getMessageFromString(message, false);
    }

    /**
     * Gets a TextComponent from a message
     *
     * @param message   A legacy or B+ formatted message
     * @param readExtra Whether or not to parse B+ formatting, A.K.A. hover and click events
     * @return Newly created TextComponent
     */
    public static TextComponent getMessageFromString(String message, boolean readExtra) {
        TextComponent chatMessage = new TextComponent();

        if (message == null) {
            return chatMessage;
        }

        BaseComponent last = new TextComponent("");

        List<TextComponent> actions = new ArrayList<>();
        // This is where we start looking for B+ formatting
        if (readExtra) {
            // Thanks to StarShadow#3546 for negative look behind
            // Will stop certain strings from getting converted
            List<String> caught = getMatches(message, "<(.+?)(?<!\\\\)>");
            for (String action : caught) {
                String hover = getMatch(action, "\\{(.+?)(?<!\\\\)\\}");
                String click = getMatch(action, "\\((.+?\\:.+?)(?<!\\\\)\\)");
                if (hover == null && click == null) {
                    // Requires a click or hover, or skip
                    continue;
                }
                String desc = action;
                HoverEvent hAction = null;
                ClickEvent cAction = null;
                if (click != null) {
                    String[] clickSplit = click.split("\\:", 2);
                    ClickEvent.Action cActionAction;
                    try {
                        cActionAction = ClickEvent.Action.valueOf(clickSplit[0].toUpperCase());
                    } catch (IllegalArgumentException exception) {
                        cActionAction = null;
                    }
                    if (cActionAction == null) {
                        if (hover == null) {
                            continue;
                        }
                    } else {
                        cAction = new ClickEvent(cActionAction, clickSplit[1]);
                        desc = desc.replace("(" + click + ")", "");
                    }
                }
                if (hover != null) {
                    hAction = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create());
                    desc = desc.replace("{" + hover + "}", "");
                }
                // Replace all the escaped tags
                desc = desc.replace("\\{", "{").replace("\\}", "}");
                desc = desc.replace("\\(", "(").replace("\\)", ")");
                TextComponent description = getMessageFromString(desc, false);
                for (BaseComponent component : description.getExtra()) {
                    component.setHoverEvent(hAction);
                    component.setClickEvent(cAction);
                }
                message = message.replace("<" + action + ">", "" + ChatColor.COLOR_CHAR + PLACEHOLDER);
                actions.add(description);
            }
            // Replace all the escaped tags
            message = message.replace("\\<", "<").replace("\\>", ">");
        }

        // More efficient conversion using split, as opposed to iterating over every character
        String[] parts = (" " + message).split("" + ChatColor.COLOR_CHAR);
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
                chatMessage.addExtra(last);
                last = last.duplicate();
            }
        }

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

    private static List<String> getMatches(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        return matches;
    }

    private static String getMatch(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}

