package formateadores;

import documentos.IDocumento;
import java.util.List;
import java.util.Map;

public class MarkdownDocumentFormatter {

    public String format(IDocumento documento) {
        StringBuilder markdown = new StringBuilder();

        markdown.append("# ").append(documento.getTitulo()).append("\n\n");
        markdown.append("## ").append(documento.getSeccionPrincipal()).append("\n");

        for (String parrafo : documento.getParrafos()) {
            markdown.append(parrafo).append("\n");
        }
        markdown.append("\n"); // Add an extra newline for separation

        for (Map.Entry<String, List<String>> entry : documento.getColecciones().entrySet()) {
            markdown.append("### ").append(entry.getKey()).append("\n"); // Title for the list
            for (String item : entry.getValue()) {
                markdown.append("- ").append(item).append("\n");
            }
            markdown.append("\n"); // Add an extra newline for separation
        }

        return markdown.toString();
    }
}
