package ru.andreycherenkov.digitasignature.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andreycherenkov.digitasignature.models.Document;
import ru.andreycherenkov.digitasignature.services.EncryptorService;

@Controller
@RequestMapping("/lab4")
public class ReceiverController {

    @GetMapping("/verify")
    public String verify(HttpSession session,
                         Model model) {
        Document document = (Document) session.getAttribute("document");
        if (document == null) {
            return "index.html";
        }
        // берем хеш от данных
        // расшифровываем нашу подпись ключом
        // сравниваем полученный и тот который сделалли сами
        String hash = EncryptorService.hashData(document.getData());
        String decryptedSignature = EncryptorService.decrypt(document.getSignature(), ((String)session.getAttribute("key")).length());

        model.addAttribute("isOriginal", hash.equals(decryptedSignature));
        model.addAttribute("document", document);
        model.addAttribute("data", document.getData());
        return "verify.html";
    }
}
