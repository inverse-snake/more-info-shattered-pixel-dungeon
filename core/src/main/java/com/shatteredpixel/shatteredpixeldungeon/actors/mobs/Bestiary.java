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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Bestiary {
	
	public static ArrayList<Class<? extends Mob>> getMobRotation( int depth ){
		ArrayList<Class<? extends Mob>> mobs = standardMobRotation( depth );
		addRareMobs(depth, mobs);
		swapMobAlts(mobs);
		Random.shuffle(mobs);
		return mobs;
	}

	public static  ArrayList<Class<?>> getAllMobs() {
		//This is very ugly, and will have to be manually redone for any bestiary changes,
		//but I don't see a better option.
		ArrayList<Class<?>> mobs = new ArrayList<>();
		//Sewers:
		mobs.add(Rat.class);
		mobs.add(Albino.class);
		mobs.add(FetidRat.class);
		mobs.add(Snake.class);
		mobs.add(Gnoll.class);
		mobs.add(GnollTrickster.class);
		mobs.add(Crab.class);
		mobs.add(GreatCrab.class);
		mobs.add(Swarm.class);
		mobs.add(Slime.class);
		mobs.add(CausticSlime.class);
		mobs.add(Goo.class);
		//Prison:
		mobs.add(Skeleton.class);
		mobs.add(Thief.class);
		mobs.add(Bandit.class);
		mobs.add(Guard.class);
		mobs.add(DM100.class);
		mobs.add(Necromancer.class);
		mobs.add(Necromancer.NecroSkeleton.class);
		mobs.add(SpectralNecromancer.class);
		mobs.add(RotLasher.class);
		mobs.add(RotHeart.class);
		mobs.add(Elemental.NewbornFireElemental.class);
		mobs.add(Tengu.class);
		//Caves:
		mobs.add(Bat.class);
		mobs.add(Brute.class);
		mobs.add(ArmoredBrute.class);
		mobs.add(Shaman.RedShaman.class);
		mobs.add(Shaman.BlueShaman.class);
		mobs.add(Shaman.PurpleShaman.class);
		mobs.add(Spinner.class);
		mobs.add(DM200.class);
		mobs.add(DM201.class);
		mobs.add(CrystalWisp.class);
		mobs.add(CrystalGuardian.class);
		mobs.add(CrystalSpire.class);
		mobs.add(Pylon.class);
		mobs.add(DM300.class);
		//City:
		mobs.add(Ghoul.class);
		mobs.add(Elemental.FireElemental.class);
		mobs.add(Elemental.FrostElemental.class);
		mobs.add(Elemental.ShockElemental.class);
		mobs.add(Elemental.ChaosElemental.class);
		mobs.add(Warlock.class);
		mobs.add(Monk.class);
		mobs.add(Senior.class);
		mobs.add(Golem.class);
		mobs.add(DwarfKing.class);
		//Halls:
		mobs.add(DemonSpawner.class);
		mobs.add(RipperDemon.class);
		mobs.add(Succubus.class);
		mobs.add(Eye.class);
		mobs.add(Scorpio.class);
		mobs.add(Acidic.class);
		mobs.add(YogDzewa.class);
		mobs.add(YogDzewa.Larva.class);
		mobs.add(YogFist.BurningFist.class);
		mobs.add(YogFist.SoiledFist.class);
		mobs.add(YogFist.RottingFist.class);
		mobs.add(YogFist.RustedFist.class);
		mobs.add(YogFist.BrightFist.class);
		mobs.add(YogFist.DarkFist.class);
		//Various
		mobs.add(Wraith.class);
		mobs.add(TormentedSpirit.class);
		mobs.add(Piranha.class);
		mobs.add(PhantomPiranha.class);
		mobs.add(Mimic.class);
		mobs.add(GoldenMimic.class);
		mobs.add(CrystalMimic.class);
		mobs.add(Statue.class);
		mobs.add(ArmoredStatue.class);
		mobs.add(Bee.class);
		return mobs;
	}
	
	//returns a rotation of standard mobs, unshuffled.
	private static ArrayList<Class<? extends Mob>> standardMobRotation( int depth ){
		switch(depth){
			
			// Sewers
			case 1: default:
				//3x rat, 1x snake
				return new ArrayList<>(Arrays.asList(
						Rat.class, Rat.class, Rat.class,
						Snake.class));
			case 2:
				//2x rat, 1x snake, 2x gnoll
				return new ArrayList<>(Arrays.asList(Rat.class, Rat.class,
						Snake.class,
						Gnoll.class, Gnoll.class));
			case 3:
				//1x rat, 1x snake, 3x gnoll, 1x swarm, 1x crab
				return new ArrayList<>(Arrays.asList(Rat.class,
						Snake.class,
						Gnoll.class, Gnoll.class, Gnoll.class,
						Swarm.class,
						Crab.class));
			case 4: case 5:
				//1x gnoll, 1x swarm, 2x crab, 2x slime
				return new ArrayList<>(Arrays.asList(Gnoll.class,
						Swarm.class,
						Crab.class, Crab.class,
						Slime.class, Slime.class));
				
			// Prison
			case 6:
				//3x skeleton, 1x thief, 1x swarm
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Swarm.class));
			case 7:
				//3x skeleton, 1x thief, 1x DM-100, 1x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						DM100.class,
						Guard.class));
			case 8:
				//2x skeleton, 1x thief, 2x DM-100, 2x guard, 1x necromancer
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class,
						Thief.class,
						DM100.class, DM100.class,
						Guard.class, Guard.class,
						Necromancer.class));
			case 9: case 10:
				//1x skeleton, 1x thief, 2x DM-100, 2x guard, 2x necromancer
				return new ArrayList<>(Arrays.asList(Skeleton.class,
						Thief.class,
						DM100.class, DM100.class,
						Guard.class, Guard.class,
						Necromancer.class, Necromancer.class));
				
			// Caves
			case 11:
				//3x bat, 1x brute, 1x shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class, Bat.class,
						Brute.class,
						Shaman.random()));
			case 12:
				//2x bat, 2x brute, 1x shaman, 1x spinner
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class,
						Brute.class, Brute.class,
						Shaman.random(),
						Spinner.class));
			case 13:
				//1x bat, 2x brute, 2x shaman, 2x spinner, 1x DM-200
				return new ArrayList<>(Arrays.asList(
						Bat.class,
						Brute.class, Brute.class,
						Shaman.random(), Shaman.random(),
						Spinner.class, Spinner.class,
						DM200.class));
			case 14: case 15:
				//1x bat, 1x brute, 2x shaman, 2x spinner, 2x DM-300
				return new ArrayList<>(Arrays.asList(
						Bat.class,
						Brute.class,
						Shaman.random(), Shaman.random(),
						Spinner.class, Spinner.class,
						DM200.class, DM200.class));
				
			// City
			case 16:
				//3x ghoul, 1x elemental, 1x warlock
				return new ArrayList<>(Arrays.asList(
						Ghoul.class, Ghoul.class, Ghoul.class,
						Elemental.random(),
						Warlock.class));
			case 17:
				//1x ghoul, 2x elemental, 1x warlock, 1x monk
				return new ArrayList<>(Arrays.asList(
						Ghoul.class,
						Elemental.random(), Elemental.random(),
						Warlock.class,
						Monk.class));
			case 18:
				//1x ghoul, 1x elemental, 2x warlock, 2x monk, 1x golem
				return new ArrayList<>(Arrays.asList(
						Ghoul.class,
						Elemental.random(),
						Warlock.class, Warlock.class,
						Monk.class, Monk.class,
						Golem.class));
			case 19: case 20:
				//1x elemental, 2x warlock, 2x monk, 3x golem
				return new ArrayList<>(Arrays.asList(
						Elemental.random(),
						Warlock.class, Warlock.class,
						Monk.class, Monk.class,
						Golem.class, Golem.class, Golem.class));
				
			// Halls
			case 21:
				//2x succubus, 1x evil eye
				return new ArrayList<>(Arrays.asList(
						Succubus.class, Succubus.class,
						Eye.class));
			case 22:
				//1x succubus, 1x evil eye
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class));
			case 23:
				//1x succubus, 2x evil eye, 1x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class));
			case 24: case 25: case 26:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
		}
		
	}
	
	//has a chance to add a rarely spawned mobs to the rotation
	public static void addRareMobs( int depth, ArrayList<Class<?extends Mob>> rotation ){
		
		switch (depth){
			
			// Sewers
			default:
				return;
			case 4:
				if (Random.Float() < 0.025f) rotation.add(Thief.class);
				return;
				
			// Prison
			case 9:
				if (Random.Float() < 0.025f) rotation.add(Bat.class);
				return;
				
			// Caves
			case 14:
				if (Random.Float() < 0.025f) rotation.add(Ghoul.class);
				return;
				
			// City
			case 19:
				if (Random.Float() < 0.025f) rotation.add(Succubus.class);
				return;
		}
	}
	
	//switches out regular mobs for their alt versions when appropriate
	private static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
		for (int i = 0; i < rotation.size(); i++){
			if (Random.Int( 50 ) == 0) {
				Class<? extends Mob> cl = rotation.get(i);
				if (cl == Rat.class) {
					cl = Albino.class;
				} else if (cl == Slime.class) {
					cl = CausticSlime.class;
				} else if (cl == Thief.class) {
					cl = Bandit.class;
				} else if (cl == Necromancer.class){
					cl = SpectralNecromancer.class;
				} else if (cl == Brute.class) {
					cl = ArmoredBrute.class;
				} else if (cl == DM200.class) {
					cl = DM201.class;
				} else if (cl == Monk.class) {
					cl = Senior.class;
				} else if (cl == Scorpio.class) {
					cl = Acidic.class;
				}
				rotation.set(i, cl);
			}
		}
	}
}
