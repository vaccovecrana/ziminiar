package io.vacco.ziminiar.superminhash;

import java.util.*;
import java.util.function.Function;

public class ZnPipeline<T> implements ZnStage<T> {

  private final List<ZnStageMapper<T, ?>> mappers = new ArrayList<>();

  public <V> ZnPipeline<T> withStage(ZnStage<V> stage, Function<T, V> mapFn) {
    var m = new ZnStageMapper<>(stage, mapFn);
    mappers.add(m);
    return this;
  }

  @Override public ZnBuffer update(T in, ZnBuffer b) {
    b.clear();
    for (ZnStageMapper<T, ?> m : mappers) {
      b = m.update(in, b);
    }
    return b.fill();
  }
}
