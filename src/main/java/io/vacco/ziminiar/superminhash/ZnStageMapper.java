package io.vacco.ziminiar.superminhash;

import java.util.Objects;
import java.util.function.Function;

public class ZnStageMapper<T, V> implements ZnStage<T> {

  public final ZnStage<V> stage;
  public final Function<T, V> mapFn;

  public ZnStageMapper(ZnStage<V> stage, Function<T, V> mapFn) {
    this.stage = Objects.requireNonNull(stage);
    this.mapFn = Objects.requireNonNull(mapFn);
  }

  @Override public ZnBuffer update(T in, ZnBuffer b) {
    return stage.update(mapFn.apply(in), b);
  }
}
