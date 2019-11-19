package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.dto.CursoDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.services.CursoService;
import javassist.tools.rmi.ObjectNotFoundException;
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
@RequestMapping(value="/curso")
public class CursoResource {
    @Autowired
    private CursoService cursoService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<CursoDTO>> findAll() {
        return ResponseEntity.ok().body(this.cursoService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<CursoDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.cursoService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Curso> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.cursoService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody CursoDTO cursoDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.cursoService.salvarRegistro(cursoDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Curso adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody CursoDTO cursoDTO
    ) throws ObjectNotFoundException {
        this.cursoService.salvarRegistro(cursoDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Curso editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.cursoService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Curso removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
