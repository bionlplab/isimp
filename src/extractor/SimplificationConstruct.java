package extractor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Range;

import annotation.ISimpAnnotations.SimplificationAnnotation;
import edu.stanford.nlp.ling.HasOffset;

/**
 * [name=simplification construct, list=<name=component,from,to>]
 * 
 * @author Yifan Peng
 * @version Oct 24, 2013
 * 
 */
// List<Pair<String, List<Triple<String, Integer, Integer>>>>
public class SimplificationConstruct implements HasOffset {

  private final SimplificationAnnotation annotation;

  private final List<Component>          components;

  SimplificationConstruct(SimplificationAnnotation annotation) {
    this.annotation = annotation;
    components = new LinkedList<Component>();
  }

  public
      void
      addComponent(SimplificationAnnotation annotation, int from, int to) {
    addComponent(annotation, Range.between(from, to));
  }

  public void addComponent(SimplificationAnnotation annotation,
      Range<Integer> range) {
    Component construct = new Component(annotation, range);
    components.add(construct);
  }

  public final List<Component> components() {
    return Collections.unmodifiableList(components);
  }

  public final SimplificationAnnotation annotation() {
    return annotation;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('[').append(annotation.name()).append(',').append(components)
        .append(']');
    return sb.toString();
  }

  @Override
  public int beginPosition() {
    if (components.isEmpty()) {
      return -1;
    }
    int from = components.get(0).beginPosition();
    for (Component c : components) {
      from = Math.min(from, c.beginPosition());
    }
    return from;
  }

  @Override
  public void setBeginPosition(int beginPos) {
    throw new UnsupportedOperationException("cannot change the range");
  }

  @Override
  public int endPosition() {
    if (components.isEmpty()) {
      return -1;
    }
    int to = components.get(0).endPosition();
    for (Component c : components) {
      to = Math.max(to, c.endPosition());
    }
    return to;
  }

  @Override
  public void setEndPosition(int endPos) {
    throw new UnsupportedOperationException("cannot change the range");
  }

  @SuppressWarnings("serial")
  public static class Component implements HasOffset {

    final SimplificationAnnotation annotation;
    final Range<Integer>           range;

    Component(SimplificationAnnotation annotation, Range<Integer> range) {
      this.annotation = annotation;
      this.range = range;
    }

    public final SimplificationAnnotation annotation() {
      return annotation;
    }

    public final Range<Integer> range() {
      return range;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('<').append(annotation.name()).append(",range=").append(range)
          .append('>');
      return sb.toString();
    }

    public int length() {
      return endPosition() - beginPosition();
    }

    @Override
    public int beginPosition() {
      return range.getMinimum();
    }

    @Override
    public void setBeginPosition(int beginPos) {
      throw new UnsupportedOperationException("cannot change the range");
    }

    @Override
    public int endPosition() {
      return range.getMaximum();
    }

    @Override
    public void setEndPosition(int endPos) {
      throw new UnsupportedOperationException("cannot change the range");
    }
  }
}
