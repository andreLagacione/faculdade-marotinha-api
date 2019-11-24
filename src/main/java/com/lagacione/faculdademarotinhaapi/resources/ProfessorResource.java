package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.dto.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.dto.ProfessorListaDTO;
import com.lagacione.faculdademarotinhaapi.dto.ProfessorToEditDTO;
import com.lagacione.faculdademarotinhaapi.services.ProfessorService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
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
@CrossOrigin(origins = "*")
@RequestMapping(value="/professor")
public class ProfessorResource {
    @Autowired
    private ProfessorService professorService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<ProfessorListaDTO>> findAll() {
        return ResponseEntity.ok().body(this.professorService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ProfessorListaDTO>> findPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.professorService.findPage(pageable));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ProfessorToEditDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.professorService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody ProfessorDTO professorDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.professorService.salvarRegistro(professorDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Professor adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody ProfessorDTO professorDTO
    ) throws Exception {
        this.professorService.salvarRegistro(professorDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Professor editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.professorService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Professor removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
