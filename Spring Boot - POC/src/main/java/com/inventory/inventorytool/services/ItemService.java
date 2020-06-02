package com.inventory.inventorytool.services;

import com.inventory.inventorytool.domain.Item;
import com.inventory.inventorytool.exceptions.ItemIdException;
import com.inventory.inventorytool.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveOrUpdateItem(Item item) {
        try {
            item.setItemIdentifier(item.getItemIdentifier().toUpperCase());
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new ItemIdException("Item ID '" + item.getItemIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Item findItemByIdentifier(String itemId) {

        Item item = itemRepository.findByItemIdentifier(itemId.toUpperCase());
        if (item == null) {
            throw new ItemIdException("Item ID '" + itemId +"' does not exists");
        }
        return item;
    }

    public Iterable<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItemByIdentifier(String itemId) {
        Item item = itemRepository.findByItemIdentifier(itemId.toUpperCase());
        if(item == null) {
            throw new ItemIdException("Cannot delete Item with ID '" + itemId +"'. This item does not exists");
        }
        itemRepository.delete(item);
    }
}
