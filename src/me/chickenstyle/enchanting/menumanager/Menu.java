package me.chickenstyle.enchanting.menumanager;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Menu {
	private Inventory inv;
	private HashMap<Integer,Button> buttons;

	
	public Menu(Inventory inv) {
		this.inv = inv;
		buttons = new HashMap<>();
	}
	
	public void setButton(int slot,Button button) {
		buttons.put(slot, button);
	}
	
	public void performClick(Menu menu,InventoryClickEvent event) {
		
		if (buttons.get(event.getSlot()) != null) {
			buttons.get(event.getSlot()).onClick(menu, event);

		}
		
	}
	
	private void loadButtons() {
		buttons.forEach((slot,button) -> {
			inv.setItem(slot, button);
		});
	}

	protected HashMap<Integer,Button> getButtons() {
		return buttons;
	}
	
	protected void open(Player player) {
		loadButtons();
		player.openInventory(inv);
	}
	
	public Inventory getInventory() {
		return inv;
	}

}
