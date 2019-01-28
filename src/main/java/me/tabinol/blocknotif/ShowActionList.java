/*
 BlockNotif: Minecraft plugin player action on blocks notification
 Copyright (C) 2013  Michel Blanchet

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.tabinol.blocknotif;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.util.ChatPaginator;

// Show action list has request from a player
public class ShowActionList {

	private BlockNotif blockNotif;
	private CommandSender sender;
	private String playerName;
	private int pageNumber;

	public ShowActionList(CommandSender sender, String playerName, int pageNumber) {

		super();
		blockNotif = BlockNotif.getThisPlugin();
		this.sender = sender;
		this.playerName = playerName;
		this.pageNumber = pageNumber;
	}

	public void show() {

		boolean hasAction = false;
		int t;
		StringBuilder listAction = new StringBuilder();

		for (t = BlockNotif.blockActionList.size() - 1; t >= 0; t--) {
			if (BlockNotif.blockActionList.get(t).getPlayerName().equalsIgnoreCase(playerName)) {
				// sender.sendMessage(blockNotif.blockActionList.get(t).getMessage());
				// Get messages into big String
				listAction.append(BlockNotif.blockActionList.get(t).getMessage()).append('\n');
				hasAction = true;
			}
		}
		if (hasAction == true) {
			sendListAction(listAction.toString(), pageNumber);
		} else {
			sender.sendMessage(blockNotif.messagesTxt.getMessage(MessagesTxt.MESSAGE_NOACTIVITY, null, null));
		}

	}

	// Help from : http://jd.bukkit.org/rb/doxygen/da/dfe/HelpCommand_8java_source.html
	// Echo ActionList to requestor with pagination
	private void sendListAction(String listAction, int pageNumber) {

		int pageHeight;
		int pageWidth;

		if (sender instanceof ConsoleCommandSender) {
			pageHeight = ChatPaginator.UNBOUNDED_PAGE_HEIGHT;
			pageWidth = ChatPaginator.UNBOUNDED_PAGE_WIDTH;
		} else {
			pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1;
			pageWidth = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH;
		}

		ChatPaginator.ChatPage page = ChatPaginator.paginate(listAction, pageNumber, pageWidth, pageHeight);

		sender.sendMessage(page.getLines());
		sender.sendMessage("---(" + page.getPageNumber() + "/" + page.getTotalPages() + ")---");

	}
}
