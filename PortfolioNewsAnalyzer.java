package first;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;

import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.StringUtils;

public class PortfolioNewsAnalyzer {
    private HashSet<String> portfolio;

    // "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
    private static final String modelPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

    private MaxentTagger tagger;

    public PortfolioNewsAnalyzer() {
        tagger = new MaxentTagger(modelPath);
        portfolio = new HashSet<String>();
    }

    public void addPortfolioCompany(String company) {
        portfolio.add(company);
    }

    public boolean areCompaniesMentioned(HashSet<String> propNounSet) {
        return !Collections.disjoint(portfolio, propNounSet);
    }

    public String tagPos(String input) {
        return tagger.tagString(input);
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
        return areCompaniesMentioned(properNounsSet);
    }
}
