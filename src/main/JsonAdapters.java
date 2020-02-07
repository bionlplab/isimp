package main;

import annotation.ISimpAnnotations.ISimpAnnotation;
import com.google.gson.annotations.SerializedName;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import extractor.SimplificationConstruct;
import extractor.SimplificationConstruct.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonAdapters {

  public static class SentenceAdapter {

    @SerializedName("TYPE")
    final String type;
    @SerializedName("TEXT")
    final String text;
    @SerializedName("FROM")
    final int begin;
    @SerializedName("TO")
    final int end;
    @SerializedName("POS ")
    final String pos;
    @SerializedName("TREE")
    final String tree;
    @SerializedName("SIMP")
    final List<ConstructAdapter> constructs;

    SentenceAdapter(CoreMap sentence) {
      type = "sentence";
      text = sentence.get(TextAnnotation.class);
      begin = sentence.get(CharacterOffsetBeginAnnotation.class);
      end = sentence.get(CharacterOffsetEndAnnotation.class);
      StringBuilder sb = new StringBuilder();
      for (CoreMap tok : sentence.get(TokensAnnotation.class)) {
        sb.append(tok.get(TextAnnotation.class))
                .append('_')
                .append(tok.get(PartOfSpeechAnnotation.class))
                .append(' ');
      }
      pos = sb.toString();
      tree = sentence.get(TreeAnnotation.class).toString();
      constructs = new ArrayList<>();
      if (sentence.get(ISimpAnnotation.class) != null) {
        for (SimplificationConstruct construct : sentence.get(ISimpAnnotation.class)) {
          constructs.add(new ConstructAdapter(construct, sentence.get(OriginalTextAnnotation.class)));
        }
      }
    }
  }

  public static class ConstructAdapter {

    @SerializedName("TYPE")
    final String type;
    @SerializedName("TEXT")
    final String text;
    @SerializedName("FROM")
    final int begin;
    @SerializedName("TO")
    final int end;
    @SerializedName("COMP")
    final List<ComponentAdapter> components;

    ConstructAdapter(SimplificationConstruct construct, String originalText) {
      type = construct.annotation().name();
      text = originalText.substring(
              construct.beginPosition(),
              construct.endPosition());
      begin = construct.beginPosition();
      end = construct.endPosition();
      components = new ArrayList<>();
      for (Component comp : construct.components()) {
        components.add(new ComponentAdapter(comp));
      }
    }
  }

  public static class ComponentAdapter {

    @SerializedName("TYPE")
    final String type;
    @SerializedName("FROM")
    final int begin;
    @SerializedName("TO")
    final int end;

    public ComponentAdapter(Component comp) {
      type = comp.annotation().name();
      begin = comp.beginPosition();
      end = comp.endPosition();
    }
  }
}
