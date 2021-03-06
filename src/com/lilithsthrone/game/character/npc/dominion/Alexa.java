package com.lilithsthrone.game.character.npc.dominion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.NameTriplet;
import com.lilithsthrone.game.character.Quest;
import com.lilithsthrone.game.character.QuestLine;
import com.lilithsthrone.game.character.SexualOrientation;
import com.lilithsthrone.game.character.body.Covering;
import com.lilithsthrone.game.character.body.types.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.BodySize;
import com.lilithsthrone.game.character.body.valueEnums.CupSize;
import com.lilithsthrone.game.character.body.valueEnums.Muscle;
import com.lilithsthrone.game.character.effects.Fetish;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.gender.GenderPreference;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.combat.Attack;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.weapon.AbstractWeaponType;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.1.75
 * @version 0.1.83
 * @author Innoxia
 */
public class Alexa extends NPC {

	private static final long serialVersionUID = 1L;

	public Alexa() {
		this(false);
	}
	
	private Alexa(boolean isImported) {
		super(new NameTriplet("Alexa"),
				"Alexa is an extremely powerful harpy matriarch, and is in control of one of the largest harpy flocks in Dominion."
						+ " Her beauty rivals that of even the most gorgeous of succubi, which, combined with her sharp mind and regal personality, makes her somewhat of an idol in harpy society.",
				8, Gender.F_V_B_FEMALE, RacialBody.HARPY, RaceStage.LESSER,
				new CharacterInventory(30), WorldType.HARPY_NEST, PlaceType.HARPY_NESTS_ALEXAS_NEST, true);
		
		if(!isImported) {
			this.setSexualOrientation(SexualOrientation.AMBIPHILIC);
			
			this.setEyeCovering(new Covering(BodyCoveringType.EYE_HARPY, Colour.EYE_BLUE));
			this.setHairCovering(new Covering(BodyCoveringType.HAIR_HARPY, Colour.FEATHERS_WHITE), true);
			this.setSkinCovering(new Covering(BodyCoveringType.FEATHERS, Colour.FEATHERS_WHITE), true);
			this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, Colour.SKIN_LIGHT), true);

			this.setFemininity(100);
			
			this.setVaginaVirgin(true);
			this.setAssVirgin(true);
			this.setFaceVirgin(true);
			this.setBreastSize(CupSize.D.getMeasurement());
			
			this.setMuscle(Muscle.TWO_TONED.getMedianValue());
			this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());
			
			this.addFetish(Fetish.FETISH_PURE_VIRGIN);
			this.addFetish(Fetish.FETISH_DENIAL);
			
			this.setHeight(162);
			
			this.equipMainWeaponFromNowhere(AbstractWeaponType.generateWeapon(WeaponType.MELEE_CHAOS_EPIC, DamageType.PHYSICAL));
			
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.GROIN_BIKINI, Colour.CLOTHING_WHITE, false), true, this);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.CHEST_BIKINI, Colour.CLOTHING_WHITE, false), true, this);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.HEAD_TIARA, Colour.CLOTHING_GOLD, false), true, this);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.EYES_AVIATORS, Colour.CLOTHING_GOLD, false), true, this);
			
			this.setPiercedEar(true);
			this.setPiercedNavel(true);
			this.setPiercedNose(true);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.PIERCING_EAR_BASIC_RING, Colour.CLOTHING_GOLD, false), true, this);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.PIERCING_NAVEL_GEM, Colour.CLOTHING_GOLD, false), true, this);
			this.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.PIERCING_NOSE_BASIC_RING, Colour.CLOTHING_GOLD, false), true, this);
		}
	}
	
	@Override
	public Alexa loadFromXML(Element parentElement, Document doc) {
		Alexa npc = new Alexa(true);

		loadNPCVariablesFromXML(npc, null, parentElement, doc);
		
		return npc;
	}
	
	@Override
	public boolean isUnique() {
		return true;
	}
	
	@Override
	public String getSpeechColour() {
		if(Main.getProperties().lightTheme) {
			return "#59005C";
		} else {
			return "#FFDFB3";
		}
	}
	
	@Override
	public void dailyReset() {
		if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_G_SLAVERY)) {
			for(String id : slavesOwned) {
				Main.game.banishNPC(id);
			}
			this.slavesOwned.clear();
			
			for(int i=0; i<5; i++) {
				NPC newSlave = new DominionAlleywayAttacker(GenderPreference.getGenderFromUserPreferences());
				try {
					Main.game.addNPC(newSlave, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				newSlave.setLocation(WorldType.SLAVER_ALLEY, PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP, true);
				addSlave(newSlave);
				newSlave.resetInventory();
				newSlave.equipClothingFromNowhere(AbstractClothingType.generateClothing(ClothingType.NECK_SLAVE_COLLAR, Colour.CLOTHING_BLACK_STEEL, false), true, Main.game.getAlexa());
				newSlave.setPlayerKnowsName(true);
				
			}
		}
	}
	
	@Override
	public void changeFurryLevel(){
	}
	
	@Override
	public DialogueNodeOld getEncounterDialogue() {
		return null;
	}

	@Override
	public void endSex(boolean applyEffects) {
	}

	// Combat (you never fight Rose):
	@Override
	public String getCombatDescription() {
		return null;
	}
	@Override
	public String getAttackDescription(Attack attackType, boolean isHit) {
		return null;
	}
	@Override
	public Response endCombat(boolean applyEffects, boolean victory) {
		return null;
	}
	@Override
	public Attack attackType() {
		return null;
	}
	@Override
	public int getExperienceFromVictory() {
		return 0;
	}

}