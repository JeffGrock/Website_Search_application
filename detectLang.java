package first;

import java.io.IOException;
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

    public static boolean verifyLangSupport(String lang) {

        if (lang == "Option.of(en)" || lang == "Option.of(es)" || lang == "Option.of(de)" || lang == "Option.of(ar)"
                || lang == "Option.of(fr)" || lang == "Option.of(zh-tw)") {
            return true;
        }

        return false;
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

        return lang;

    }

}
