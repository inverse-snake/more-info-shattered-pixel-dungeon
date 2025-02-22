package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.watabou.noosa.Image;

public class ItemIconTitle extends IconTitle {
    private IconButton noteButton = null;
    private IconButton aimButton = null;

    public ItemIconTitle(Item item, WndUseItem window ) {
        super(item);

        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)) {
            noteButton = new IconButton(noteIconByText(item.notes)) {
                @Override
                protected void onClick() {
                    item.editNotes(noteButton);
                }
            };
            add(noteButton);

            if (item.needsAim()) {
                aimButton = new IconButton(new BuffIcon(BuffIndicator.MARK, true)) {
                    @Override
                    protected void onClick() {
                        window.hide();
                        if (window.owner != null && window.owner.parent != null) {
                            window.owner.hide();
                        }
                        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)){
                            item.execute( Dungeon.hero, Item.AC_AIM );
                        }
                        Item.updateQuickslot();
                    }
                };
                add(aimButton);
            }
        }
    }//vjm-dww-rxl

    public static Image noteIconByText(String text) {
        return new ItemSprite(ItemSpriteSheet.STYLUS, text.isEmpty() ? null : new ItemSprite.Glowing(0xFFFFFF, 3f));
    }

    @Override
    protected void layout() {
        int shift = 16;
        if (noteButton != null) {
            noteButton.setRect(x + width - shift, y, 16, 16);
            shift += 16 + 3;
            PixelScene.align(noteButton);
        }
        if (aimButton != null) {
            aimButton.setRect(x + width - shift, y, 16, 16);
            shift += 16 + 3;
            PixelScene.align(aimButton);
        }

        imIcon.x = x + (Math.max(0, 8 - imIcon.width()/2));
        imIcon.y = y + (Math.max(0, 8 - imIcon.height()/2));
        PixelScene.align(imIcon);

        int imWidth = (int)Math.max(imIcon.width(), 16);
        int imHeight = (int)Math.max(imIcon.height(), 16);

        tfLabel.maxWidth((int)(width - (imWidth + 2) - shift + 16));
        tfLabel.setPos(x + imWidth + 2,
                imHeight > tfLabel.height() ? y +(imHeight - tfLabel.height()) / 2 : y);
        PixelScene.align(tfLabel);

        if (health.visible) {
            health.setRect( tfLabel.left(), tfLabel.bottom(), tfLabel.maxWidth(), 0 );
            height = Math.max( imHeight, health.bottom() );
        } else {
            height = Math.max( imHeight, tfLabel.height() );
        }
    }
}
