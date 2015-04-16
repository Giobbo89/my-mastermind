package it.xpug.mastermind.java;

import static java.lang.Integer.*;
import it.xpug.generic.db.*;
import it.xpug.generic.web.*;

public class Main {

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port == null || port.isEmpty()) {
			port = "8080";
		}

		ReusableJettyApp app = new ReusableJettyApp(new IndexServlet());
		app.start(valueOf(port), "src/main/webapp");
	}

}
