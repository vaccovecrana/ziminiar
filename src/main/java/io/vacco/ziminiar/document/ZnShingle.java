package io.vacco.ziminiar.document;

import java.util.Objects;

public class ZnShingle implements Comparable<ZnShingle> {

  public String token;
  public long weight;

  public static ZnShingle from(String token, long weight) {
    var s = new ZnShingle();
    s.token = Objects.requireNonNull(token);
    s.weight = weight;
    return s;
  }

  @Override public int compareTo(ZnShingle o) {
    return this.token.compareTo(o.token);
  }

  @Override public String toString() {
    return String.format("[%s -> %d]", token, weight);
  }

}
