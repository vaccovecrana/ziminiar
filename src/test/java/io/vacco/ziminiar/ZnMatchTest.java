package io.vacco.ziminiar;

import io.vacco.ziminiar.document.ZShingle;
import io.vacco.ziminiar.document.ZnShingles;
import io.vacco.ziminiar.superminhash.ZnBuffers;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.function.Function;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class ZnMatchTest {
  static {

    var headLineIdx = new HashMap<String, List<String>>() {{
      put("AWS launched a new AI agent platform specifically for healthcare.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable."));

      put("The US is reportedly considering sweeping new chip export controls.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems."));

      put("Amazon is rolling out a redesigned Fire TV app.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems."));

      put("BYD rolled out EV batteries with 5-minute flash charging.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems."));

      put("Italian prosecutors confirmed a journalist was hacked with Paragon spyware.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems."));

      put("The Pentagon labeled Anthropic a supply-chain risk.",
        List.of("Anthropic will challenge the Department of Defense's supply-chain label in court."));

      put("Hardware testing startup Nominal hit a $1B valuation after raising $155M in 10 months.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable."));

      put("Roblox launched real-time AI chat rephrasing to filter out banned language.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable."));

      put("Impersonators are targeting companies with fake TechCrunch outreach.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems."));

      put("Luma launched creative AI agents powered by its new Unified Intelligence models.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable.",
          "X revamped Creator Subscriptions with new features, including exclusive threads and shareable cards."));

      put("Google reported that half of all zero-days it tracked in 2025 targeted buggy enterprise tech.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems.",
          "Cluely CEO Roy Lee admitted to publicly lying about revenue numbers last year."));

      put("Cursor is rolling out a new kind of agentic coding tool.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable.",
          "X revamped Creator Subscriptions with new features, including exclusive threads and shareable cards."));

      put("Meta is sued over AI smart glasses' privacy concerns after workers reviewed nudity, sex, and other footage.",
        List.of("The FBI is investigating a hack on its wiretap and surveillance systems.",
          "DiligenceSquared uses AI and voice agents to make M&A research affordable.",
          "Anthropic will challenge the Department of Defense's supply-chain label in court."));

      put("Anthropic CEO Dario Amodei could still be trying to make a deal with the Pentagon.",
        List.of("DiligenceSquared uses AI and voice agents to make M&A research affordable.",
          "Anthropic will challenge the Department of Defense's supply-chain label in court."));

      put("Israel claims war against Iran is in decisive phase.",
        List.of("Iran considers US military sites in UAE legitimate targets."));

      put("Iran, Israel, and US trade threats as war rattles global markets.",
        List.of("US defense chief says Iran's new leader is wounded and likely disfigured."));

      put("White House is split as Trump mulls victory claim in widening Iran war.",
        List.of("US defense chief says Iran's new leader is wounded and likely disfigured."));

      put("Iran's new supreme leader issues first address to nation amid war.",
        List.of("US defense chief says Iran's new leader is wounded and likely disfigured."));

      put("Airstrikes on Iran-aligned PMF in western Iraq kill at least 30.",
        List.of("One person is dead and two oil tankers are set ablaze off Iraq in likely Iranian attack."));

      put("Israeli strikes pound central Beirut again, killing at least 8.",
        List.of("Pakistani strikes kill 6 and injure more in Kabul and eastern provinces."));

      put("Iran targets at least 25 US sites in Middle East during war.",
        List.of("Iran considers US military sites in UAE legitimate targets."));

      put("Iran's Khamenei is reported injured but safe after days out of public view.",
        List.of("US defense chief says Iran's new leader is wounded and likely disfigured.",
          "UN Security Council urges Iran to halt attacks on Gulf states."));

      put("Romania allows US aircraft involved in Iran war to use its air bases.",
        List.of("Iran considers US military sites in UAE legitimate targets."));

      put("US probe finds American forces behind strike on Iranian elementary school.",
        List.of("Romania allows US aircraft involved in Iran war to use its air bases."));

      put("Syria reaffirms resolve at UN to destroy Assad-era chemical weapons.",
        List.of("UN Security Council urges Iran to halt attacks on Gulf states."));

      put("At least 4 are injured when Iranian drones hit near Dubai airport.",
        List.of("Romania allows US aircraft involved in Iran war to use its air bases."));

      put("Syria appoints assistant defense minister for eastern region.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("Germany is concerned US and Israel lack clear plan to end Iran war.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("Israeli forces storm occupied West Bank's Balata refugee camp.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("Iran continues to strike Gulf and block Hormuz to mount US pressure.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("Iran bets on endurance and energy disruption to outlast US and Israel.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("US orders non-essential staff to leave Saudi Arabia amid Iran war.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("US envoys call off Israel trip as strikes trigger Iran retaliation.",
        List.of("US urges Israel to avoid strikes on Iran's energy facilities."));

      put("Iran launches the 27th wave of attacks against Israel and the US.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran warns that the US uses regional countries' soil to attack it.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Nearly 200 Iranian students and teachers have died in US attacks.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran denounces the world's complicit silence before the US aggression.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iranian armed forces reiterate their respect for the national sovereignty of neighbors.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran shoots down and injures more than 220 US military personnel in a single day.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites.",
          "Nearly 200 Iranian students and teachers have died in US attacks."));

      put("Iran initiates the 25th wave of military operations against the US and Israel.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran launches a massive attack with drones against a US air base in the Emirates.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("President: enemies will bury their dream of seeing Iran subdued.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors.",
          "Iran condemns US attacks on civilian, sports, and airport sites."));

      put("CGRI of Iran attacks positions of separatist groups.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran criticizes the UN chief for minimizing US and Israel atrocities.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran attacks an oil tanker infractor that sought to cross the Strait of Hormuz with a drone.",
        List.of("CGRI of Iran attacks positions of separatist groups.",
          "Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran Navy drones attack US bases again.",
        List.of("Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iran denies enemy lies: bombings affect the civilian population.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors.",
          "Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Pezeshkian to Putin: Iran will continue the path of \"firm defense\" and lasting peace.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors."));

      put("Iran to Europe: Any country that joins the US and Israel is a target.",
        List.of("Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\"",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Iran, committed to regional peace, but says it will not hesitate in defense.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors.",
          "Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\""));

      put("Iran to US allies after aircraft carrier escape: \"Do not link your security to Trump lies.\"",
        List.of("Iran to Europe: Any country that joins the US and Israel is a target.",
          "Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\"",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Iran destroys THAAD and US radar in the Emirates, Jordan, and region; blinds their eyes.",
        List.of("Iran to Europe: Any country that joins the US and Israel is a target.",
          "Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\"",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Cuba condemns US-Israel attack on Iran; Araqchi assures \"decisive\" response to aggression.",
        List.of("Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship.",
          "Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Iranian Army drones attack US bases in Kuwait.",
        List.of("Cuba condemns US-Israel attack on Iran; Araqchi assures \"decisive\" response to aggression.",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Iran aims at Israel with powerful Jorramshahr-4, Jeibar, and Fatah missiles.",
        List.of("Iran to Europe: Any country that joins the US and Israel is a target.",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Iran: attack on patients and children violates human principles; the world must condemn.",
        List.of("Iran, \"the only force left against evil,\" reiterates Baqai.",
          "CGRI of Iran attacks positions of separatist groups.",
          "Iran condemns US attacks on civilian, sports, and airport sites."));

      put("Chancellor: Iranian attacks do not target neighbors but the source of aggression, the US.",
        List.of("Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Iran reports 650 US military dead and wounded; We will open the gates of hell to the enemy.",
        List.of("Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\""));

      put("CGRI of Iran attacks 10 key command points and US team headquarters in Bahrain.",
        List.of("CGRI of Iran attacks positions of separatist groups."));

      put("Continued retaliatory attacks: Iran aims at US aircraft carriers and shoots down their F-15.",
        List.of("Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("Hard revenge of Iran in 6th phase of True Promise IV operation.",
        List.of("Iranian commander: \"We will defeat the US or fall as martyrs, there is no turning back.\"",
          "Iran attacks a US aircraft carrier with a missile in revenge for an attack on its ship."));

      put("UN condemns US and Israel attacks on Iran during negotiations.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Iran: Deadly US-Israel attack on school is \"flagrant crime.\"",
        List.of("Iran before UN: US-Israel aggression is war against international law.",
          "Nearly 200 Iranian students and teachers have died in US attacks."));

      put("Iran to UN: We will continue in legitimate defense until aggression ceases \"total and unequivocally.\"",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("US and Israel launch joint attack against Iran, which responds with regional offensive.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Iranian fleet completes mission; Navy guarantees defense of maritime borders.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors."));

      put("Iranian Army warns of \"conspiracies\" and promises to defend the country.",
        List.of("Iranian armed forces reiterate their respect for the national sovereignty of neighbors.",
          "Iranian fleet completes mission; Navy guarantees defense of maritime borders."));

      put("BACKGROUND: Dangerous Iranian weapons that would sink US fleet to the bottom of the sea.",
        List.of("In the 5th wave of operation: Iran aims at US ships and bases.",
          "Iranian fleet completes mission; Navy guarantees defense of maritime borders."));

      put("Iran alerts the UN on environmental risk of US military deployment.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Dialogues in Geneva: Iran shows firmness, rejects US pressure, and advances with cautious optimism.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Iran denounces UN inaction before Israel crimes in Lebanon.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Iran offers help to reduce tensions between Pakistan and Afghanistan.",
        List.of("Iran and Qatar emphasize diplomatic path for regional peace."));

      put("Araqchi underlines \"new advances\" in diplomatic path between Iran and the US.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Iran on 3rd negotiation with the US: one of the best and most serious.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Iran: Dialogues with the US continue with the objective of lifting sanctions.",
        List.of("Araqchi underlines \"new advances\" in diplomatic path between Iran and the US.",
          "Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Iran denounces contraband of prohibited equipment by Dutch diplomat.",
        List.of("Iran confiscates weapons after ambush on terrorists in the southeast of the country."));

      put("Iran and the US will resume nuclear dialogues after a pause of a few hours.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Pezeshkian: Iran rejects nuclear weapons based on the Leader's edict.",
        List.of("Iran confiscates weapons after ambush on terrorists in the southeast of the country."));

      put("China rejects use of force against Iran before US military rhetoric.",
        List.of("OCI warns the US on any use of force against Iran."));

      put("\"Iran approaches dialogues with the US with full seriousness and preparation.\"",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("El-Baradei warns the US of terrible costs of a war with Iran.",
        List.of("OCI warns the US on any use of force against Iran."));

      put("Pezeshkian sees \"good prospects\" for nuclear dialogue with the US.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("Israel recognizes that Iran surpasses it scientifically.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Iran dismantles terrorist network in the southeast; 8 detained and 3 shot down.",
        List.of("Iran confiscates weapons after ambush on terrorists in the southeast of the country."));

      put("US NGO recognizes Starlink deployment in Iran in January.",
        List.of("\"US through disinformation attempts to justify an attack on Iran.\""));

      put("Iranian Chancellor travels to Geneva to resume dialogues with the US.",
        List.of("Iran: Success of dialogues depends on \"seriousness and realism\" of the US."));

      put("\"Asymmetric war capacity of Iran would be costly for the US.\"",
        List.of("\"US through disinformation attempts to justify an attack on Iran.\""));

      put("Iran denounces US disinformation campaign against it.",
        List.of("\"US through disinformation attempts to justify an attack on Iran.\""));

      put("Israel: The US could only sustain 4 or 5 days of attacks against Iran.",
        List.of("Iran before UN: US-Israel aggression is war against international law.",
          "\"US through disinformation attempts to justify an attack on Iran.\""));

      put("Iran: UN resolution ignores causes of the Ukraine conflict.",
        List.of("Iran before UN: US-Israel aggression is war against international law."));

      put("Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway."));

      put("Representative Chris Smith, accepting a VOA interview, urged President Trump to personally request the release of Jimmy Lai from China.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway.",
          "Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("Members of both parties in the U.S. Senate introduced a bill to strengthen cooperation with Taiwan on drones.",
        List.of("Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("The Supreme Court heard oral arguments in the birthright citizenship case with President Trump attending as a spectator.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway."));

      put("The House China Subcommittee reported that Beijing purchased sanctioned crude oil via a \"shadow fleet,\" weakening Western sanctions pressure.",
        List.of("Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("A couple was stripped of U.S. citizenship for conspiring to steal medical research secrets and sharing them with China.",
        List.of("Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("U.S. Senators visited Taiwan, warning it to learn from Hong Kong and not underestimate Chinese intentions.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway.",
          "Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("A U.S. Senator visited Taiwan, urging it to pass a $40 billion defense appropriation bill, stating that strengthening self-defense is necessary to counter Chinese pressure.",
        List.of("Members of both parties in the U.S. Congress introduced a new bill to restrict exports of key chip manufacturing equipment to China."));

      put("President Trump stated birthright citizenship is not about wealthy people from China and other parts of the world.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway."));

      put("Secretary Rubio called for a plan to ensure the safety of the Strait of Hormuz after U.S. military operations at the G7 summit.",
        List.of("President Trump stated the United States can easily open the Strait of Hormuz while other nations discuss protecting shipping safety in the waterway."));

      // Litmus test: Headlines about Khamenei death from different sources
      put("Ayatollah Ali Khamenei, Autocratic Cleric Who Made Iran a Regional Power, Is Dead at 86",
        List.of("Iran confirms Supreme Leader Ali Khamenei dead after US-Israeli attacks",
          "Iranian state media confirms Supreme Leader Khamenei is dead",
          "Iran’s supreme leader is dead. Here’s what it means.",
          "Ayatollah Khamenei is dead. Here’s what that means for Iran’s leadership.",
          "Iran leader death: With Ayatollah Ali Khamenei dead, here's how succession works in Iran and who could be next supreme leader",
          "Why Khamenei Is Dead",
          "Iran’s Supreme Leader Ayatollah Ali Khamenei, who led the Islamic Republic since 1989, is dead at 86"));

      // Normalized versions focusing on core facts (who, what, where, when)
      put("Ayatollah Ali Khamenei is dead at 86",
        List.of("Ali Khamenei, Iran's Supreme Leader, is dead",
          "Iran's Supreme Leader Khamenei is dead",
          "Iran's supreme leader is dead",
          "Ayatollah Khamenei is dead",
          "Ayatollah Ali Khamenei is dead",
          "Khamenei is dead",
          "Ayatollah Ali Khamenei, Iran's Supreme Leader, is dead at 86"));
    }};

    it("Matches similar headlines", () -> {
      var hashFn = (Function<ZShingle, Long>) sh -> (long) sh.token.hashCode();
      for (var shLen : new int []{3, 4}) {
        for (var sigLen : new int[]{128, 256}) {
          System.out.println("-".repeat(32));
          System.out.printf("Shingle length: %d Signature Length: %d%n", shLen, sigLen);
          var results = new TreeMap<String, Map<String, Double>>();
          for (var entry : headLineIdx.entrySet()) {
            var inputBuf = ZnShingles.fromDocument(entry.getKey(), shLen, sigLen, hashFn);
            var scores = new TreeMap<String, Double>();
            for (var candidate : entry.getValue()) {
              var candBuf = ZnShingles.fromDocument(candidate, shLen, sigLen, hashFn);
              var similarity = ZnBuffers.similarity(inputBuf, candBuf);
              if (similarity > 0.5) {
                scores.put(candidate, similarity);
              }
            }
            results.put(entry.getKey(), scores);
          }
          for (var entry : results.entrySet()) {
            if (!entry.getValue().isEmpty()) {
              System.out.println(entry.getKey() + ": " + entry.getValue());
            }
          }
        }
      }
    });

  }
}
