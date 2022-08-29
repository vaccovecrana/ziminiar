package io.vacco.ziminiar.superminhash;

public interface ZnStage<V> {

  ZnBuffer update(V in, ZnBuffer b);

}
