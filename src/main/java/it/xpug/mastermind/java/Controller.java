package it.xpug.mastermind.java;

import static java.lang.String.*;
import java.io.*;
import javax.servlet.http.*;

public class Controller {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public Controller(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		response.setContentType("application/json");
	}

	protected void do404() throws IOException {
		response.setStatus(404);
		writeBody(toJson("description", "Not Found"));
	}

	protected String toJson(String name, String value) {
		return format("{ \"%s\": \"%s\" }", name, value);
	}

	protected String toJson(String name, int value) {
		return format("{ \"%s\": %s }", name, value);
	}
	
	// metodo aggiunto per poter inserire più valori con relativo nome
	protected String toJson(String[] names, String[] values) {
		String json = "";
		for (int i=0; i<names.length; i++) {
			if (i != 0) {
				json+= "," + format("\"%s\": \"%s\" ", names[i], values[i]);
			} else {
				json+= format("\"%s\": \"%s\" ", names[i], values[i]);
			}
		}
		return "{" + json + "}";
	}

	protected void writeBody(String body) throws IOException {
		response.getWriter().write(body);
	}

}