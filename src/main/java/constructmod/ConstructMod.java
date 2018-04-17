package constructmod;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import constructmod.cards.*;
import constructmod.relics.*;
import java.nio.charset.StandardCharsets;

import constructmod.characters.TheConstruct;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;

@SpireInitializer
public class ConstructMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber,
	EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber {
	
	public static final Logger logger = LogManager.getLogger(ConstructMod.class.getName());
			
    private static final String MODNAME = "Construct Mod";
    private static final String AUTHOR = "Moocowsgomoo";
    private static final String DESCRIPTION = "New Character: The Construct!";
	
	private static final Color CONSTRUCT_MOD_COLOR = CardHelper.getColor(170.0f, 150.0f, 50.0f);
    
    public ConstructMod() {
        BaseMod.subscribe(this);
		
		logger.info("creating the color " + AbstractCardEnum.CONSTRUCT_MOD_COLOR.toString());
        BaseMod.addColor(AbstractCardEnum.CONSTRUCT_MOD_COLOR.toString(),
        		CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR,
        		"img/512/bg_attack_gold.png", "img/512/bg_skill_gold.png",
        		"img/512/bg_power_gold.png", "img/512/card_gold_orb.png",
				"img/1024/bg_attack_gold.png", "img/1024/bg_skill_gold.png",
        		"img/1024/bg_power_gold.png", "img/1024/card_gold_orb.png");
				
		Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }
    
    public static void initialize() {
        ConstructMod mod = new ConstructMod();
    }
    
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("img/ConstructModBadge.png"));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("This mod does not have any settings", 400.0f, 700.0f, (me) -> {});  
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
		
		/*logger.info("Adding ConstructMod Keywords");
		GameDictionary.keywords.put("cycle", "When drawn, discard this and draw a new card. Only works once per turn.");
		GameDictionary.keywords.put("thorns", "Enemies take damage when they attack you.");
		GameDictionary.keywords.put("metallicize", "Gain Block at the end of each turn.");
		GameDictionary.keywords.put("blur", "Block is not removed at the start of your next turn.");
		GameDictionary.keywords.put("plated armor", "Gain Block at the end of each turn. Reduced when you take unblocked damage.");*/
    }
    
    public void receiveEditKeywords() {
    	final String[] cycle = {"cycle", "cycles"};
    	BaseMod.addKeyword(cycle, "When drawn, discard this and draw a new card. Only works once per turn.");
    	final String[] thorns = {"thorns"};
    	BaseMod.addKeyword(thorns, "Enemies take damage when they attack you.");
    	final String[] metallicize = {"metallicize"};
    	BaseMod.addKeyword(metallicize, "Gain Block at the end of each turn.");
    	final String[] pArmor = {"plated armor"}; // not working b/c it's two words?
    	BaseMod.addKeyword(pArmor, "Gain Block at the end of each turn. Reduced when you take unblocked damage.");
    	final String[] slimed = {"slimed"};
    	BaseMod.addKeyword(slimed, "Slimed is a status card that costs [R] to exhaust.");
    	final String[] orbs = {"orbs"};
    	BaseMod.addKeyword(orbs, "Orbs are cards that cycle and apply a small bonus effect.");
    }
	
	public void receiveEditCharacters() {
		logger.info("begin editting characters");
		
		logger.info("add " + TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		BaseMod.addCharacter(TheConstruct.class, "The Construct", "Construct class string",
				AbstractCardEnum.CONSTRUCT_MOD_COLOR.toString(), "The Construct",
				"img/charSelect/constructButton.png", "img/charSelect/constructPortrait.jpg",
				TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		
		logger.info("done editting characters");
	}
	
	public void receiveEditStrings() {
		
		// RelicStrings
        String relicStrings = Gdx.files.internal("localization/ConstructMod-RelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		// CardStrings
        String cardStrings = Gdx.files.internal("localization/ConstructMod-CardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
	}
	
	public void receiveEditCards() {
		// Add cards
		logger.info("Adding Construct Cards");
		// BASIC
		BaseMod.addCard(new Strike_Gold());
		BaseMod.addCard(new Defend_Gold());
		BaseMod.addCard(new AttackMode());
		BaseMod.addCard(new DefenseMode());
		
		// COMMON
		//	Attacks
		BaseMod.addCard(new Boost()); 		// atk, block
		BaseMod.addCard(new ScrapCannon());	// atk, exhaust
		BaseMod.addCard(new SweepLaser());	// atk
		BaseMod.addCard(new Versatility());	// atk, block
		BaseMod.addCard(new SuppressiveFire());// atk, defense
		BaseMod.addCard(new Backfire());	// atk
		BaseMod.addCard(new HeavyBolt());	// atk
		//	Skills (Defensive)
		BaseMod.addCard(new Reinforce());	// block
		BaseMod.addCard(new ReactiveShield()); // block (cycle)
		BaseMod.addCard(new GuardOrb());	// cycle, block
		BaseMod.addCard(new MetalShell());	// block
		BaseMod.addCard(new Anticipate());	// block
		//	Skills (Misc.)
		BaseMod.addCard(new Analyze()); 	// draw/energy next turn
		BaseMod.addCard(new FlameOrb());	// cycle, atk
		BaseMod.addCard(new ShockOrb());	// cycle, atk
		BaseMod.addCard(new VentSteam());	// debuff, exhaust
		BaseMod.addCard(new ModeShift()); 	// modes, draw
		//	Powers
		BaseMod.addCard(new ElectricArmor());// block-based
		BaseMod.addCard(new BubbleShield());// block-based
		BaseMod.addCard(new Autoturret());	// atk from cycle
		BaseMod.addCard(new Bunker()); 		// block
		
		// UNCOMMON
		// 	Attacks
		BaseMod.addCard(new ChargeShot());	// atk (retain)
		BaseMod.addCard(new CripplingShot());//atk, debuff
		BaseMod.addCard(new Electrocute()); // atk, debuff
		BaseMod.addCard(new HammerDown());	// atk, modes
		BaseMod.addCard(new Antimatter());	// atk
		BaseMod.addCard(new FlakBarrage());	// atk (cycle)
		//	Skills (Defensive)
		BaseMod.addCard(new Disorient());	// debuff
		BaseMod.addCard(new OneWayMirror());// block
		//	Skills (Misc.)
		BaseMod.addCard(new ScopeOrb());	// debuff
		BaseMod.addCard(new BatteryOrb());	// energy
		BaseMod.addCard(new Backup());		// copy
		BaseMod.addCard(new BatteryAcid()); // energy
		BaseMod.addCard(new Reboot());	 	// exhaust
		// 	Powers
		BaseMod.addCard(new Synchronize());	// copy-based
		BaseMod.addCard(new Enhance());		// upgrade
		BaseMod.addCard(new ShieldGenerator());//defensive
		BaseMod.addCard(new Disruptor());  	// block
		
		// RARE
		//	Attacks
		BaseMod.addCard(new HyperBeam());	// atk
		BaseMod.addCard(new GoldenBullet());// atk
		BaseMod.addCard(new ShieldBurst());	// atk from block
		BaseMod.addCard(new GatlingGun());	// atk, X-cost
		//	Skills
		BaseMod.addCard(new OrbGenesis());	// copy
		BaseMod.addCard(new MassProduction());//copy
		BaseMod.addCard(new ClockworkEgg());// egg
		BaseMod.addCard(new HastyRepair()); // heal
		BaseMod.addCard(new Hazardproof()); // block, buff
		//	Powers
		BaseMod.addCard(new SiegeForm());	// buff, atk-based
		BaseMod.addCard(new PanicFire()); 	// atk from cycle
		BaseMod.addCard(new SpinDrive()); 	// cards
	}
	
	public void receiveEditRelics() {
		logger.info("Adding Construct Relics");
		RelicLibrary.add(new Cogwheel());
	}
}