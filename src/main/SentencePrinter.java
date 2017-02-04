package main;

import main.JsonAdapters.ComponentAdapter;
import main.JsonAdapters.ConstructAdapter;
import main.JsonAdapters.SentenceAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;

public abstract class SentencePrinter {

  boolean isPrettyPrinting;

  private SentencePrinter(boolean isPrettyPrinting) {
    this.isPrettyPrinting = isPrettyPrinting;
  }

  public abstract String get(CoreMap sentence);


  protected static class JsonPrinter extends SentencePrinter {

    JsonPrinter(boolean isPrettyPrinting) {
      super(isPrettyPrinting);
    }

    private static Gson gson = null;

    @Override
    public String get(CoreMap sentence) {
      if (gson == null) {
        GsonBuilder builder = new GsonBuilder();
        if (isPrettyPrinting) {
          builder = builder.setPrettyPrinting();
        }
        gson = builder.create();
      }
      return gson.toJson(new SentenceAdapter(sentence));
    }

  }

  protected static class PlainPrinter extends SentencePrinter {

    PlainPrinter(boolean isPrettyPrinting) {
      super(isPrettyPrinting);
    }

    private static Plain plain = null;

    @Override
    public String get(CoreMap sentence) {
      if (plain == null) {
        PlainBuilder builder = new PlainBuilder();
        if (isPrettyPrinting) {
          builder = builder.setPrettyPrinting();
        }
        plain = builder.create();
      }
      return plain.toPlain(new SentenceAdapter(sentence));
    }

    private class PlainBuilder {

      private boolean isPrettyPrinting;

      PlainBuilder() {
        isPrettyPrinting = false;
      }

      PlainBuilder setPrettyPrinting() {
        isPrettyPrinting = true;
        return this;
      }

      Plain create() {
        return new Plain(this);
      }
    }

    class Plain {

      private final PlainBuilder builder;
      private final int          width = 80;

      Plain(PlainBuilder builder) {
        this.builder = builder;
      }

      String toPlain(SentenceAdapter adapter) {
        StringBuilder sb = new StringBuilder();
        String line;

        sb.append("TYPE: ").append(adapter.type);
        // loc
        sb.append(loc(adapter.begin, adapter.end)).append('\n');
        // text
        line = "TEXT: " + adapter.text;
        if (builder.isPrettyPrinting) {
          line = autoformat(line, 6);
        }
        sb.append(line).append('\n');
        // pos
        line = "POS : " + adapter.pos;
        if (builder.isPrettyPrinting) {
          line = autoformat(line, 6);
        }
        sb.append(line).append('\n');
        // tree
        line = "TREE: " + adapter.tree;
        if (builder.isPrettyPrinting) {
          line = autoformat(line, 6);
        }
        sb.append(line).append('\n');
        // construct
        for (ConstructAdapter construct : adapter.constructs) {
          sb.append("SIMP:\n");
          // construct
          sb.append(indent(2)).append("TYPE: ").append(construct.type);
          // loc
          sb.append(loc(construct.begin, construct.end)).append('\n');
          // text
          line = indent(2) + "TEXT: " + construct.text;
          if (builder.isPrettyPrinting) {
            line = autoformat(line, 2 + 6);
          }
          sb.append(line).append('\n');
          // component
          for (ComponentAdapter component : construct.components) {
            sb.append(indent(2)).append("COMP: ").append(component.type);
            sb.append(loc(component.begin, component.end)).append('\n');
          }
        }
        return sb.toString();
      }

      private String autoformat(String line, int indent) {
        StringBuilder sb = new StringBuilder(line.substring(0, indent));
        // Needed to handle last line correctly
        line = line.substring(indent) + "\n";
        line = line.replaceAll(
            "(.{1," + (width - indent) + "})\\s+",
            indent(indent) + "$1\n");
        return sb.append(line.trim()).toString();
      }

      private String loc(int from, int to) {
        return new StringBuilder().append('[').append(from).append("..")
            .append(to).append(']').toString();
      }

      String indent(int indent) {
        return StringUtils.repeat(' ', indent);
      }
    }
  }
}
