package first;

import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.coref.data.Document;
import edu.stanford.nlp.ling.CoreLabel;

public class DocumentAnalyzer {
    private HashSet<String> searchTerms;
    private static StanfordCoreNLP tagger;

    public DocumentAnalyzer() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");
        tagger = new StanfordCoreNLP(props);
        searchTerms = new HashSet<String>();
    }

    public boolean areTermsPresent(HashSet<String> propNounSet) {
        return !Collections.disjoint(searchTerms, propNounSet);
    }

    public void addSearchTerm(String company) {
        searchTerms.add(company);
    }

    public static HashSet<String> getPropNouns(String input) {
        CoreDocument document = tagger.processToCoreDocument(input);
        HashSet<String> propNounSet = new HashSet<String>();
        List<String> propNounList = new ArrayList<String>();
        for (CoreLabel tok : document.tokens()) {
            if (tok.tag() == "NNP") {
                propNounList.add(tok.word());
            } else {
                if (!propNounList.isEmpty()) {
                    propNounSet.add(StringUtils.join(propNounList, " "));
                    propNounList.clear();
                }
            }
        }
        if (!propNounList.isEmpty()) {
            propNounSet.add(StringUtils.join(propNounList, " "));
            propNounList.clear();
        }
        return propNounSet;
    }

    public static String tagPos(String input) {
        CoreDocument document = tagger.processToCoreDocument(input);

        String output = "";
        for (CoreLabel tok : document.tokens()) {
            output += tok.word() + "_" + tok.tag() + " ";
        }
        return output;
    }

    public static HashSet<String> extractProperNouns(String taggedOutput) {
        HashSet<String> propNounSet = new HashSet<String>();
        String[] split = taggedOutput.split(" ");
        List<String> propNounList = new ArrayList<String>();

        for (String token : split) {
            String[] splitTokens = token.split("_");
            if (splitTokens[1].equals("NNP")) {
                propNounList.add(splitTokens[0]);
            } else {
                if (!propNounList.isEmpty()) {
                    propNounSet.add(StringUtils.join(propNounList, " "));
                    propNounList.clear();
                }
            }
        }
        if (!propNounList.isEmpty()) {
            propNounSet.add(StringUtils.join(propNounList, " "));
            propNounList.clear();
        }
        return propNounSet;
    }

    public boolean analyzeArticle(String UrlString) throws IOException, SAXException, BoilerpipeProcessingException {

        String articleText = Extractor.extractFromUrl(UrlString);
        String tagged = tagPos(articleText);
        HashSet<String> properNounsSet = extractProperNouns(tagged);
        return areTermsPresent(properNounsSet);
    }
}
