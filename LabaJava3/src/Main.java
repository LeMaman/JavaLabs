package com.gildedrose;
/*на першому етапі рефакторингу розбив логіку на невеликі методи
* else замінив на виходи через return
* спробував дотримуватися максимум одного відступу
* але деінде не вийшло, далі поправлю, сподіваюся
* */
class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (isLegendary(item)) {
                continue;
            }

            updateSellIn(item);

            if (isSpecialItem(item)) {
                updateSpecialItemQuality(item);
                continue;
            }

            degradeQuality(item);

            if (isExpired(item)) {
                handleExpiredItem(item);
            }
        }
    }

    private boolean isLegendary(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private void updateSellIn(Item item) {
        item.sellIn -= 1;
    }

    private boolean isSpecialItem(Item item) {
        return item.name.equals("Aged Brie") || item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private void updateSpecialItemQuality(Item item) {
        increaseQuality(item);

        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.sellIn < 10) {
                increaseQuality(item);
            }
            if (item.sellIn < 5) {
                increaseQuality(item);
            }
        }
    }

    private void degradeQuality(Item item) {
        if (item.quality > 0) {
            item.quality -= 1;
        }
    }

    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private void handleExpiredItem(Item item) {
        if (item.name.equals("Aged Brie")) {
            increaseQuality(item);
            return;
        }

        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            item.quality = 0;
            return;
        }

        degradeQuality(item);
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality += 1;
        }
    }
}
