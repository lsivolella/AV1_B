package com.sivolella.AV1B.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sivolella.AV1B.model.Candidato;
import com.sivolella.AV1B.service.CandidatosTseService;

@Controller
public class CandidatosTseController {
    private final CandidatosTseService candidatoService;

    public CandidatosTseController(CandidatosTseService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String escolaridade,
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax,
            Model model) {

        candidatoService.carregarCsv();

        List<Candidato> resultado = candidatoService.filtrarPerfil(genero, escolaridade, idadeMin, idadeMax);

        model.addAttribute("candidatos", resultado);
        model.addAttribute("totalEncontrado", resultado.size());
        model.addAttribute("generos", candidatoService.listarGeneros());
        model.addAttribute("escolaridade", candidatoService.listarEscolaridades());

        // | `candidatos` | Lista filtrada |
        // | `totalEncontrado` | Tamanho da lista filtrada |
        // | `generos` | `listarGeneros()` (para os botões de opção) |
        // | `escolaridades` | `listarEscolaridades()` (para o `<select>`) |
        // | `generoSelecionado` / `escolaridadeSelecionada` | Valores atuais do filtro
        // (`""` quando não informados) |
        // | `idadeMin` / `idadeMax` | Valores atuais da faixa etária (`null` quando não
        // informados) |

        IO.println(model.toString());

        return "index";
    }
}
