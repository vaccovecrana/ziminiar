package io.vacco.ziminiar.superminhash;

public interface ZnStage<T> {

  ZnBuffer update(T in, ZnBuffer b);

}
