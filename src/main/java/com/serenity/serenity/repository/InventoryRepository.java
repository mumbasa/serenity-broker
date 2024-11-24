package com.serenity.serenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serenity.serenity.data.his.SerenityInventoryStore;

@Repository
public interface InventoryRepository extends JpaRepository<SerenityInventoryStore,Long> {

}
