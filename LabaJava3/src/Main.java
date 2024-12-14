package com.gildedrose;
/*на першому етапі рефакторингу розбив логіку на невеликі методи
* else замінив на виходи через return
* спробував дотримуватися максимум одного відступу
* але деінде не вийшло, далі поправлю, сподіваюся
*
*
*
* Хотів розбити для ясності на три етапи задля ясності процесу
* але під час Wrapping'у все якось встало на свої місця одразу
* й не бачив сенсу якісь проміжкові етапи робити
* Цю ідею з Wrapper`ами підглянув у розумних людей на StackOverflow:)
*
* Всі принципи вже, ніби, враховані в цей раз
* */
class GildedRose {
    private final ItemCollection items;

    public GildedRose(Item[] items) {
        this.items = new ItemCollection(items);
    }

    public void updateQuality() {
        items.updateQualityForAll();
    }
}

class ItemCollection {
    private final ItemWrapper[] items;

    public ItemCollection(Item[] items) {
        this.items = wrapItems(items);
    }

    private ItemWrapper[] wrapItems(Item[] items) {
        ItemWrapper[] wrappedItems = new ItemWrapper[items.length];
        for (int i = 0; i < items.length; i++) {
            wrappedItems[i] = ItemWrapperFactory.create(items[i]);
        }
        return wrappedItems;
    }

    public void updateQualityForAll() {
        for (ItemWrapper item : items) {
            item.update();
        }
    }
}
/*Основна ідея полягає в тому, що ми опрацьовуємо кожен тип предмету по своєму
 * та застосовуємо унікальні способи опрацювання через Override до кожного
 * Я не впевнений, що зберіглася на 100% логіка, але ніби, все повинно було
 * залишитися так, як і було
 */
abstract class ItemWrapper {
    protected final Item item;

    public ItemWrapper(Item item) {
        this.item = item;
    }

    public final void update() {
        if (isSulfuras()) {
            return;
        }
        updateSellIn();
        updateQuality();
        if (isExpired()) {
            handleExpiration();
        }
    }

    protected boolean isSulfuras() {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    protected void updateSellIn() {
        item.sellIn -= 1;
    }

    protected abstract void updateQuality();

    protected boolean isExpired() {
        return item.sellIn < 0;
    }

    protected abstract void handleExpiration();
}
//тут super, бо йде до батьківського класу
class AgedBrieWrapper extends ItemWrapper {
    public AgedBrieWrapper(Item item) {
        super(item);
    }

    @Override
    protected void updateQuality() {
        increaseQuality();
    }

    @Override
    protected void handleExpiration() {
        increaseQuality();
    }

    private void increaseQuality() {
        if (item.quality < 50) {
            item.quality += 1;
        }
    }
}

class BackstagePassWrapper extends ItemWrapper {
    public BackstagePassWrapper(Item item) {
        super(item);
    }

    @Override
    protected void updateQuality() {
        increaseQuality();
        if (item.sellIn < 10) {
            increaseQuality();
        }
        if (item.sellIn < 5) {
            increaseQuality();
        }
    }

    @Override
    protected void handleExpiration() {
        item.quality = 0;
    }

    private void increaseQuality() {
        if (item.quality < 50) {
            item.quality += 1;
        }
    }
}

class StandardItemWrapper extends ItemWrapper {
    public StandardItemWrapper(Item item) {
        super(item);
    }

    @Override
    protected void updateQuality() {
        decreaseQuality();
    }

    @Override
    protected void handleExpiration() {
        decreaseQuality();
    }

    private void decreaseQuality() {
        if (item.quality > 0) {
            item.quality -= 1;
        }
    }
}

class ItemWrapperFactory {
    public static ItemWrapper create(Item item) {
        if (item.name.equals("Aged Brie")) {
            return new AgedBrieWrapper(item);
        }
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return new BackstagePassWrapper(item);
        }
        return new StandardItemWrapper(item);
    }
}
