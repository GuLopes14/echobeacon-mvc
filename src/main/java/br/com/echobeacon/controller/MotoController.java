package br.com.echobeacon.controller;

import br.com.echobeacon.model.Moto;
import br.com.echobeacon.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public String listarMotos(Model model) {
        List<Moto> todasMotos = motoService.listarTodos();
        List<Moto> motosRecepcao = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() == null)
            .toList();
        List<Moto> motosPatio = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() != null)
            .toList();

        model.addAttribute("motosRecepcao", motosRecepcao);
        model.addAttribute("motosPatio", motosPatio);
        return "motos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("moto", new Moto());
        return "cadastro-moto";
    }

    @PostMapping
    public String salvarMoto(@ModelAttribute Moto moto) {
        motoService.salvar(moto);
        return "redirect:/motos";
    }
}