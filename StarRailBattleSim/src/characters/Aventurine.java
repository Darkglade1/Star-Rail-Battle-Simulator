package characters;

import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Aventurine extends AbstractCharacter {
    public static String NAME = "Aventurine";

    AventurineTalentPower talentPower = new AventurineTalentPower();
    private int numFollowUps = 0;
    private int numBlindBetGained = 0;
    private int numBlindBetGainedFUA = 0;
    private int blindBetCounter = 0;
    private final int BLIND_BET_THRESHOLD = 7;
    private final int BLIND_BET_CAP = 10;
    private int blindBetFollowUpPerTurn = 3;
    private int blindBetFollowUpCounter = blindBetFollowUpPerTurn;
    private boolean SPNeutral;
    private String numFollowUpsMetricName = "Follow up Attacks used";
    private String numBlindBetGainedMetricName = "Blind Bet gained";
    private String numBlindBetFromFUAMetricName = "Blind Bet gained from Ally FUA";

    public Aventurine(boolean SPNeutral) {
        super(NAME, 1203, 446, 655, 106, 80, ElementType.IMAGINARY, 110, 150, Path.PRESERVATION);

        this.SPNeutral = SPNeutral;
        this.addPower(new TracePower()
                .setStat(PowerStat.DEF_PERCENT, 35)
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 14.4f)
                .setStat(PowerStat.EFFECT_RES, 10));
        this.hasAttackingUltimate = true;
    }

    public Aventurine() {
        this(true);
    }

    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.DEF, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        enemy.addPower(new AventurineUltDebuff());
        getBattle().getHelper().hitEnemy(this, enemy, 2.7f, BattleHelpers.MultiplierStat.DEF, types, TOUGHNESS_DAMAGE_THREE_UNITs);
        getBattle().getHelper().PostAttackLogic(this, types);

        int blindBetGain = getBattle().getGambleChanceRng().nextInt(7) + 1;
        increaseBlindBet(blindBetGain);
    }

    public void useFollowUp() {
        numFollowUps++;
        int initialBlindBet = this.blindBetCounter;
        this.blindBetCounter -= BLIND_BET_THRESHOLD;
        getBattle().addToLog(String.format("%s used Follow Up (%d -> %d)", name, initialBlindBet, this.blindBetCounter));
        increaseEnergy(7);

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        int numBounces = 7;
        while (numBounces > 0) {
            getBattle().getHelper().hitEnemy(this, getBattle().getRandomEnemy(), 0.25f, BattleHelpers.MultiplierStat.DEF, types, 3.3333333333333335f);
            numBounces--;
        }
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void takeTurn() {
        super.takeTurn();
        if (SPNeutral) {
            if (getBattle().getSkillPoints() > 0) {
                if (lastMove(MoveType.BASIC) || firstMove) {
                    useSkill();
                    firstMove = false;
                } else {
                    useBasicAttack();
                }
            } else {
                useBasicAttack();
            }
        } else {
            useBasicAttack();
        }
    }

    public void onCombatStart() {
        for (AbstractCharacter character : getBattle().getPlayers()) {
            character.addPower(talentPower);
        }
        addPower(PermPower.create(PowerStat.CRIT_CHANCE, 48, "Aventurine Crit Chance Bonus"));
    }

    public void onTurnStart() {
        blindBetFollowUpCounter = blindBetFollowUpPerTurn;
    }

    public void increaseBlindBet(int amount) {
        numBlindBetGained += amount;
        int initialBlindBet = this.blindBetCounter;
        this.blindBetCounter += amount;
        if (this.blindBetCounter > BLIND_BET_CAP) {
            this.blindBetCounter = BLIND_BET_CAP;
        }
        getBattle().addToLog(String.format("%s gained %d Blind Bet (%d -> %d)", name, amount, initialBlindBet, this.blindBetCounter));
        if (this.blindBetCounter >= BLIND_BET_THRESHOLD) {
            useFollowUp();
        }
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numFollowUpsMetricName, String.valueOf(numFollowUps));
        map.put(numBlindBetGainedMetricName, String.valueOf(numBlindBetGained));
        map.put(numBlindBetFromFUAMetricName, String.valueOf(numBlindBetGainedFUA));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numFollowUpsMetricName);
        list.add(numBlindBetGainedMetricName);
        list.add(numBlindBetFromFUAMetricName);
        return list;
    }

    private static class AventurineUltDebuff extends AbstractPower {
        public AventurineUltDebuff() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 3;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float receiveConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 15;
        }
    }

    private class AventurineTalentPower extends AbstractPower {
        public AventurineTalentPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
            this.setStat(PowerStat.EFFECT_RES, 50); //assume 100% shield uptime
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<DamageType> types, int energyFromAttacked) {
            if (character == Aventurine.this) {
                increaseBlindBet(2);
            } else {
                increaseBlindBet(1);
            }
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            if (character != Aventurine.this && types.contains(DamageType.FOLLOW_UP) && blindBetFollowUpCounter > 0) {
                increaseBlindBet(1);
                blindBetFollowUpCounter--;
                numBlindBetGainedFUA++;
            }
        }
    }
}
