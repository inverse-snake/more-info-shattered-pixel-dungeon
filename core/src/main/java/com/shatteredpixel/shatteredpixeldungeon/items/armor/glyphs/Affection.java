/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor.Glyph;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Affection extends Glyph {
	
	private static final ItemSprite.Glowing PINK = new ItemSprite.Glowing( 0xFF4488 );

	private float baseProcChance(int level) {
		level = Math.max(0, level);
		return (level+3f)/(level+20f);
	}

	private int duration(int level) {
		return Math.round(Charm.DURATION*Math.max(1f, baseProcChance(level)));
	}

	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {
		// lvl 0 - 15%
		// lvl 1 ~ 19%
		// lvl 2 ~ 23%
		float procChance = baseProcChance(armor.buffedLvl()) * procChanceMultiplier(defender);
		if (Random.Float() < procChance) {
			Buff.affect( attacker, Charm.class, duration(armor.buffedLvl())).object = defender.id();
			attacker.sprite.centerEmitter().start( Speck.factory( Speck.HEART ), 0.2f, 5 );

		}
		
		return damage;
	}

	@Override
	public String desc(int armorLevel) {
		return Messages.get(this, "desc",
				Messages.decimalFormat("#.##", 100f * Math.min(1f, baseProcChance(armorLevel))),
				duration(armorLevel)
		);
	}

	@Override
	public Glowing glowing() {
		return PINK;
	}
}
