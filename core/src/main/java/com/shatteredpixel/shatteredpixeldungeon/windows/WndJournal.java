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

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredStatue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bestiary;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM300;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DwarfKing;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Goo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Pylon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tengu;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogDzewa;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickRecipe;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollingListPane;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class WndJournal extends WndTabbed {
	
	public static final int WIDTH_P     = 126;
	public static final int HEIGHT_P    = 180;
	
	public static final int WIDTH_L     = 200;
	public static final int HEIGHT_L    = 130;
	
	private static final int ITEM_HEIGHT	= 18;
	
	private GuideTab guideTab;
	private AlchemyTab alchemyTab;
	private NotesTab notesTab;
	private CatalogTab catalogTab;
	private LoreTab loreTab;
	
	public static int last_index = 0;
	
	public WndJournal(){
		
		int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;
		int height = PixelScene.landscape() ? HEIGHT_L : HEIGHT_P;
		
		resize(width, height);
		
		guideTab = new GuideTab();
		add(guideTab);
		guideTab.setRect(0, 0, width, height);
		guideTab.updateList();
		
		alchemyTab = new AlchemyTab();
		add(alchemyTab);
		alchemyTab.setRect(0, 0, width, height);
		
		notesTab = new NotesTab();
		add(notesTab);
		notesTab.setRect(0, 0, width, height);
		notesTab.updateList();
		
		catalogTab = new CatalogTab();
		add(catalogTab);
		catalogTab.setRect(0, 0, width, height);
		catalogTab.updateList();

		loreTab = new LoreTab();
		add(loreTab);
		loreTab.setRect(0, 0, width, height);
		loreTab.updateList();
		
		Tab[] tabs = {
				new IconTab( new ItemSprite(ItemSpriteSheet.MASTERY, null) ) {
					protected void select( boolean value ) {
						super.select( value );
						guideTab.active = guideTab.visible = value;
						if (value) last_index = 0;
					}
				},
				new IconTab( new ItemSprite(ItemSpriteSheet.ALCH_PAGE, null) ) {
					protected void select( boolean value ) {
						super.select( value );
						alchemyTab.active = alchemyTab.visible = value;
						if (value) last_index = 1;
					}
				},
				new IconTab( Icons.get(Icons.STAIRS) ) {
					protected void select( boolean value ) {
						super.select( value );
						notesTab.active = notesTab.visible = value;
						if (value) last_index = 2;
					}
				},
				new IconTab( new ItemSprite(ItemSpriteSheet.WEAPON_HOLDER, null) ) {
					protected void select( boolean value ) {
						super.select( value );
						catalogTab.active = catalogTab.visible = value;
						if (value) last_index = 3;
					}
				},
				new IconTab( new ItemSprite(ItemSpriteSheet.GUIDE_PAGE, null) ) {
					protected void select( boolean value ) {
						super.select( value );
						loreTab.active = loreTab.visible = value;
						if (value) last_index = 4;
					}
				}
		};
		
		for (Tab tab : tabs) {
			add( tab );
		}
		
		layoutTabs();
		
		select(last_index);
	}

	@Override
	public void offset(int xOffset, int yOffset) {
		super.offset(xOffset, yOffset);
		guideTab.layout();
		alchemyTab.layout();
		notesTab.layout();
		catalogTab.layout();
		loreTab.layout();
	}
	
	public static class GuideTab extends Component {

		private ScrollingListPane list;
		
		@Override
		protected void createChildren() {
			list = new ScrollingListPane();
			add( list );
		}
		
		@Override
		protected void layout() {
			super.layout();
			list.setRect( 0, 0, width, height);
		}
		
		private void updateList(){
			list.addTitle(Document.ADVENTURERS_GUIDE.title());

			for (String page : Document.ADVENTURERS_GUIDE.pageNames()){
				boolean found = Document.ADVENTURERS_GUIDE.isPageFound(page);
				ScrollingListPane.ListItem item = new ScrollingListPane.ListItem(
						Document.ADVENTURERS_GUIDE.pageSprite(page),
						null,
						found ? Messages.titleCase(Document.ADVENTURERS_GUIDE.pageTitle(page)) : Messages.titleCase(Messages.get( this, "missing" ))
				){
					@Override
					public boolean onClick(float x, float y) {
						if (inside( x, y ) && found) {
							GameScene.show( new WndStory( Document.ADVENTURERS_GUIDE.pageSprite(page),
									Document.ADVENTURERS_GUIDE.pageTitle(page),
									Document.ADVENTURERS_GUIDE.pageBody(page) ));
							Document.ADVENTURERS_GUIDE.readPage(page);
							return true;
						} else {
							return false;
						}
					}
				};
				if (!found){
					item.hardlight(0x999999);
					item.hardlightIcon(0x999999);
				}
				list.addItem(item);
			}

			list.setRect(x, y, width, height);
		}

	}
	
	public static class AlchemyTab extends Component {
		
		private RedButton[] pageButtons;
		private static final int NUM_BUTTONS = 10;
		
		private static final int[] spriteIndexes = {10, 12, 7, 9, 11, 8, 3, 13, 14, 15};
		
		public static int currentPageIdx   = 0;
		
		private IconTitle title;
		private RenderedTextBlock body;
		
		private ScrollPane list;
		private ArrayList<QuickRecipe> recipes = new ArrayList<>();
		
		@Override
		protected void createChildren() {
			pageButtons = new RedButton[NUM_BUTTONS];
			for (int i = 0; i < NUM_BUTTONS; i++){
				final int idx = i;
				pageButtons[i] = new RedButton( "" ){
					@Override
					protected void onClick() {
						currentPageIdx = idx;
						updateList();
					}
				};
				if (Document.ALCHEMY_GUIDE.isPageFound(i)) {
					pageButtons[i].icon(new ItemSprite(ItemSpriteSheet.SOMETHING + spriteIndexes[i], null));
				} else {
					pageButtons[i].icon(new ItemSprite(ItemSpriteSheet.SOMETHING, null));
					pageButtons[i].enable(false);
				}
				add( pageButtons[i] );
			}
			
			title = new IconTitle();
			title.icon( new ItemSprite(ItemSpriteSheet.ALCH_PAGE));
			title.visible = false;

			body = PixelScene.renderTextBlock(6);
			
			list = new ScrollPane(new Component());
			add(list);
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			if (PixelScene.landscape()){
				float buttonWidth = width()/pageButtons.length;
				for (int i = 0; i < NUM_BUTTONS; i++) {
					pageButtons[i].setRect(i*buttonWidth, 0, buttonWidth, ITEM_HEIGHT);
					PixelScene.align(pageButtons[i]);
				}
			} else {
				//for first row
				float buttonWidth = width()/5;
				float y = 0;
				float x = 0;
				for (int i = 0; i < NUM_BUTTONS; i++) {
					pageButtons[i].setRect(x, y, buttonWidth, ITEM_HEIGHT);
					PixelScene.align(pageButtons[i]);
					x += buttonWidth;
					if (i == 4){
						y += ITEM_HEIGHT;
						x = 0;
						buttonWidth = width()/5;
					}
				}
			}
			
			list.setRect(0, pageButtons[NUM_BUTTONS-1].bottom() + 1, width,
					height - pageButtons[NUM_BUTTONS-1].bottom() - 1);
			
			updateList();
		}
		
		private void updateList() {

			if (currentPageIdx != -1 && !Document.ALCHEMY_GUIDE.isPageFound(currentPageIdx)){
				currentPageIdx = -1;
			}

			for (int i = 0; i < NUM_BUTTONS; i++) {
				if (i == currentPageIdx) {
					pageButtons[i].icon().color(TITLE_COLOR);
				} else {
					pageButtons[i].icon().resetColor();
				}
			}
			
			if (currentPageIdx == -1){
				return;
			}
			
			for (QuickRecipe r : recipes){
				if (r != null) {
					r.killAndErase();
					r.destroy();
				}
			}
			recipes.clear();
			
			Component content = list.content();
			
			content.clear();
			
			title.visible = true;
			title.label(Document.ALCHEMY_GUIDE.pageTitle(currentPageIdx));
			title.setRect(0, 0, width(), 10);
			content.add(title);
			
			body.maxWidth((int)width());
			body.text(Document.ALCHEMY_GUIDE.pageBody(currentPageIdx));
			body.setPos(0, title.bottom());
			content.add(body);

			Document.ALCHEMY_GUIDE.readPage(currentPageIdx);
			
			ArrayList<QuickRecipe> toAdd = QuickRecipe.getRecipes(currentPageIdx);
			
			float left;
			float top = body.bottom()+2;
			int w;
			ArrayList<QuickRecipe> toAddThisRow = new ArrayList<>();
			while (!toAdd.isEmpty()){
				if (toAdd.get(0) == null){
					toAdd.remove(0);
					top += 6;
				}
				
				w = 0;
				while(!toAdd.isEmpty() && toAdd.get(0) != null
						&& w + toAdd.get(0).width() <= width()){
					toAddThisRow.add(toAdd.remove(0));
					w += toAddThisRow.get(0).width();
				}
				
				float spacing = (width() - w)/(toAddThisRow.size() + 1);
				left = spacing;
				while (!toAddThisRow.isEmpty()){
					QuickRecipe r = toAddThisRow.remove(0);
					r.setPos(left, top);
					left += r.width() + spacing;
					if (!toAddThisRow.isEmpty()) {
						ColorBlock spacer = new ColorBlock(1, 16, 0xFF222222);
						spacer.y = top;
						spacer.x = left - spacing / 2 - 0.5f;
						PixelScene.align(spacer);
						content.add(spacer);
					}
					recipes.add(r);
					content.add(r);
				}
				
				if (!toAdd.isEmpty() && toAdd.get(0) == null){
					toAdd.remove(0);
				}
				
				if (!toAdd.isEmpty() && toAdd.get(0) != null) {
					ColorBlock spacer = new ColorBlock(width(), 1, 0xFF222222);
					spacer.y = top + 16;
					spacer.x = 0;
					content.add(spacer);
				}
				top += 17;
				toAddThisRow.clear();
			}
			top -= 1;
			content.setSize(width(), top);
			list.setSize(list.width(), list.height());
			list.scrollTo(0, 0);
		}
	}
	
	private static class NotesTab extends Component {
		
		private ScrollingListPane list;
		
		@Override
		protected void createChildren() {
			list = new ScrollingListPane();
			add( list );
		}
		
		@Override
		protected void layout() {
			super.layout();
			list.setRect( x, y, width, height);
		}
		
		private void updateList(){
			//Keys
			ArrayList<Notes.KeyRecord> keys = Notes.getRecords(Notes.KeyRecord.class);
			if (!keys.isEmpty()){
				list.addTitle(Messages.get(this, "keys"));

				for(Notes.Record rec : keys){
					ScrollingListPane.ListItem item = new ScrollingListPane.ListItem( Icons.get(Icons.STAIRS),
							Integer.toString(rec.depth()),
							Messages.titleCase(rec.desc()));
					if (Dungeon.depth == rec.depth()) item.hardlight(TITLE_COLOR);
					list.addItem(item);
				}
			}
			
			//Landmarks
			ArrayList<Notes.LandmarkRecord> landmarks = Notes.getRecords(Notes.LandmarkRecord.class);
			if (!landmarks.isEmpty()){

				list.addTitle(Messages.get(this, "landmarks"));

				for (Notes.Record rec : landmarks) {
					ScrollingListPane.ListItem item = new ScrollingListPane.ListItem( Icons.get(Icons.STAIRS),
							Integer.toString(rec.depth()),
							Messages.titleCase(rec.desc()));
					if (Dungeon.depth == rec.depth()) item.hardlight(TITLE_COLOR);
					list.addItem(item);
				}

			}

			list.setRect(x, y, width, height);
		}
		
	}
	
	private static class CatalogTab extends Component{
		
		private RedButton[] itemButtons;
		private static final int NUM_BUTTONS = 12;
		
		private static int currentItemIdx   = 0;
		
		//sprite locations
		private static final int WEAPON_IDX = 0;
		private static final int ARMOR_IDX  = 1;
		private static final int WAND_IDX   = 2;
		private static final int RING_IDX   = 3;
		private static final int ARTIF_IDX  = 4;
		private static final int POTION_IDX = 5;
		private static final int SCROLL_IDX = 6;
		private static final int SEED_IDX = 7;
		private static final int DART_IDX = 8;
		private static final int ENCH_IDX = 9;
		private static final int GLYPH_IDX = 10;
		private static final int MOB_IDX = 11;
		
		private static final int spriteIndexes[] = {1, 2, 4, 5, 6, 9, 11, 10, 3, 17, 16, 18};

		private ScrollingListPane list;

		@Override
		protected void createChildren() {
			itemButtons = new RedButton[NUM_BUTTONS];
			for (int i = 0; i < NUM_BUTTONS; i++){
				final int idx = i;
				itemButtons[i] = new RedButton( "" ){
					@Override
					protected void onClick() {
						currentItemIdx = idx;
						updateList();
					}
				};
				itemButtons[i].icon(new ItemSprite(ItemSpriteSheet.SOMETHING + spriteIndexes[i], null));
				add( itemButtons[i] );
			}

			list = new ScrollingListPane();
			add( list );
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			int perRow = NUM_BUTTONS;
			for (int rows = 1; ; ++rows) {
				int perRowTemp = (NUM_BUTTONS + rows - 1) / rows;
				float btnWidthTemp = width() / perRowTemp;
				if (btnWidthTemp >= ITEM_HEIGHT * 0.67) {
					perRow = perRowTemp;
					break;
				}
			}
			float buttonWidth = width()/perRow;
			
			for (int i = 0; i < NUM_BUTTONS; i++) {
				itemButtons[i].setRect((i%perRow) * (buttonWidth), (i/perRow) * (ITEM_HEIGHT ),
						buttonWidth, ITEM_HEIGHT);
				PixelScene.align(itemButtons[i]);
			}
			
			list.setRect(0, itemButtons[NUM_BUTTONS-1].bottom() + 1, width,
					height - itemButtons[NUM_BUTTONS-1].bottom() - 1);
		}
		
		private void updateList() {
			
			list.clear();
			
			for (int i = 0; i < NUM_BUTTONS; i++){
				if (i == currentItemIdx){
					itemButtons[i].icon().color(TITLE_COLOR);
				} else {
					itemButtons[i].icon().resetColor();
				}
			}
			
			list.scrollTo( 0, 0 );
			
			ArrayList<Class<?/* extends Item*/>> itemClasses;
			final HashMap<Class<?  extends Item>, Boolean> known = new HashMap<>();
			if (currentItemIdx == WEAPON_IDX) {
				itemClasses = new ArrayList<>(Catalog.WEAPONS.items());
				for (Class<? extends Item> cls : Catalog.WEAPONS.items()) known.put(cls, true);
			} else if (currentItemIdx == ARMOR_IDX){
				itemClasses = new ArrayList<>(Catalog.ARMOR.items());
				for (Class<? extends Item> cls : Catalog.ARMOR.items()) known.put(cls, true);
			} else if (currentItemIdx == WAND_IDX){
				itemClasses = new ArrayList<>(Catalog.WANDS.items());
				for (Class<? extends Item> cls : Catalog.WANDS.items()) known.put(cls, true);
			} else if (currentItemIdx == RING_IDX){
				itemClasses = new ArrayList<>(Catalog.RINGS.items());
				for (Class<? extends Item> cls : Catalog.RINGS.items()) known.put(cls, Ring.getKnown().contains(cls));
			} else if (currentItemIdx == ARTIF_IDX){
				itemClasses = new ArrayList<>(Catalog.ARTIFACTS.items());
				for (Class<? extends Item> cls : Catalog.ARTIFACTS.items()) known.put(cls, true);
			} else if (currentItemIdx == POTION_IDX){
				itemClasses = new ArrayList<>(Catalog.POTIONS.items());
				for (Class<? extends Item> cls : Catalog.POTIONS.items()) known.put(cls, Potion.getKnown().contains(cls));
			} else if (currentItemIdx == SCROLL_IDX) {
				itemClasses = new ArrayList<>(Catalog.SCROLLS.items());
				for (Class<? extends Item> cls : Catalog.SCROLLS.items()) known.put(cls, Scroll.getKnown().contains(cls));
			} else if (currentItemIdx == SEED_IDX) {
				itemClasses = new ArrayList<>(Catalog.SEEDS.items());
				for (Class<? extends Item> cls : Catalog.SEEDS.items()) known.put(cls, true);
			} else if (currentItemIdx == DART_IDX) {
				itemClasses = new ArrayList<>(Catalog.DARTS.items());
				for (Class<? extends Item> cls : Catalog.DARTS.items()) known.put(cls, true);
			} else if (currentItemIdx == GLYPH_IDX) {
				itemClasses = new ArrayList<>(Arrays.asList(Armor.Glyph.common));
				itemClasses.addAll(Arrays.asList(Armor.Glyph.uncommon));
				itemClasses.addAll(Arrays.asList(Armor.Glyph.rare));
				itemClasses.addAll(Arrays.asList(Armor.Glyph.curses));
			} else if (currentItemIdx == ENCH_IDX) {
				itemClasses = new ArrayList<>(Arrays.asList(Weapon.Enchantment.common));
				itemClasses.addAll(Arrays.asList(Weapon.Enchantment.uncommon));
				itemClasses.addAll(Arrays.asList(Weapon.Enchantment.rare));
				itemClasses.addAll(Arrays.asList(Weapon.Enchantment.curses));
			} else if (currentItemIdx == MOB_IDX) {
				itemClasses = Bestiary.getAllMobs();
			} else {
				itemClasses = new ArrayList<>();
			}
			
			Collections.sort(itemClasses, new Comparator<Class<?>>() {
				@Override
				public int compare(Class<?> a, Class<?> b) {
					if (!Item.class.isAssignableFrom(a)) {
						return 0;
					}
					int result = 0;
					
					//specifically known items appear first, then seen items, then unknown items.
					if (known.get(a) && Catalog.isSeen((Class<? extends Item>) a)) result -= 2;
					if (known.get(b) && Catalog.isSeen((Class<? extends Item>) b)) result += 2;
					if (Catalog.isSeen((Class<? extends Item>) a))                 result --;
					if (Catalog.isSeen((Class<? extends Item>) b))                 result ++;
					
					return result;
				}
			});
			
			for (Class<?> itemClass : itemClasses) {
				Item item;
				boolean itemSeen;
				if (Item.class.isAssignableFrom(itemClass)) {
					item = (Item) Reflection.newInstance(itemClass);
					itemSeen = Catalog.isSeen((Class<? extends Item>) itemClass);
				} else if (Armor.Glyph.class.isAssignableFrom(itemClass)) {
					item = new Armor.GlyphHolder();
					((Armor)item).glyph = (Armor.Glyph) Reflection.newInstance(itemClass);
					itemSeen = true;
				} else if (Weapon.Enchantment.class.isAssignableFrom(itemClass)) {
					item = new MeleeWeapon.EnchantHolder();
					((Weapon)item).enchantment = (Weapon.Enchantment) Reflection.newInstance(itemClass);
					itemSeen = true;
				} else if (Mob.class.isAssignableFrom(itemClass)) {
					Mob mob = (Mob) Reflection.newInstance(itemClass);
					if (mob instanceof Mimic) {
						((Mimic) mob).setLevel(Dungeon.scalingDepth());
						mob.alignment = Char.Alignment.ENEMY;
					} else if (mob instanceof Wraith) {
						((Wraith) mob).adjustStats(Dungeon.scalingDepth());
					} else if (mob instanceof Bee) {
						((Bee) mob).spawn(Dungeon.scalingDepth());
						mob.HP = mob.HT;
						//This doesn't actually spawn anything, only adjusts stats
					}
					item = new Mob.MobItem(mob);
					itemSeen = true;
				} else {
					continue;
				}
				boolean itemIDed;
				if (known.containsKey(itemClass)) {
					itemIDed = known.get(itemClass);
				} else {
					itemIDed = true;
				}
				if ( itemSeen && !itemIDed ){
					if (item instanceof Ring){
						((Ring) item).anonymize();
					} else if (item instanceof Potion){
						((Potion) item).anonymize();
					} else if (item instanceof Scroll){
						((Scroll) item).anonymize();
					}
				}
				ScrollingListPane.ListItem listItem = new ScrollingListPane.ListItem(
						(itemIDed && itemSeen) ? getImage(item) : new ItemSprite( ItemSpriteSheet.SOMETHING + spriteIndexes[currentItemIdx]),
						null,
						itemSeen ? Messages.titleCase(getDisplayedName(item)) : "???"
				){
					@Override
					public boolean onClick(float x, float y) {
						if (inside( x, y ) && itemSeen) {
							if (item instanceof ClassArmor){
								GameScene.show(new WndTitledMessage(new Image(icon),
										Messages.titleCase(getDisplayedName(item)), item.desc()));
							} else {
								GameScene.show(new WndTitledMessage(new Image(icon),
										Messages.titleCase(getDisplayedName(item)), item.info()));
							}
							return true;
						} else {
							return false;
						}
					}
				};

				if (!itemSeen) {
					listItem.hardlight( 0x999999 );
				} else if (!itemIDed) {
					listItem.hardlight( 0xCCCCCC );
				}

				list.addItem(listItem);

			}

			list.setRect(x, itemButtons[NUM_BUTTONS-1].bottom() + 1, width,
					height - itemButtons[NUM_BUTTONS-1].bottom() - 1);
		}

		private static String getDisplayedName(Item i) {
			if (i instanceof Armor.GlyphHolder) {
				return ((Armor) i).glyph.name();
			}
			if (i instanceof MeleeWeapon.EnchantHolder) {
				return ((Weapon) i).enchantment.name();
			}
			if (i instanceof Mob.MobItem) {
				return i.name();
			}
			return i.trueName();
		}

		private static Image getImage(Item i) {
			if (i instanceof Mob.MobItem) {
				return new Image(((Mob.MobItem) i).mob.sprite().forceIdling());
			}
			return new ItemSprite(i);
		}
		
	}

	public static class LoreTab extends Component{

		private ScrollingListPane list;

		@Override
		protected void createChildren() {
			list = new ScrollingListPane();
			add( list );
		}

		@Override
		protected void layout() {
			super.layout();
			list.setRect( 0, 0, width, height);
		}

		private void updateList(){
			list.addTitle(Messages.get(this, "title"));

			for (Document doc : Document.values()){
				if (!doc.isLoreDoc()) continue;

				boolean found = doc.anyPagesFound();
				ScrollingListPane.ListItem item = new ScrollingListPane.ListItem(
						doc.pageSprite(),
						null,
						found ? Messages.titleCase(doc.title()) : "???"
				){
					@Override
					public boolean onClick(float x, float y) {
						if (inside( x, y ) && found) {
							ShatteredPixelDungeon.scene().addToFront( new WndDocument( doc ));
							return true;
						} else {
							return false;
						}
					}
				};
				if (!found){
					item.hardlight(0x999999);
					item.hardlightIcon(0x999999);
				}
				list.addItem(item);
			}

			list.setRect(x, y, width, height);
		}

	}
	
}
