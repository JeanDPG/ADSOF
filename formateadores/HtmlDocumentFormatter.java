package formateadores;

import documentos.IDocumento;
import java.util.List;
import java.util.Map;

public class HtmlDocumentFormatter {

    public String format(IDocumento documento) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"es\">\n");
        html.append("<head>\n");
        html.append("<title>").append(documento.getSeccionPrincipal()).append("</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<h1>").append(documento.getSeccionPrincipal()).append("</h1>\n");

        for (String parrafo : documento.getParrafos()) {
            html.append("<p>").append(parrafo).append("</p>\n");
        }

        for (Map.Entry<String, List<String>> entry : documento.getColecciones().entrySet()) {
            html.append("<p>").append(entry.getKey()).append("</p>\n"); // Title for the list
            html.append("<ul>\n");
            for (String item : entry.getValue()) {
                html.append("<li>").append(item).append("</li>\n");
            }
            html.append("</ul>\n");
        }

        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}
