package fi.matiaspaavilainen.masuitecore.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaSuiteGUI {

    private Inventory inv;
    private static HashMap<String, MaSuiteGUI> inventories = new HashMap<>();
    private HashMap<Integer, ClickRunnable> runs = new HashMap<>();
    private int currentOpen = 0;
    private boolean registered = false;

    /**
     * Initialize an empty GUI without placeholders
     *
     * @param name name of the GUI
     * @param size size of the GUI
     */
    public MaSuiteGUI(String name, int size) {
        this(name, size, null);
    }


    /**
     * Initialize an empty GUI with placeholders
     *
     * @param name        name of the GUI
     * @param size        size of the GUI
     * @param placeholder {@link ItemStack} to use as placeholder blocks
     */
    public MaSuiteGUI(String name, int size, ItemStack placeholder) {
        if (size == 0) {
            return;
        }
        inv = Bukkit.createInventory(null, size, colorize(name));
        if (placeholder != null) {
            for (int i = 0; i < size; i++) {
                inv.setItem(i, placeholder);
            }
        }
        register();
    }

    /**
     * Set GUI item to inventory
     *
     * @param itemStack      item of the button
     * @param slot           in which slot the item is located
     * @param executeOnClick what happens when player clicks in the menu
     */
    public void setItem(ItemStack itemStack, Integer slot, ClickRunnable executeOnClick) {
        setItem(itemStack, null, slot, executeOnClick);
    }

    /**
     * Set GUI item to inventory
     *
     * @param itemStack      item of the button
     * @param displayName    name of the item
     * @param slot           in which slot the item is located
     * @param executeOnClick what happens when player clicks in the menu
     * @param description    description of the item
     */
    public void setItem(ItemStack itemStack, String displayName, Integer slot, ClickRunnable executeOnClick, String... description) {
        ItemMeta im = itemStack.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        if (displayName != null) {
            im.setDisplayName(colorize(displayName));
        }
        if (description != null) {
            List<String> lore = new ArrayList<>();
            for (String s : description) {
                lore.add(colorize(s));
            }
            im.setLore(lore);
        }
        itemStack.setItemMeta(im);
        inv.setItem(slot, itemStack);
        runs.put(slot, executeOnClick);
    }

    /**
     * Remove item from slot
     *
     * @param slot slot to remove
     */
    public void removeItem(int slot) {
        inv.setItem(slot, new ItemStack(Material.AIR));
    }

    /**
     * Set item to slot
     *
     * @param itemStack item to set
     * @param slot      slot to be used
     */
    public void setItem(ItemStack itemStack, Integer slot) {
        inv.setItem(slot, itemStack);
    }


    /**
     * Register listener
     *
     * @return listener to register
     */
    public static Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent e) {
                if (e.getWhoClicked() instanceof Player) {
                    if (e.getCurrentItem() == null) {
                        return;
                    }
                    if (inventories.containsKey(e.getClickedInventory().getName())) {
                        MaSuiteGUI current = inventories.get(e.getClickedInventory().getName());
                        e.setCancelled(true);
                        Player p = (Player) e.getWhoClicked();
                        if (current.runs.get(e.getSlot()) == null) {
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
                            if (current.runs.get(e.getSlot()) != null) {
                                current.runs.get(e.getSlot()).run(e);
                            }
                        }
                    }

                    if(inventories.containsKey(e.getView().getTopInventory().getName())){
                        e.setCancelled(true);
                    }
                }
            }

            @EventHandler
            public void onClose(InventoryCloseEvent e) {
                if (e.getPlayer() instanceof Player) {
                    Inventory currentinv;
                    if ((currentinv = e.getInventory()) == null) {
                        return;
                    }
                    if (inventories.containsKey(currentinv.getName())) {
                        MaSuiteGUI current = inventories.get(currentinv.getName());
                        current.currentOpen--;
                        if (current.currentOpen == 0) {
                            current.unRegister();
                        }
                    }
                }
            }
        };
    }

    /**
     * Open inventory for player
     *
     * @param player player to use
     */
    public void openInventory(Player player) {
        currentOpen++;
        register();
        player.openInventory(getSourceInventory());
    }

    private void register() {
        if (!registered) {
            inventories.put(inv.getName(), this);
            registered = true;
        }
    }

    private void unRegister() {
        if (registered) {
            inventories.remove(inv.getName());
            registered = false;
        }
    }

    /**
     * Run on click
     */
    public static abstract class ClickRunnable {
        public abstract void run(InventoryClickEvent e);
    }

    /**
     * Close on click
     */
    public static abstract class CloseRunnable {
        public abstract void run(InventoryCloseEvent e);
    }

    /**
     * Colorize a string
     *
     * @param string string to color
     * @return colored string
     */
    private String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * @return the inventory
     */
    public Inventory getSourceInventory() {
        return inv;
    }

    /**
     * @return the size of the inventory
     */
    public int getSize() {
        return inv.getSize();
    }
}
