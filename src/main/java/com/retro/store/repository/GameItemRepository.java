package com.retro.store.repository;

import com.retro.store.model.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    List<GameItem> findByIsDeletedIsNull();
}
