package io.vacco.ziminiar;

import io.vacco.ziminiar.superminhash.ZnSignatures;
import io.vacco.ziminiar.transform.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.function.Function;
import java.util.stream.Collectors;

import static j8spec.J8Spec.*;
import static org.junit.Assert.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class ZDocumentSignatureTest {

  public static final String d0 = "How are you? I Am fine. ablar ablar xyz blar blar blar blar blar blar blar Thanks.";
  public static final String d1 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar than";
  public static final String d2 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar thank";

  static {
    it("Splits a string into sliding tokens", () -> {
      var tokens = ZnStringShingler.sliding(d0, 4).collect(Collectors.toList());
      assertEquals(60, tokens.size());
    });
    it("Converts tokens into weighted shingles", () -> {
      Function<ZShingle, Long> hashFn = sh -> (long) sh.token.hashCode();
      var sig0 = ZnSignatures.fromDocument(d0, 4, 64, hashFn);
      var sig1 = ZnSignatures.fromDocument(d1, 4, 64, hashFn);
      var sig2 = ZnSignatures.fromDocument(d2, 4, 64, hashFn);

      var sim02 = ZnSignatures.similarity(sig0, sig2);
      var sim12 = ZnSignatures.similarity(sig1, sig2);

      System.out.println(sim02);
      System.out.println(sim12);
    });
  }
}
