package io.vacco.ziminiar;

import io.vacco.ziminiar.document.*;
import io.vacco.ziminiar.superminhash.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.*;
import java.util.function.Function;

import static j8spec.J8Spec.*;
import static org.junit.Assert.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class ZnDocumentTest {

  public static void logBuffer(ZnBuffer b) {
    System.out.println(Arrays.toString(b.fill().hi));
  }

  static {
    var sigLength = 128;
    Function<ZnShingle, Long> hashFn = sh -> (long) sh.token.hashCode();

    it("Computes long document similarities", () -> {
      String d0 = "How are you? I Am fine. ablar ablar xyz blar blar blar blar blar blar blar Thanks.";
      String d1 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar than";
      String d2 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar thank";

      var docPairs = new ArrayList<ZnTestPair>();
      var b0 = new ZnBuffer().init(sigLength);
      var b1 = new ZnBuffer().init(sigLength);
      var b2 = new ZnBuffer().init(sigLength);

      for (int i = 1; i < 8; i++) {
        System.out.println(d0);
        b0 = new ZnDocument(i, hashFn, ZnDocumentTest::logBuffer).update(d0, b0);
        System.out.println(d1);
        b1 = new ZnDocument(i, hashFn, ZnDocumentTest::logBuffer).update(d1, b1);
        System.out.println(d2);
        b2 = new ZnDocument(i, hashFn, ZnDocumentTest::logBuffer).update(d2, b2);

        var sim02 = ZnBuffers.similarity(b0, b2);
        var sim12 = ZnBuffers.similarity(b1, b2);

        System.out.println("-----------------------------");
        System.out.printf("[sl: %d, d0-d2: %.4f]%n", i, sim02);
        System.out.printf("[sl: %d, d1-d2: %.4f]%n", i, sim12);

        docPairs.add(ZnTestPair.from(d0, d2).withSimilarity(sim02));
        docPairs.add(ZnTestPair.from(d1, d2).withSimilarity(sim12));

        b0.clear();
        b1.clear();
        b2.clear();
      }

      assertTrue(docPairs.get(0).similarity > 0.77);
      assertTrue(docPairs.get(1).similarity > 0.95);
      assertTrue(docPairs.get(12).similarity > 0.42);
      assertTrue(docPairs.get(13).similarity > 0.98);
    });

    it("Computes short document similarities", () -> {
      var shingleLength = 1;
      var textPairs = new ArrayList<ZnTestPair>();

      var b0 = new ZnBuffer().init(sigLength);
      var b1 = new ZnBuffer().init(sigLength);

      for (ZnTestPair p : new ZnTestPair[] {
          ZnTestPair.from("abc", ""),
          ZnTestPair.from("", "abc"),
          ZnTestPair.from("", ""),
          ZnTestPair.from("abc", "abc"),
          ZnTestPair.from("abc", "xyz"),
          ZnTestPair.from("night", "nacht"),
          ZnTestPair.from("context", "contact"),
          ZnTestPair.from("ht", "nacht"),
      }) {
        System.out.println(p.d0);
        b0 = new ZnDocument(shingleLength, hashFn, ZnDocumentTest::logBuffer).update(p.d0, b0);
        System.out.println(p.d1);
        b1 = new ZnDocument(shingleLength, hashFn, ZnDocumentTest::logBuffer).update(p.d1, b1);
        var sim = ZnBuffers.similarity(b0, b1);
        System.out.println("-----------------------------");
        System.out.printf("[sl: %d, d0: %s, d1: %s, sim: %.4f]%n", shingleLength, p.d0, p.d1, sim);

        textPairs.add(p.withSimilarity(sim));
        b0.clear();
        b1.clear();
      }
      System.out.println("-----------------------------");

      assertEquals(0.0, textPairs.get(0).similarity, 0.0);
      assertEquals(0.0, textPairs.get(1).similarity, 0.0);
      assertEquals(1.0, textPairs.get(2).similarity, 0.0);
      assertEquals(1.0, textPairs.get(3).similarity, 0.0);

      assertTrue(textPairs.get(4).similarity < 0.025);
      assertTrue(textPairs.get(5).similarity > 0.4);
      assertTrue(textPairs.get(6).similarity > 0.57);
      assertTrue(textPairs.get(7).similarity > 0.35);
    });
  }
}
