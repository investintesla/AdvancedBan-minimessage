package me.leoko.advancedban.manager;

import me.leoko.advancedban.MethodInterface;
import me.leoko.advancedban.Universal;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;


/**
 * The Update Manager used to keep config files up to date and migrate them seamlessly to the newest version.
 */
public class UpdateManager {

    private static UpdateManager instance = null;

    /**
     * Get the update manager.
     *
     * @return the update manager instance
     */
    public static synchronized UpdateManager get() {
        return instance == null ? instance = new UpdateManager() : instance;
    }

    /**
     * Initially checks which configuration options from the newest version are missing and tries to add them
     * without altering any old configuration settings.
     */
    public void setup() {
        MethodInterface mi = Universal.get().getMethods();

        if (mi.isUnitTesting()) return;

        if (!mi.contains(mi.getMessages(), "UnNote.Usage")) {
            try {
                addMessage("Check:", "  Note: \"<red>Notes <dark_gray>» <gray>%COUNT%\"", 1);
                FileUtils.writeLines(new File(mi.getDataFolder(), "Messages.yml"), "UTF8", Arrays.asList(
                        "",
                        "# Automatically added by v2.2.1 update process",
                        "UnNote:",
                        "  Usage: \"<red>Usage <dark_gray>» <gray><i>/unwarn [ID] or /unnote clear [Name]<!i>\"",
                        "  NotFound: \"<red>Could not find note #%ID%\"",
                        "  Done: \"<gray>Note <green><i>#%ID% <!i><gray>was successfully deleted!\"",
                        "  Notification: \"<yellow><i>%OPERATOR% <!i><gray>unnoted <red><i>%NAME%<!i>\"",
                        "  Clear:",
                        "    Empty: \"<red><i>%NAME% <!i><gray>has no notes!\"",
                        "    Done: \"<gray>Cleared <green><i>%COUNT% <!i><gray>notes\"",
                        "",
                        "Note:",
                        "  Usage: \"<red>Usage <dark_gray>» <gray><i>/note [Name] [Reason]<!i>\"",
                        "  Done: \"<red><i>%NAME% <!i><gray>was successfully noted!\"",
                        "  Exempt: \"<gray>You are not able to note <red><i>%NAME%<!i>\"",
                        "  Notification:",
                        "    - \"<red><i>%NAME% <!i><gray>got noted by <yellow><i>%OPERATOR%<!i>\"",
                        "    - \"<gray>For the reason <i>%REASON%<!i>\"",
                        "",
                        "Notes:",
                        "  Usage: \"<red>Usage <dark_gray>» <gray><i>/notes [Name] <Page> <!i><red>or <gray><i>/notes <Page><!i>\"",
                        "  OutOfIndex: \"<red>There is no page %PAGE%!\"",
                        "  NoEntries: \"<red><i>%NAME% has no notes yet<!i>\"",
                        "  Header:",
                        "    - \"<gray>\"",
                        "    - \"%PREFIX% <gray>Notes for %NAME%:\"",
                        "    - \"<gray>\"",
                        "  Entry:",
                        "    - \"<gray>%DATE% <dark_gray>| <gray>By <i>%OPERATOR% <!i><gray>(<red>#%ID%<gray>)\"",
                        "    - \"<dark_gray>> <yellow>%REASON%\"",
                        "    - \"<gray>\"",
                        "  Footer: \"<gray>Page <yellow><i>%CURRENT_PAGE% <!i><gray>of <yellow><i>%TOTAL_PAGES% <!i><dark_gray>| <gray>Notes: <yellow><i>%COUNT%<!i>\"",
                        "  PageFooter: \"<gray>Use <yellow><i>/notes %NAME% %NEXT_PAGE% <!i><gray>to see the next page\"",
                        "",
                        "NotesOwn:",
                        "  OutOfIndex: \"<red>There is no page %PAGE%!\"",
                        "  NoEntries: \"<red><i>You have no notes yet<!i>\"",
                        "  Header:",
                        "    - \"<gray>\"",
                        "    - \"%PREFIX% <gray>Your notes:\"",
                        "    - \"<gray>\"",
                        "  Entry:",
                        "    - \"<gray>%DATE% <dark_gray>| <gray>By <i>%OPERATOR% <!i><gray>(<red>#%ID%<gray>)\"",
                        "    - \"<dark_gray>> <yellow>%REASON%\"",
                        "    - \"<gray>\"",
                        "  Footer: \"<gray>Page <yellow><i>%CURRENT_PAGE% <!i><gray>of <yellow><i>%TOTAL_PAGES% <!i><dark_gray>| <gray>Notes: <yellow><i>%COUNT%<!i>\"",
                        "  PageFooter: \"<gray>Use <yellow><i>/notes %NEXT_PAGE% <!i><gray>to see the next page\""
                ), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!mi.contains(mi.getMessages(), "WarnsOwn")) {
            addMessage("ChangeReason:", "", 0);
            addMessage("ChangeReason:", "WarnsOwn:", -1);
            addMessage("ChangeReason:", "  OutOfIndex: \"<red>There is no page %PAGE%!\"", -1);
            addMessage("ChangeReason:", "  NoEntries: \"<red><i>You have no warnings yet<!i>\"", -1);
            addMessage("ChangeReason:", "  Header:", -1);
            addMessage("ChangeReason:", "    - \"%PREFIX% <gray>Your warnings:\"", -1);
            addMessage("ChangeReason:", "    - \"<yellow><i>Duration <!i><dark_gray>| <gray><i>Warned by<!i>\"", -1);
            addMessage("ChangeReason:", "    - \"<red><i>#ID <!i><dark_gray>> <gray><i>Reason<!i>\"", -1);
            addMessage("ChangeReason:", "    - \"<gray>\"", -1);
            addMessage("ChangeReason:", "  Entry:", -1);
            addMessage("ChangeReason:", "    - \"<dark_gray>[<yellow>%DATE%<dark_gray>]\"", -1);
            addMessage("ChangeReason:", "    - \"<yellow>%DURATION% <dark_gray>| <gray>%OPERATOR%\"", -1);
            addMessage("ChangeReason:", "    - \"<red><b>#%ID% <dark_gray>> <gray><i>%REASON%<!i>\"", -1);
            addMessage("ChangeReason:", "    - \"<gray>\"", -1);
            addMessage("ChangeReason:", "  Footer: \"<gray>Page <yellow><i>%CURRENT_PAGE% <!i><gray>of <yellow><i>%TOTAL_PAGES% <!i><dark_gray>| <gray>Active warnings: <yellow><i>%COUNT%<!i>\"", -1);
            addMessage("ChangeReason:", "  PageFooter: \"<gray>Use <yellow><i>/warns %NEXT_PAGE% <!i><gray>to see the next page\"", -1);
        }

        if (!mi.contains(mi.getMessages(), "UnBan.Notification")) {
            addMessage("UnBan:", "  Notification: \"<yellow><i>%OPERATOR% <!i><gray>unbanned <red><i>%NAME%<!i>\"", 1);
            addMessage("UnMute:", "  Notification: \"<yellow><i>%OPERATOR% <!i><gray>unmuted <red><i>%NAME%<!i>\"", 1);
            addMessage("UnWarn:", "  Notification: \"<yellow><i>%OPERATOR% <!i><gray>unwarned <red><i>%NAME%<!i>\"", 1);
        }

        if (!mi.contains(mi.getMessages(), "Check.MuteReason")) {
            try {
                File file = new File(mi.getDataFolder(), "Messages.yml");
                List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
                int index = lines.indexOf("Check:");
                lines.add(index + 1, "  MuteReason: \"  <red>Reason <dark_gray>\\xbb <gray>%REASON%\"");
                FileUtils.writeLines(file, lines);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (!mi.contains(mi.getMessages(), "Check.BanReason")) {
            try {
                File file = new File(mi.getDataFolder(), "Messages.yml");
                List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
                int index = lines.indexOf("Check:");
                lines.add(index + 1, "  BanReason: \"  <red>Reason <dark_gray>\\xbb <gray>%REASON%\"");
                FileUtils.writeLines(file, lines);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (!mi.contains(mi.getMessages(), "Tempipban")) {
            try {
                FileUtils.writeLines(new File(mi.getDataFolder(), "Messages.yml"), Arrays.asList(
                        "",
                        "Tempipban:",
                        "  Usage: \"<red>Usage <dark_gray>\\xbb <gray><i>/tempipban [Name/IP] [Xmo/Xd/Xh/Xm/Xs/#TimeLayout] [Reason/@Layout]<!i>\"",
                        "  MaxDuration: \"<red>You are not able to ban more than %MAX%sec\"",
                        "  Layout:",
                        "  - '%PREFIX% <gray>Temporarily banned'",
                        "  - '<gray>'",
                        "  - '<gray>'",
                        "  - \"<red>Reason <dark_gray>\\xbb <gray>%REASON%\"",
                        "  - \"<red>Duration <dark_gray>\\xbb <gray>%DURATION%\"",
                        "  - '<gray>'",
                        "  - '<dark_gray>Unban application in TS or forum'",
                        "  - \"<yellow>TS-Ip <dark_gray>\\xbb <red><u>coming soon\"",
                        "  - \"<yellow>Forum <dark_gray>\\xbb <red><u>coming soon\"",
                        "  Notification:",
                        "  - \"<red><i>%NAME% <!i><gray>got banned by <yellow><i>%OPERATOR%<!i>\"",
                        "  - \"<gray>For the reason <i>%REASON%<!i>\"",
                        "  - \"<gray><i>This player got banned for <yellow>%DURATION%<!i>\"",
                        "",
                        "ChangeReason:",
                        "  Usage: \"<red>Usage <dark_gray>\\xbb <gray><i>/change-reason [ID or ban/mute USER] [New reason]<!i>\"",
                        "  Done: \"<gray>Punishment <green><i>#%ID% <!i><gray>has successfully been updated!\"",
                        "  NotFound: \"<red>Sorry we have not been able to find this punishment\""), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            File file = new File(mi.getDataFolder(), "config.yml");
            List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
            if (!mi.contains(mi.getConfig(), "EnableAllPermissionNodes")) {

                lines.remove("  # Disable for cracked servers");

                int indexOf = lines.indexOf("UUID-Fetcher:");
                if (indexOf != -1) {
                    lines.addAll(indexOf + 1, Arrays.asList(
                            "  # If dynamic it set to true it will override the 'enabled' and 'intern' settings",
                            "  # and automatically detect the best possible uuid fetcher settings for your server.",
                            "  # Our recommendation: don't set dynamic to false if you don't have any problems.",
                            "  Dynamic: true"));
                }

                lines.addAll(Arrays.asList("",
                        "# This is useful for bungeecord servers or server with permission systems which do not support *-Perms",
                        "# So if you enable this you can use ab.all instead of ab.* or ab.ban.all instead of ab.ban.*",
                        "# This does not work with negative permissions! e.g. -ab.all would not block all commands for that user.",
                        "EnableAllPermissionNodes: false"));
            }
            if (!mi.contains(mi.getConfig(), "Debug")) {
                lines.addAll(Arrays.asList(
                        "",
                        "# With this active will show more information in the console, such as errors, if",
                        "# the plugin works correctly is not recommended to activate it since it is",
                        "# designed to find bugs.",
                        "Debug: false"));
            }
            if (mi.contains(mi.getConfig(), "Logs Purge Days")) {
                lines.removeAll(Arrays.asList(
                        "",
                        "# This is the amount of days that we should keep plugin logs in the plugins/AdvancedBan/logs folder.",
                        "# By default is set to 10 days.",
                        "Logs Purge Days: 10"
                ));
            }
            if (!mi.contains(mi.getConfig(), "Log Purge Days")) {
                lines.addAll(Arrays.asList(
                        "",
                        "# This is the amount of days that we should keep plugin logs in the plugins/AdvancedBan/logs folder.",
                        "# By default is set to 10 days.",
                        "Log Purge Days: 10"
                ));
            }
            if (!mi.contains(mi.getConfig(), "Disable Prefix")) {
                lines.addAll(Arrays.asList(
                        "",
                        "# Removes the prefix of the plugin in every message.",
                        "Disable Prefix: false"
                ));
            }
            if (!mi.contains(mi.getConfig(), "Friendly Register Commands")) {
                lines.addAll(Arrays.asList("",
                        "# Register commands in a more friendly manner",
                        "# Off by default, so AdvancedBan can override /ban from other plugins",
                        "# This is a Bukkit-specific option. It has no meaning on BungeeCord",
                        "Friendly Register Commands: false"));
            }
            FileUtils.writeLines(file, lines);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void addMessage(String search, String insert, int indexOffset) {
        try {
            File file = new File(Universal.get().getMethods().getDataFolder(), "Messages.yml");
            List<String> lines = FileUtils.readLines(file, "UTF8");
            int index = lines.indexOf(search);
            lines.add(index + indexOffset, insert);
            FileUtils.writeLines(file, "UTF8", lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
