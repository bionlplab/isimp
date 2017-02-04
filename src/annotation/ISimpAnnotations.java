package annotation;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.ErasureUtils;
import extractor.SimplificationConstruct;

public class ISimpAnnotations {

  public static class ISimpAnnotation implements
      CoreAnnotation<List<SimplificationConstruct>> {

    @Override
    public Class<List<SimplificationConstruct>> getType() {
      return ErasureUtils.uncheckedCast(List.class);
    }

  }

  public static class ISimpSimplifiedAnnotation implements
      CoreAnnotation<List<Tree>> {

    @Override
    public Class<List<Tree>> getType() {
      return ErasureUtils.uncheckedCast(List.class);
    }

  }

  public static interface SimplificationAnnotation extends
      CoreAnnotation<Integer> {

    public String name();

    public String simpleName();
  }

  public static class ParenthesisAnnotation implements SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "parenthesis";
    }

    @Override
    public String simpleName() {
      return "BRAC";
    }
  }

  public static class ParenthesisRefAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "referred noun phrase";
    }

    @Override
    public String simpleName() {
      return "REFN";
    }
  }

  public static class ParenthesisElemAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "parenthesized elements";
    }

    @Override
    public String simpleName() {
      return "ELEM";
    }
  }

  public static class RelativeClauseAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "relative clause";
    }

    @Override
    public String simpleName() {
      return "REL";
    }
  }

  public static class FullRelativeClauseAnnotation extends
      RelativeClauseAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "full relative clause";
    }

    @Override
    public String simpleName() {
      return "FREL";
    }
  }

  public static class RedRelativeClauseAnnotation extends
      RelativeClauseAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "reduced relative clause";
    }

    @Override
    public String simpleName() {
      return "RREL";
    }
  }

  public static class RelativeClauseRefAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "referred noun phrase";
    }

    @Override
    public String simpleName() {
      return "REFN";
    }
  }

  public static class RelativeClauseClauseAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "clause";
    }

    @Override
    public String simpleName() {
      return "CLAU";
    }
  }

  public static class AppositionAnnotation implements SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "apposition";
    }

    @Override
    public String simpleName() {
      return "APPN";
    }
  }

  public static class AppositionRefAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "referred noun phrase";
    }

    @Override
    public String simpleName() {
      return "REFN";
    }
  }

  public static class AppositionAppositiveAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "appositive";
    }

    @Override
    public String simpleName() {
      return "APPV";
    }
  }

  public static class CoordinationAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "coordination";
    }

    @Override
    public String simpleName() {
      return "COO";
    }
  }

  public static class NpCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "noun or noun phrase coordination";
    }

    @Override
    public String simpleName() {
      return "NCOO";
    }
  }

  public static class VpCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "verb or verb phrase coordination";
    }

    @Override
    public String simpleName() {
      return "VCOO";
    }
  }

  public static class PpCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "prep or prep phrase coordination";
    }

    @Override
    public String simpleName() {
      return "PCOO";
    }
  }

  public static class SenCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "sentence coordination";
    }

    @Override
    public String simpleName() {
      return "SCOO";
    }
  }

  public static class AdjpCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "adj or adj phrase coordination";
    }

    @Override
    public String simpleName() {
      return "JCOO";
    }
  }

  public static class AdvpCoordinationAnnotation extends CoordinationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "adj or adj phrase coordination";
    }

    @Override
    public String simpleName() {
      return "RCOO";
    }
  }

  public static class CoordinationConjunctAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "conjunct";
    }

    @Override
    public String simpleName() {
      return "CONJ";
    }
  }

  public static class CoordinationConjunctionAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "conjunction";
    }

    @Override
    public String simpleName() {
      return "CC";
    }
  }

  public static class MemberCollectionAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "member-collection";
    }

    @Override
    public String simpleName() {
      return "MEM-COL";
    }
  }

  public static class MemberCollectionMemberAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "member";
    }

    @Override
    public String simpleName() {
      return "MEMB";
    }
  }

  public static class MemberCollectionCollectionAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "collection";
    }

    @Override
    public String simpleName() {
      return "COLL";
    }
  }

  public static class HypernymyAnnotation implements SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "hypernymy";
    }

    @Override
    public String simpleName() {
      return "HYP";
    }
  }

  public static class HypernymyHypernymAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "hypernym";
    }

    @Override
    public String simpleName() {
      return "HYPE";
    }
  }

  public static class HypernymyHyponymAnnotation implements
      SimplificationAnnotation {

    @Override
    public Class<Integer> getType() {
      return Integer.class;
    }

    @Override
    public String name() {
      return "hyponym";
    }

    @Override
    public String simpleName() {
      return "HYPO";
    }
  }
}
