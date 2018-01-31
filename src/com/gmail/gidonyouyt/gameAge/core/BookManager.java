package com.gmail.gidonyouyt.gameAge.core;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;

public class BookManager {

	public static ItemStack book(String title, String author, String... pages) {
		ItemStack is = new ItemStack(Material.WRITTEN_BOOK, 1);
		net.minecraft.server.v1_12_R1.ItemStack nmsis = CraftItemStack.asNMSCopy(is);
		NBTTagCompound bd = new NBTTagCompound();
		bd.setString("title", title);
		bd.setString("author", author);
		NBTTagList bp = new NBTTagList();
		for (String text : pages) {
			bp.add(new NBTTagString(text));
		}
		bd.set("pages", bp);
		nmsis.setTag(bd);
		is = CraftItemStack.asBukkitCopy(nmsis);
		return is;
	}
	
	@SuppressWarnings("unchecked")
	public static ItemStack book(String title, String author, IChatBaseComponent... icbcs) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		List<IChatBaseComponent> pages;
		
		//get the pages
		try {
		    pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
		} catch (ReflectiveOperationException ex) {
		    ex.printStackTrace();
		    return null;
		}
		
		for (IChatBaseComponent icbc : icbcs) {
			pages.add(icbc);
		}
		
		bookMeta.setTitle(title);
		bookMeta.setAuthor(author);
		
		//update the ItemStack with this new meta
		book.setItemMeta(bookMeta);
		
		return book;
	}

	public static void openBook(ItemStack book, Player p) {
		int slot = p.getInventory().getHeldItemSlot();
		ItemStack old = p.getInventory().getItem(slot);
		p.getInventory().setItem(slot, book);

		ByteBuf buf = Unpooled.buffer(256);
		buf.setByte(0, (byte) 0);
		buf.writerIndex(1);

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		p.getInventory().setItem(slot, old);
	}
}