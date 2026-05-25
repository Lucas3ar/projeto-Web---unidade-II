package com.retro.store.controller;

import com.retro.store.dto.GameItemForm;
import com.retro.store.model.GameItem;
import com.retro.store.service.GameItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameItemController {
    private final GameItemService service;

    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        model.addAttribute("itens", service.listarAtivos());
        model.addAttribute("qtdCarrinho", carrinho(session).size());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) { model.addAttribute("itens", service.listarTodos()); return "admin"; }

    @GetMapping("/cadastro")
    public String cadastro(Model model) { model.addAttribute("form", new GameItemForm()); return "form"; }

    @GetMapping("/editar")
    public String editar(@RequestParam Long id, Model model) {
        GameItem i = service.buscarPorId(id);
        GameItemForm f = new GameItemForm();
        f.setId(i.getId()); f.setTitle(i.getTitle()); f.setPlatform(i.getPlatform()); f.setGenre(i.getGenre());
        f.setSkuCode(i.getSkuCode()); f.setDescription(i.getDescription()); f.setPrice(i.getPrice());
        model.addAttribute("form", f);
        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("form") GameItemForm form, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) return "form";
        service.salvar(form);
        ra.addFlashAttribute("mensagem", "Atualizacao ocorreu com sucesso");
        return "redirect:/admin";
    }

    @GetMapping("/detalhe/{id}")
    public String detalhe(@PathVariable Long id, Model model) { model.addAttribute("item", service.buscarPorId(id)); return "detalhe"; }

    @GetMapping("/deletar")
    public String deletar(@RequestParam Long id, RedirectAttributes ra) { service.deletarLogico(id); ra.addFlashAttribute("mensagem", "Remocao ocorreu com sucesso"); return "redirect:/index"; }

    @GetMapping("/restaurar")
    public String restaurar(@RequestParam Long id, RedirectAttributes ra) { service.restaurar(id); ra.addFlashAttribute("mensagem", "Restauracao ocorreu com sucesso"); return "redirect:/index"; }

    @GetMapping("/adicionarCarrinho")
    public String adicionarCarrinho(@RequestParam Long id, HttpSession session, RedirectAttributes ra) {
        carrinho(session).add(service.buscarPorId(id));
        ra.addFlashAttribute("mensagem", "Item adicionado ao carrinho");
        return "redirect:/index";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(HttpSession session, Model model, RedirectAttributes ra) {
        List<GameItem> carrinho = carrinho(session);
        if (carrinho.isEmpty()) { ra.addFlashAttribute("mensagem", "Nao existem itens no carrinho"); return "redirect:/index"; }
        double total = carrinho.stream().map(GameItem::getPrice).filter(java.util.Objects::nonNull).mapToDouble(Double::doubleValue).sum();
        model.addAttribute("itens", carrinho);
        model.addAttribute("totalCarrinho", total);
        return "carrinho";
    }

    @GetMapping("/removerCarrinho")
    public String removerCarrinho(@RequestParam Long id, HttpSession session, RedirectAttributes ra) {
        List<GameItem> carrinho = carrinho(session);
        carrinho.removeIf(item -> item.getId() != null && item.getId().equals(id));
        ra.addFlashAttribute("mensagem", "Item removido do carrinho");
        return "redirect:/verCarrinho";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session, RedirectAttributes ra) {
        session.removeAttribute("carrinho");
        ra.addFlashAttribute("mensagem", "Obrigado pela compra!");
        return "redirect:/index";
    }

    @SuppressWarnings("unchecked")
    private List<GameItem> carrinho(HttpSession session) {
        Object carrinho = session.getAttribute("carrinho");
        if (carrinho == null) {
            List<GameItem> novo = new ArrayList<>();
            session.setAttribute("carrinho", novo);
            return novo;
        }
        return (List<GameItem>) carrinho;
    }
}
