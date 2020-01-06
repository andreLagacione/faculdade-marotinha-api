package com.lagacione.faculdademarotinhaapi.professor.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorListaDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorToEditDTO;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/professor")
public class ProfessorResource {
    private ProfessorService professorService;

    @Autowired
    public void ProfessorResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping(value="/combo-list")
    public List<ProfessorListaDTO> findAll() {
        return this.professorService.findAll();
    }

    @PostMapping(value = "/list")
    public Page<ProfessorListaDTO> findPage(Pageable pageable) {
        return this.professorService.findPage(pageable);
    }

    @GetMapping(value="/{id}")
    public ProfessorToEditDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.professorService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody ProfessorDTO professorDTO) throws ActionNotAllowedException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.professorService.salvarRegistro(professorDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Professor adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody ProfessorDTO professorDTO
    ) throws ActionNotAllowedException {
        this.professorService.salvarRegistro(professorDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Professor editado com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.professorService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Professor removido com sucesso!");
    }
}
