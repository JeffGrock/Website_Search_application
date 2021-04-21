package first;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

public class detectLang {

    public static String[] langs;

    public detectLang() {
        langs = new String[] { "english", "spanish", "french", "german", "arabic", "chinese" };
    }

    public static boolean verifyLangSupport(String lang) {
        List<String> temp = Arrays.asList(langs);
        boolean isSupported = temp.contains(lang);
        return isSupported;
    }

    public static String getLangFromText(String URL) throws IOException, SAXException, BoilerpipeProcessingException {

        String source = Extractor.extractFromUrl(URL);

        List<LanguageProfile> languageProfiles = new LanguageProfileReader().readAllBuiltIn();

        LanguageDetector languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles).build();
        TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();

        TextObject textObject = textObjectFactory.forText(source);
        Optional<LdLocale> newLang = languageDetector.detect(textObject);

        String lang = newLang.toString();
        switch (lang) {
        case "Optional.of(en)":
            lang = "english";
            break;
        case "Optional.of(es)":
            lang = "spanish";
            break;
        case "Optional.of(de)":
            lang = "german";
            break;
        case "Optional.of(ar)":
            lang = "arabic";
            break;
        case "Optional.of(fr)":
            lang = "french";
            break;
        case "Optional.of(zh-tw)":
            lang = "chinese";
        default:
            lang = "unsupported";
            break;
        }

        return lang;

    }

}
