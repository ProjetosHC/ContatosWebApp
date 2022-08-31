package com.npi.contatosweb.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
// import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.npi.contatosweb.entity.Contato;
import com.npi.contatosweb.entity.Usuario;
import com.npi.contatosweb.helper.Mensagem;
import com.npi.contatosweb.repository.ContatoRepositorio;
import com.npi.contatosweb.repository.UsuarioRepositorio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepositorio uRepo;

    @Autowired
    private ContatoRepositorio cRepo;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String userName = principal.getName();
        // System.out.println("USUARIO " + userName);
        Usuario usuario = uRepo.getUserByUserName(userName);
        // System.out.println("USER " + usuario);
        model.addAttribute("usuario", usuario);
    }

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("title", "Pagina Inicial Usuario");
        return "user/usuario_dashboard";
    }

    @GetMapping("/novo-contato")
    public String addContatoForm(Model model) {
        model.addAttribute("title", "Adicionar Contato");
        model.addAttribute("contato", new Contato());
        return "user/add-contato";
    }

    @PostMapping("/salvar-contato")
    public String salvarContato(@Valid @ModelAttribute("contato") Contato contato, @RequestParam("cpimagem") MultipartFile file, Model model, Principal principal, HttpSession session) {

        model.addAttribute("title", "Adicionar Contato");
        try {
            Optional<Contato> ctelefone = this.cRepo.findByTelefone(contato.getTelefone());
            if(ctelefone.isPresent()) {
                throw new Exception("Telefone já cadastrado!");
            }

            String name = principal.getName();
            Usuario usuario = this.uRepo.getUserByUserName(name);

            if(file.isEmpty()) {
                System.out.println("Arquivo está vazio!");
                contato.setImagem("contato.png");
            } else {
                contato.setImagem(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            contato.setUsuario(usuario);
            usuario.getContatos().add(contato);
            this.uRepo.save(usuario);

            session.setAttribute("mensagem", new Mensagem("Contato adicionado com sucesso!", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("contato", contato);
            session.setAttribute("mensagem", new Mensagem("Erro! Contato não pode ser adicionado! " + e.getMessage(), "alert-danger"));
        }
        // System.out.println("Adicionado ao BD...");
            return "user/add-contato";
    }

    @GetMapping("/ver-contatos/{page}")
    public String mostrarContatos(@PathVariable("page") Integer page, Model model, Principal principal) {

        String userName = principal.getName();
        Usuario usuario = this.uRepo.getUserByUserName(userName);

        Pageable pageable = PageRequest.of(page, 3);
        Page<Contato> contatos = this.cRepo.findContactsByUser(usuario.getId(), pageable);
        model.addAttribute("contatos", contatos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contatos.getTotalPages());
        model.addAttribute("title", "Mostrar Contatos do Usuário");
        return "user/mostrar-contatos";
    }

    @RequestMapping("/contato/{cId}")
    public String mostrarContato(@PathVariable("cId") Long cId, Model model, Principal principal) {

        Optional<Contato> contatoOpcional = this.cRepo.findById(cId);
        Contato contato = contatoOpcional.get();

        String userName = principal.getName();
        Usuario usuario = this.uRepo.getUserByUserName(userName);

        if(usuario.getId()==contato.getUsuario().getId()) {
            model.addAttribute("contato", contato);
            model.addAttribute("title", contato.getNome());
        }

        return "user/contato-perfil";
    }

    @GetMapping("/apagar/{cId}")
    public String deletarContato(@PathVariable("cId") Long cId, Model model, HttpSession session, Principal principal) {

        // Optional<Contato> contatoOpcional = this.cRepo.findById(cId);
        // Contato contato = contatoOpcional.get();
        Contato contato = this.cRepo.findById(cId).get();

        Usuario usuario = this.uRepo.getUserByUserName(principal.getName());
        usuario.getContatos().remove(contato);
        this.uRepo.save(usuario);

        session.setAttribute("mensagem", new Mensagem("Contato deletado!", "alert-success"));

        return "redirect:/usuario/ver-contatos/0";
    }

    @PostMapping("/atualizar/{cId}")
    public String atualizarContato(@PathVariable("cId") Long cId, Model model) {

        Contato contato = this.cRepo.findById(cId).get();

        model.addAttribute("title", "Atualizar " + contato.getNome());
        model.addAttribute("contato", contato);

        return "user/atualizar-contato";
    }

    @RequestMapping(value = "/atualizar-processo", method = RequestMethod.POST)
    public String processoAtualizar(@ModelAttribute Contato contato, @RequestParam("cpimagem") MultipartFile file, Model model, HttpSession session, Principal principal) {

        try {
            // Optional<Contato> ctelefone = this.cRepo.findByTelefone(contato.getTelefone());
            // if(ctelefone.isPresent()) {
            //     throw new Exception("Telefone já cadastrado!");
            // }

            Contato dadosAntigos = this.cRepo.findById(contato.getcId()).get();

            if(!file.isEmpty()) {
                File deleteFile = new ClassPathResource("static/img").getFile();
                File dfile = new File(deleteFile, dadosAntigos.getImagem());
                dfile.delete();

                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contato.setImagem(file.getOriginalFilename());
            } else {
                contato.setImagem(dadosAntigos.getImagem());
            }

            Usuario usuario = this.uRepo.getUserByUserName(principal.getName());
            contato.setUsuario(usuario);
            this.cRepo.save(contato);
            session.setAttribute("mensagem", new Mensagem("Contato atualizado com sucesso!", "alert-success"));
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagem", new Mensagem("Erro! Contato não pode ser atualizado! " + e.getMessage(), "alert-danger"));
        }

        // System.out.println("NOME: " + contato.getNome());
        // System.out.println("ID: " + contato.getcId());

        return "redirect:/usuario/contato/" + contato.getcId();
    }
}
