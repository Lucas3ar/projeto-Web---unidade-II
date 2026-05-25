package com.retro.store.service;

import com.retro.store.dto.GameItemForm;
import com.retro.store.exception.RecursoNaoEncontradoException;
import com.retro.store.model.GameItem;
import com.retro.store.repository.GameItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameItemService {
    private final GameItemRepository repository;

    private static final List<String> IMAGES = Arrays.asList(
            "/img/retro-1.svg", "/img/retro-2.svg", "/img/retro-3.svg", "/img/retro-4.svg"
    );

    public List<GameItem> listarAtivos() { return repository.findByIsDeletedIsNull(); }
    public List<GameItem> listarTodos() { return repository.findAll(); }

    public GameItem buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Item nao encontrado: " + id));
    }

    public GameItem salvar(GameItemForm form) {
        GameItem item = form.getId() != null ? buscarPorId(form.getId()) : new GameItem();
        item.setTitle(form.getTitle()); item.setPlatform(form.getPlatform()); item.setGenre(form.getGenre());
        item.setSkuCode(form.getSkuCode()); item.setDescription(form.getDescription()); item.setPrice(form.getPrice());
        if (item.getImageUrl() == null || item.getImageUrl().isBlank()) item.setImageUrl(imageAleatoria());
        return repository.save(item);
    }

    public void deletarLogico(Long id) { GameItem i = buscarPorId(id); i.setIsDeleted(System.currentTimeMillis()); repository.save(i); }
    public void restaurar(Long id) { GameItem i = buscarPorId(id); i.setIsDeleted(null); repository.save(i); }

    private String imageAleatoria() {
        return IMAGES.get(ThreadLocalRandom.current().nextInt(IMAGES.size()));
    }
}
