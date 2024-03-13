package scraping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class webScraper {
	public static void main(String[] args) {
		try {
			// URL de la página web que queremos scrapear
			String url = "https://principedelcrochet.com/collections/all";

			// Realizar una solicitud HTTP para obtener el contenido de la página
			Document doc = Jsoup.connect(url).get();

			// Seleccionar los elementos de interés utilizando selectores CSS o expresiones
			// jQuery
			Elements resultado = doc.select("div.card-list.grid");
			Elements elementos = doc.select("*");

			// System.out.println(resultado);
			// System.out.println(resultado.select("div.card.critical-clear").first().outerHtml());
			for (Element card : resultado.select("div.card.critical-clear")) {
				// System.out.println(card.outerHtml());

				// img
				System.out.println("htpps:" + card.select("img").attr("src"));
				// nombre
				System.out.println(card.select("h3.card__name.h4").html());
				// precio
				System.out.println(card.select("div.card__price").html());
				System.out.println();
			}

			// Crear un archivo HTML
			BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.html"));
			writer.write("<!DOCTYPE html>");
			writer.write("<html>");
			writer.write("<head>");
			writer.write("<meta charset=\"UTF-8\">");
			writer.write("<title>Resultados de Web Scraping</title>");
			writer.write("<style>.micard {\r\n"
					+ "    display: inline-block;\r\n"
					+ "    margin: 10px;\r\n"
					+ "    border-radius: 10px;\r\n"
					+ "    padding: 10px;\r\n"
					+ "    background: #9cf;\r\n"
					+ "}</style>");
			writer.write("</head>");

			writer.write("<body>");
			writer.write("<h1>Resultados de Web Scraping</h1>");
			
			String src = "";

			for (Element card : resultado.select("div.card.critical-clear")) {
				// System.out.println(card.outerHtml());
				writer.write("<div class='micard'>");
				// img
				src = "https:" + card.select("img").attr("src");
				writer.write("<img src='" + src + "'/>");
				// nombre
				writer.write("<h3>" + card.select("h3.card__name.h4").html() + "</h3>");
				// precio
				writer.write("<h4>" + card.select("div.card__price").html() + "</h4>");
				writer.write("</div>");
			}

			writer.write("<ul>");
			for(Element elemento: elementos) {
				if(!elemento.className().equals("")) {
					writer.write("<li>" + elemento.tagName() + "." + elemento.className() + "</li>");
				}
			}
			writer.write("</ul>");
			
			writer.write("</body>");
			writer.write("</html>");

			// Cerrar el archivo
			writer.close();

			System.out.println("Archivo HTML creado con éxito.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}