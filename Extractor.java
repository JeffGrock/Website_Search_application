package first;

import java.net.URL;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

/*
Class that uses the boilerpipe library to reduce clutter from the requested URL
supplies the analyzer class with the string of words to search for
*/
public class Extractor {

    public static String extractFromUrl(String userUrl)
            throws java.io.IOException, org.xml.sax.SAXException, de.l3s.boilerpipe.BoilerpipeProcessingException {

        final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(userUrl));
        final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
        return CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);

    }
}