package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public abstract class ExponentialRing extends Ring {
    protected final float exponent_base;

    protected ExponentialRing(float exponentBase) { //Way too many rings follow this formula, so it deserves a class.
        exponent_base = exponentBase;
    }

    @Override
    protected String statsInfo() {
        String effect = Messages.get(this, "effect");
        if (isIdentified()){
            String info = Messages.get(this, "stats", effect,
                    Messages.decimalFormat("#.##", 100f * (Math.pow(exponent_base, soloBuffedBonus()) - 1f)));
            if (cursed && cursedKnown) {
                info += "\n" + Messages.get(this, "remove_curse", effect,
                        Messages.decimalFormat("#.##", 100f * (Math.pow(exponent_base, buffedLvl() + 1) - 1f)));
            }
            if (isEquipped(Dungeon.hero) && soloBuffedBonus() != combinedBuffedBonus(Dungeon.hero, buff().getClass())) {
                info += "\n\n" + Messages.get(this, "combined_stats", effect,
                        Messages.decimalFormat("#.##", 100f * (Math.pow(exponent_base, combinedBuffedBonus(Dungeon.hero, buff().getClass())) - 1f)));
            }
            return info + "\n\n" + Messages.get(this, "each_upgrade", effect, Messages.decimalFormat("#.##", 100f * (exponent_base - 1f)));
        } else {
            return Messages.get(this, "typical_stats", effect, Messages.decimalFormat("#.##", 100f * (exponent_base - 1f)))
                    + "\n\n" + Messages.get(this, "each_upgrade", effect, Messages.decimalFormat("#.##", 100f * (exponent_base - 1f)));
        }
    }
}
