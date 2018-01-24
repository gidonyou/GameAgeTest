package com.gmail.gidonyouyt.gameAge;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.bukkit.entity.Player;

import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebManager {

	public static void start() throws Exception {
		// Double Check
		// if (GameSettings.WEBPAGE_ON.value() != 1)
		// return;

		int port = (int) GameSettings.WEBPAGE_PORT.value();

		System.out.println("WEBUI : Standby... Port: " + port);
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			if (GameSettings.WEBPAGE_DEBUG.value() == 1)
				System.out.println("WEB UI DEBUG : ASKED REQUEST");

			t.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");

			String response = "<title>Hello World</title>\n<meta http-equiv=\"Refresh\" content=\"1\">";
			if (GameStatus.getStatus() == GameStatus.RUNNING) {
				response += "<body>";

				for (int i = 0; i < Sequence.getRankList().length; i++) {
					Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[i]);

					if (NextPlayers.isEmpty()) {
						response += "<p style=\"color: red; margin: 2px;\">ì—†ìŒ</p>";
					} else {
						for (Player p : NextPlayers) {
							if (p == null) {
								response += "<p style=\"color: red; margin: 2px;\">" + i + "ì• ëŸ¬</p>";
								continue;
							}
							response += "<p style=\"margin: 2px;\">" + i + ". " + p.getName()
									+ " (%s)".replace("%s", String.valueOf(p.getHealth())) + "  "
									+ Sequence.toMinute(Sequence.getPlayerTime().get(p)) + "</p>";
						}
					}
				}

				response += "</body>";

			} else {
				response += "<body><h1 style=\"color: red\">ê²Œìž„ ì‹œìž‘ ì•ˆí•¨</h1></body>";
			}
			byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
			t.sendResponseHeaders(200, bytes.length);
			OutputStream os = t.getResponseBody();
			os.write(bytes);
			os.close();
		}
	}

}
