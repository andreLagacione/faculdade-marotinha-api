package com.lagacione.faculdademarotinhaapi.curso.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoListaDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoToEditDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/curso")
public class CursoResource {
    private CursoService cursoService;

    @Autowired
    public void CursoResource(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping(value="/combo-list")
    public List<CursoListaDTO> findAll() {
        return this.cursoService.findAll();
    }

    @PostMapping(value = "/list")
    public Page<CursoListaDTO> findPage(@PageableDefault(page = 0, size = 25) Pageable pageable) {
        return this.cursoService.findPage(pageable);
    }

    @GetMapping(value="/{id}")
    public CursoToEditDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.cursoService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody CursoDTO cursoDTO) throws ActionNotAllowedException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.cursoService.salvarRegistro(cursoDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Curso adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody CursoDTO cursoDTO
    ) throws ActionNotAllowedException {
        this.cursoService.salvarRegistro(cursoDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Curso editado com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.cursoService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Curso removido com sucesso!");
    }
}
