package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;
import com.lagacione.faculdademarotinhaapi.dto.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.dto.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetorno;
import com.lagacione.faculdademarotinhaapi.services.AlunoService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/aluno")
public class AlunoResource {
    @Autowired
    private AlunoService alunoService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<AlunoDTO>> findAll() {
        return ResponseEntity.ok().body(this.alunoService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<AlunoListaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.alunoService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Aluno> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.alunoService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody AlunoDTO alunoDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.alunoService.salvarRegistro(alunoDTO, true).getId()).toUri();
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Aluno adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody AlunoDTO alunoDTO
    ) throws Exception {
        this.alunoService.salvarRegistro(alunoDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.alunoService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
