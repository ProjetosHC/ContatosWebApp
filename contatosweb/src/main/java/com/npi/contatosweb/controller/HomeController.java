package com.npi.contatosweb.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.npi.contatosweb.entity.Usuario;
import com.npi.contatosweb.helper.Mensagem;
import com.npi.contatosweb.repository.UsuarioRepositorio;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepositorio uRepo;
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Inicio - Contatos WebApp");
        return "home";
    }

    @RequestMapping("/sobre")
    public String sobre(Model model) {
        model.addAttribute("title", "Sobre - Contatos WebApp");
        return "sobre";
    }

    @RequestMapping("/inscrever")
    public String inscrever(Model model) {
        model.addAttribute("title", "Registrar Usuário");
        model.addAttribute("usuario", new Usuario());
        return "inscrever";
    }


    @RequestMapping(value = "/fzr_registro", method = RequestMethod.POST)
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, @RequestParam(value = "termos", defaultValue = "false") boolean termos, Model model, HttpSession session, Principal principal) {

        try {
            Optional<Usuario> uemail = this.uRepo.findByEmail(usuario.getEmail());
            if(uemail.isPresent()) {
                throw new Exception("E-mail já utilizado");
            }

            if(!termos) {
                // System.out.println("Você não aceitou os termos de uso!");
                throw new Exception("Você não aceitou os termos de uso!");
            }
            if(result.hasErrors()) {
                // System.out.println("ERROR " + result.toString());
                model.addAttribute("usuario", usuario);
                return "inscrever";
            }

            usuario.setRole("ROLE_USUARIO");
            usuario.setAtivo(true);
            usuario.setImagemUrl("default.png");
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    
            // System.out.println("Termos " + termos);
            // System.out.println("Usuario " + usuario);
            
            this.uRepo.save(usuario);
            model.addAttribute("usuario", new Usuario());
            session.setAttribute("mensagem", new Mensagem("Registrado com sucesso! ", "alert-success"));
            return "inscrever";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("usuario", usuario);
            session.setAttribute("mensagem", new Mensagem("Ocorreu um erro! " + e.getMessage(), "alert-danger"));
            return "inscrever";
        }
        
    }

    @GetMapping("/entrar")
    public String customLogin(Model model) {
        model.addAttribute("title", "Entrar - Contatos WebApp");
        return "login";
    }

}
