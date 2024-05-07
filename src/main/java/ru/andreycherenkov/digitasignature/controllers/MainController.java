package ru.andreycherenkov.digitasignature.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.digitasignature.models.Document;
import ru.andreycherenkov.digitasignature.services.EncryptorService;

@Controller
@RequestMapping("/lab4")
public class MainController {

    @GetMapping("")
    public String index() {
        return "index.html";
    }

    @PostMapping("/process")
    public String process(@RequestParam("data") String data,
                          @RequestParam("key") String key,
                          Model model,
                          HttpSession session) {

        String hash = EncryptorService.hashData(data);
        String signature = EncryptorService.encrypt(hash, key.length());

        model.addAttribute("data", data);

        model.addAttribute("hash", hash);
        model.addAttribute("signature", signature);

        Document document = new Document(data, signature);
        model.addAttribute("document", document);
        session.setAttribute("document", document);
        session.setAttribute("key", key);
        return "index.html";
    }

    @GetMapping("/modify")
    public String modifyData(HttpSession session,
                    Model model) {
        Document document = (Document)session.getAttribute("document");
        model.addAttribute("data", document.getData());
        session.setAttribute("document", document);
        return "modify.html";
    }

    @PostMapping("/modify")
    public String modifyData(@RequestParam("data") String data,
                            HttpSession session,
                             Model model) {
        Document document = (Document)session.getAttribute("document");
        document.setData(data);
        model.addAttribute("document", document);
        return "index.html";
    }
}
