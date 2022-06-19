package io.vacco.ziminiar.transform;

import java.util.Objects;

public class ZShingle implements Comparable<ZShingle> {

  public String token;
  public long weight;

  public static ZShingle from(String token, long weight) {
    var s = new ZShingle();
    s.token = Objects.requireNonNull(token);
    s.weight = weight;
    return s;
  }

  @Override public int compareTo(ZShingle o) {
    return this.token.compareTo(o.token);
  }

  @Override public String toString() {
    return String.format("[%s -> %d]", token, weight);
  }
}
