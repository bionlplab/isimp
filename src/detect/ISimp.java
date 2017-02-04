package detect;

import java.util.List;
import java.util.Properties;

import adapter.OtherAdapter;
import adapter.ParenthesisAdapter;
import annotator.AppositionAnnotator;
import annotator.CoordinationAnnotator;
import annotator.HypernymyAnnotator;
import annotator.MemberCollectionAnnotator;
import annotator.ParenthesisAnnotator;
import annotator.RelativeClauseAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import extractor.AppositionExtractor;
import extractor.CoordinationExtractor;
import extractor.CoordinationExtractor.AdjpCoordinationExtractor;
import extractor.CoordinationExtractor.AdvpCoordinationExtractor;
import extractor.CoordinationExtractor.NpCoordinationExtractor;
import extractor.CoordinationExtractor.PpCoordinationExtractor;
import extractor.CoordinationExtractor.SenCoordinationExtractor;
import extractor.CoordinationExtractor.VpCoordinationExtractor;
import extractor.HypernymyExtractor;
import extractor.MemberCollectionExtractor;
import extractor.ParenthesisExtractor;
import extractor.RelativeClauseExtractor;
import extractor.RelativeClauseExtractor.FullRelativeClauseExtractor;
import extractor.RelativeClauseExtractor.RedRelativeClauseExtractor;

public class ISimp extends AnnotationPipeline {

  StanfordCoreNLP stanford;

  // POSTaggerAnnotator postagger;
  // ParserAnnotator parser;

  ISimp(ISimpBuilder builder) {
    // tokenizer and splitter
    try {
      Properties properties = new Properties();
      properties.put("annotators", "tokenize, ssplit, pos, parse");
      if (builder.isTokenized) {
        properties.put("tokenize.whitespace", "true");
        properties.put("ssplit.eolonly", "true");
      } else {
        properties.put("tokenize.options", "americanize=false,"
            + "normalizeCurrency=false,"
              + "normalizeFractions=false,"
              // + "normalizeParentheses=false,"
              // + "normalizeOtherBrackets=false,"
              // + "asciiQuotes=false,"
              // + "latexQuotes=false,"
              + "untokenizable=allKeep,"
              + "ptb3Dashes=false,"
              + "escapeForwardSlashAsterisk=false");
      }
      stanford = new StanfordCoreNLP(properties);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // //
    // // POS tagger
    // //
    // try {
    // postagger = new POSTaggerAnnotator("pos", new Properties());
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // //
    // // Parser
    // //
    // try {
    // Properties properties = new Properties();
    // properties.setProperty("parse.type", "stanford");
    // parser = new ParserAnnotator("parse", properties);
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }

    // add annotate
    super.addAnnotator(stanford);
    // super.addAnnotator(postagger);
    // super.addAnnotator(parser);
    if (builder.onlyParser) {
      return;
    }
    // adapter
    super.addAnnotator(new ParenthesisAdapter());
    super.addAnnotator(new OtherAdapter());
    // annotator
    super.addAnnotator(new ParenthesisAnnotator());
    super.addAnnotator(new RelativeClauseAnnotator());
    super.addAnnotator(new AppositionAnnotator());
    super.addAnnotator(new CoordinationAnnotator());
    super.addAnnotator(new MemberCollectionAnnotator());
    super.addAnnotator(new HypernymyAnnotator());
    // extractor
    super.addAnnotator(new ParenthesisExtractor());
    super.addAnnotator(new RelativeClauseExtractor());
    super.addAnnotator(new FullRelativeClauseExtractor());
    super.addAnnotator(new RedRelativeClauseExtractor());
    super.addAnnotator(new AppositionExtractor());
    super.addAnnotator(new CoordinationExtractor());
    super.addAnnotator(new NpCoordinationExtractor());
    super.addAnnotator(new VpCoordinationExtractor());
    super.addAnnotator(new PpCoordinationExtractor());
    super.addAnnotator(new AdjpCoordinationExtractor());
    super.addAnnotator(new AdvpCoordinationExtractor());
    super.addAnnotator(new SenCoordinationExtractor());
    super.addAnnotator(new MemberCollectionExtractor());
    super.addAnnotator(new HypernymyExtractor());
  }

  @Override
  public void annotate(Annotation annotation) {
    super.annotate(annotation);
    // add origin text
    String originText = annotation.get(TextAnnotation.class);
    List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      sentence.set(OriginalTextAnnotation.class, originText);
    }
  }

}
